package media.mediastreamer.repositories;

import media.mediastreamer.domain.Media;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Repository
@Profile({ "production", "dev" })
public interface MediaRepository extends ReactiveCrudRepository<Media, UUID> {
}
