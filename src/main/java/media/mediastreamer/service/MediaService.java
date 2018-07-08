package media.mediastreamer.service;

import media.mediastreamer.domain.Media;
import media.mediastreamer.exception.GenericServiceException;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.InputStream;

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

    /**
     * It returns video with given name.
     *
     * @param name name of video
     *
     * @return input stream of found video
     *
     * @throws GenericServiceException
     */
    InputStream getFile(String name) throws GenericServiceException;

    /**
     * It returns collection of stored medias information.
     *
     * @return collection of all stored medias information.
     *
     * @throws GenericServiceException
     */
    Flux<Media> listMedias() throws GenericServiceException;
}
