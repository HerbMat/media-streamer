package media.mediastreamer.configuration;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import media.mediastreamer.configuration.properties.MinioConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio configuration file.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Configuration
@EnableConfigurationProperties(MinioConfigurationProperties.class)
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioConfigurationProperties minioConfigurationProperties) throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioConfigurationProperties.getUrl(), minioConfigurationProperties.getAccessKey(), minioConfigurationProperties.getSecretKey());
    }
}
