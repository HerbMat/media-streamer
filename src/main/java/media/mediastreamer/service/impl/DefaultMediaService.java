package media.mediastreamer.service.impl;

import lombok.extern.log4j.Log4j2;
import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.multipart.MultipartImage;
import media.mediastreamer.service.FileService;
import media.mediastreamer.service.MediaService;
import org.apache.commons.io.FilenameUtils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link MediaService}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Service
@Log4j2
public class DefaultMediaService implements MediaService {
    private FileService fileService;

    public DefaultMediaService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(MultipartFile media) throws GenericServiceException {
        fileService.putFile(media);
        try {
            fileService.putFile(getPosterImage(media));
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
    public Collection<String> listFiles() throws GenericServiceException {
        return fileService.listFiles().stream()
                .map(FileResult::getObjectName)
                .collect(Collectors.toSet());
    }

    private MultipartFile getPosterImage(MultipartFile file) throws IOException {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file.getInputStream());
        frameGrabber.start();
        frameGrabber.setFrameNumber(frameGrabber.getLengthInFrames()/2);
        frameGrabber.getLengthInFrames();
        Frame posterFrame = frameGrabber.grabImage();
        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
        BufferedImage posterImage = java2DFrameConverter.convert(posterFrame);
        ByteArrayOutputStream tempImage = new ByteArrayOutputStream();
        ImageIO.write(posterImage, "PNG", tempImage);
        tempImage.close();
        int contentLength = tempImage.size();

        return new MultipartImage(FilenameUtils.removeExtension(file.getOriginalFilename()) + ".png", "image/png", contentLength, new ByteArrayInputStream(tempImage.toByteArray()));
    }

}
