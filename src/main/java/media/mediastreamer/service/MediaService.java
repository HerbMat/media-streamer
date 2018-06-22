package media.mediastreamer.service;

import media.mediastreamer.exception.GenericServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service responsible for basic operations on media file.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface MediaService {

    /**
     * Upload given media file.
     *
     * @param media media file
     *
     * @throws GenericServiceException
     */
    void upload(MultipartFile media) throws GenericServiceException;
}
