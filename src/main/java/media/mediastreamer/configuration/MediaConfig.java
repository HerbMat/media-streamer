package media.mediastreamer.configuration;

import media.mediastreamer.domain.Media;
import media.mediastreamer.factory.DefaultImageExtractorFactory;
import media.mediastreamer.factory.ImageExtractorFactory;
import media.mediastreamer.factory.MediaFactory;
import media.mediastreamer.factory.UploadFormFactory;
import media.mediastreamer.form.UploadForm;
import media.mediastreamer.processor.ImageExtractor;
import media.mediastreamer.processor.impl.MusicToPNGImageExtractor;
import media.mediastreamer.processor.impl.VideoToPNGImageExtractor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration with beans necessary for upload operations.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Configuration
public class MediaConfig {

    @Bean
    public FactoryBean<UploadForm> uploadFormFactory() {
        return new UploadFormFactory();
    }

    @Bean
    public FactoryBean<Media> mediaFactory() {
        return new MediaFactory();
    }

    @Bean
    public ImageExtractor imageExtractorFromVideo() {
        return new VideoToPNGImageExtractor();
    }

    @Bean
    public ImageExtractor imageExtractorFromMusic() {
        return new MusicToPNGImageExtractor();
    }

    @Bean
    public ImageExtractorFactory imageExtractorFactory(ImageExtractor imageExtractorFromVideo, ImageExtractor imageExtractorFromMusic) {
        return new DefaultImageExtractorFactory(imageExtractorFromMusic, imageExtractorFromVideo);
    }
}
