package media.mediastreamer.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Properties for minio client.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */

@Data
@ConfigurationProperties(prefix = "minio")
@Validated
public class MinioConfigurationProperties {

    @NotEmpty
    String url;

    @NotEmpty
    String secretKey;

    @NotEmpty
    String accessKey;
}
