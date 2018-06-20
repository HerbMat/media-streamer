package media.mediastreamer.configuration;

import media.mediastreamer.factory.UploadFormFactory;
import media.mediastreamer.form.UploadForm;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration with beans necessary for upload operations.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Configuration
public class UploadConfig {

    @Bean
    public FactoryBean<UploadForm> uploadFormFactory() {
        return new UploadFormFactory();
    }
}
