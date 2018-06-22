package media.mediastreamer.listeners;

import lombok.extern.log4j.Log4j2;
import media.mediastreamer.exception.GenericServiceException;
import media.mediastreamer.service.MinioFileService;
import org.apache.logging.log4j.Level;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listener responsible for initializing data after server start.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Component
@Log4j2
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private MinioFileService minioFileService;

    public ApplicationStartup(MinioFileService minioFileService) {
        this.minioFileService = minioFileService;
    }

    /**
     * It inits minio storage.
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            minioFileService.init();
        } catch (GenericServiceException e) {
            log.log(Level.ERROR, "Cannot create bucket on startup", e);
            throw new RuntimeException("Cannot create bucket on startup", e);
        }
    }
}
