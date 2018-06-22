package media.mediastreamer.service;

import media.mediastreamer.dto.FileResult;
import media.mediastreamer.exception.GenericServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;

/**
 * Service responsible for basic file operations.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface FileService {

    /**
     * Uploads file to storage.
     *
     * @param file file to upload
     *
     * @throws GenericServiceException
     */
    void putFile(MultipartFile file) throws GenericServiceException;

    /**
     * Get file with given name from storage.
     *
     * @param fileName name of searched file
     *
     * @return found file
     *
     * @throws GenericServiceException
     */
    InputStream getFile(String fileName) throws GenericServiceException;

    /**
     * Returns information about stored files.
     *
     * @return list of information of stored files.
     *
     * @throws GenericServiceException
     */
    Collection<FileResult> listFiles() throws GenericServiceException;
}
