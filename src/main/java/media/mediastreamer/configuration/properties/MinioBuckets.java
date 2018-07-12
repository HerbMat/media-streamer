package media.mediastreamer.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Properties storing minio buckets names.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Data
@ConfigurationProperties(prefix = "minio.bucket")
@Validated
public class MinioBuckets {

    @NotEmpty
    private String media;
}
