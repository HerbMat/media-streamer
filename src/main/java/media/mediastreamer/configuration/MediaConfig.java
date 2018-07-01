package media.mediastreamer.configuration;

import media.mediastreamer.domain.Media;
import media.mediastreamer.factory.MediaFactory;
import media.mediastreamer.factory.UploadFormFactory;
import media.mediastreamer.form.UploadForm;
import media.mediastreamer.processor.ImageExtractor;
import media.mediastreamer.processor.impl.PNGImageExtractor;
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
    public ImageExtractor imageExtractor() {
        return new PNGImageExtractor();
    }
}
