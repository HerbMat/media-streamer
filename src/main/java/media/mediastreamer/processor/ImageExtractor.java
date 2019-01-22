package media.mediastreamer.processor;

import media.mediastreamer.exception.GenericServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * It grabs specific frame from video and returns it as image.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface ImageExtractor {
    MultipartFile extractImage(MultipartFile file) throws IOException, GenericServiceException;
}
