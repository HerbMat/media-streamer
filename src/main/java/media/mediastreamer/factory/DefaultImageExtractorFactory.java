package media.mediastreamer.factory;

import media.mediastreamer.domain.MediaType;
import media.mediastreamer.processor.ImageExtractor;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class DefaultImageExtractorFactory  implements ImageExtractorFactory {

    private ImageExtractor imageExtractorFromMusic;
    private ImageExtractor imageExtractorFromVideo;

    public DefaultImageExtractorFactory(ImageExtractor imageExtractorFromMusic, ImageExtractor imageExtractorFromVideo) {
        this.imageExtractorFromMusic = imageExtractorFromMusic;
        this.imageExtractorFromVideo = imageExtractorFromVideo;
    }

    public ImageExtractor getExtractorForMediaType(MediaType mediaType) {
        switch (mediaType) {
            case VIDEO:
                return imageExtractorFromVideo;
            case MUSIC:
                return imageExtractorFromMusic;
        }
        return null;
    }
}
