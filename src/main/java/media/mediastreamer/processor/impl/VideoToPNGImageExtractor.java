package media.mediastreamer.processor.impl;

import media.mediastreamer.multipart.MultipartImage;
import media.mediastreamer.processor.ImageExtractor;
import org.apache.commons.io.FilenameUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Default implementation of {@link ImageExtractor} for png images.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class VideoToPNGImageExtractor implements ImageExtractor {

    @Override
    public MultipartFile extractImage(MultipartFile file) throws IOException {
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

        return new MultipartImage(FilenameUtils.removeExtension(file.getOriginalFilename()) + ".png", MediaType.IMAGE_PNG_VALUE, contentLength, new ByteArrayInputStream(tempImage.toByteArray()));
    }
}
