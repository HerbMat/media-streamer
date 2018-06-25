package media.mediastreamer.service;

import media.mediastreamer.exception.GenericServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;

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
     * It returns collection of stored file names.
     *
     * @return collection of all stored file names.
     *
     * @throws GenericServiceException
     */
    Collection<String> listFiles() throws GenericServiceException;
}
