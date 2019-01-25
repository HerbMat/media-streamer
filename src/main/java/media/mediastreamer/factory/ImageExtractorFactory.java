package media.mediastreamer.factory;

import media.mediastreamer.domain.MediaType;
import media.mediastreamer.processor.ImageExtractor;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface ImageExtractorFactory {
    ImageExtractor getExtractorForMediaType(MediaType mediaType);
}
