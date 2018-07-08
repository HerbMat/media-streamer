package media.mediastreamer.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * Entity representing media in database.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Table("medias")
@Data
public class Media {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    private String mediaName;
    private String imgName;
    private MediaType mediaType;

    public Media() {
        this.id = UUID.randomUUID();
    }
}
