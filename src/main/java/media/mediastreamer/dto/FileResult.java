package media.mediastreamer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * DTO for {@link io.minio.messages.Item}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Data
public class FileResult {
    private String objectName;
    private Date lastModified;
    private String etag;
    private long size;
    private String storageClass;
    private Owner owner;
    private boolean isDir;

    @Data
    @AllArgsConstructor
    public static final class Owner {
        private String id;
        private String displayName;
    }
}
