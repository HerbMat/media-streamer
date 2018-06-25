package media.mediastreamer.service.impl;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.extern.log4j.Log4j2;
import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.mapper.FileResultMapper;
import media.mediastreamer.service.MinioFileService;
import org.apache.logging.log4j.Level;
import org.springframework.core.env.Environment;
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

    private static final String BUCKET_NAME_ATTR_NAME = "minio.bucket.name";

    private String bucketName;
    private final MinioClient minioClient;

    public DefaultMinioFileService(MinioClient minioClient, Environment env) {
        this.minioClient = minioClient;
        this.bucketName = env.getProperty(BUCKET_NAME_ATTR_NAME);
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
        createBucket(this.bucketName);
        log.log(Level.DEBUG, "Initialization of minio ended with success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putFile(MultipartFile file) throws GenericServiceException {
        try {
            minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
            log.log(Level.DEBUG, "Successfully uploaded file with name {}.", file.getOriginalFilename());
        }  catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile(String fileName) throws GenericServiceException {
        try {
            return minioClient.getObject(bucketName, fileName);
        }   catch (MinioException | GeneralSecurityException | XmlPullParserException | IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FileResult> listFiles() throws GenericServiceException {
        try {
            Iterable<Result<Item>> itemResults = minioClient.listObjects(bucketName);
            Set<FileResult> fileResultSet = new HashSet<>();
            itemResults.forEach(itemResult -> addItemToFileResultSet(itemResult, fileResultSet));

            return fileResultSet;
        } catch (XmlPullParserException e) {
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
