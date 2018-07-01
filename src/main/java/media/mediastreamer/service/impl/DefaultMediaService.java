package media.mediastreamer.service.impl;

import lombok.extern.log4j.Log4j2;
import media.mediastreamer.domain.Media;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.multipart.MultipartImage;
import media.mediastreamer.processor.ImageExtractor;
import media.mediastreamer.repositories.MediaRepository;
import media.mediastreamer.service.FileService;
import media.mediastreamer.service.MediaService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Level;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default implementation of {@link MediaService}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Service
@Log4j2
public class DefaultMediaService implements MediaService {
    private FileService fileService;
    private MediaRepository mediaRepository;
    private FactoryBean<Media> mediaFactory;
    private ImageExtractor imageExtractor;

    public DefaultMediaService(FileService fileService, MediaRepository mediaRepository, FactoryBean<Media> mediaFactory, ImageExtractor imageExtractor) {
        this.fileService = fileService;
        this.mediaRepository = mediaRepository;
        this.mediaFactory = mediaFactory;
        this.imageExtractor = imageExtractor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(MultipartFile media) throws GenericServiceException {
        fileService.putFile(media);
        try {
            MultipartFile posterImage = imageExtractor.extractImage(media);
            fileService.putFile(posterImage);
            saveMediaInformation(media, posterImage);
        } catch (IOException e) {
            throw new GenericServiceException("IO exception");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile(String name) throws GenericServiceException {
        return fileService.getFile(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<Media> listMedias() {
        return mediaRepository.findAll();
    }

    private void saveMediaInformation(MultipartFile mediaFile, MultipartFile posterImage) throws GenericServiceException {
        try {
            Media mediaInformation = mediaFactory.getObject();
            mediaInformation.setImgName(posterImage.getOriginalFilename());
            mediaInformation.setMediaName(mediaFile.getOriginalFilename());
            mediaInformation.setMediaType(media.mediastreamer.domain.MediaType.VIDEO);
            mediaRepository.save(mediaInformation).block();
        } catch (Exception e) {
            log.log(Level.ERROR, e.getLocalizedMessage(), e);
            throw new GenericServiceException(e.getLocalizedMessage(), e);
        }
    }

}
