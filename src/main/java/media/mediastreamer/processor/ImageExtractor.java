package media.mediastreamer.processor;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface ImageExtractor {
    MultipartFile extractImage(MultipartFile file) throws IOException;
}
