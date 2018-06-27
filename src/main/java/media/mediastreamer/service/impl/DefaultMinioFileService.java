package media.mediastreamer.service.impl;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.extern.log4j.Log4j2;
import media.mediastreamer.configuration.properties.MinioBuckets;
import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.BadFileTypeException;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.mapper.FileResultMapper;
import media.mediastreamer.service.MinioFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of {@link MinioFileService}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Log4j2
@Service
public class DefaultMinioFileService implements MinioFileService {

    private static final String VIDEO_CONTENT_TYPE_PREFIX = "video";
    private static final String IMG_CONTENT_TYPE_PREFIX = "image";

    private final MinioBuckets minioBuckets;
    private final MinioClient minioClient;

    public DefaultMinioFileService(MinioClient minioClient, MinioBuckets minioBuckets) {
        this.minioClient = minioClient;
        this.minioBuckets = minioBuckets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createBucket(String name) throws GenericServiceException {
        try {
            if (minioClient.bucketExists(name)) {
               log.log(Level.DEBUG, "Bucket with name {} is already created.", name);
               return;
            }
            minioClient.makeBucket(name);
            log.log(Level.DEBUG, "Created bucket with name {}.", name);
        } catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws GenericServiceException {
        log.log(Level.DEBUG, "Started initialization of minio");
        createBucket(minioBuckets.getVideo());
        createBucket(minioBuckets.getImg());
        log.log(Level.DEBUG, "Initialization of minio ended with success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putFile(MultipartFile file) throws GenericServiceException {
        if(file.getContentType().contains(VIDEO_CONTENT_TYPE_PREFIX)) {
            putFileToBucket(file, minioBuckets.getVideo());
            return;
        }
        if(file.getContentType().contains(IMG_CONTENT_TYPE_PREFIX)) {
            putFileToBucket(file, minioBuckets.getImg());
            return;
        }
        log.log(Level.DEBUG, "Bad type of file");
        throw new BadFileTypeException("Bad type of file");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile(String fileName) throws GenericServiceException {
        if(FilenameUtils.getExtension(fileName).equals("mp4")) {
            return getFileFromBucket(fileName, minioBuckets.getVideo());
        }
        if(FilenameUtils.getExtension(fileName).equals("png")) {
            return getFileFromBucket(fileName, minioBuckets.getImg());
        }
        log.log(Level.DEBUG, "Bad type of file");
        throw new BadFileTypeException("Bad type of file");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FileResult> listFiles() throws GenericServiceException {
        try {
            Iterable<Result<Item>> itemResults = minioClient.listObjects(minioBuckets.getVideo());
            Set<FileResult> fileResultSet = new HashSet<>();
            itemResults.forEach(itemResult -> addItemToFileResultSet(itemResult, fileResultSet));

            return fileResultSet;
        } catch (XmlPullParserException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    private void putFileToBucket(MultipartFile file, String bucketName) throws GenericServiceException {
        try {
            minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
            log.log(Level.DEBUG, "Successfully uploaded file with name {}.", file.getOriginalFilename());
        }  catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    public InputStream getFileFromBucket(String fileName, String bucketName) throws GenericServiceException {
        try {
            return minioClient.getObject(bucketName, fileName);
        }   catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    private void addItemToFileResultSet(Result<Item> itemResult, Set<FileResult> fileResultSet) {
        try {
            fileResultSet.add(FileResultMapper.INSTANCE.itemToFileResult(itemResult.get()));
        }   catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }
}
