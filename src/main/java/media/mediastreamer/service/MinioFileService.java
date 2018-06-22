package media.mediastreamer.service;

import media.mediastreamer.exception.GenericServiceException;

/**
 * Interface extends {@link FileService} on operations specific only to minio.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public interface MinioFileService extends FileService {

    /**
     * Create bucket with given name in minio.
     *
     * @param name name of bucket to create.
     *
     * @throws GenericServiceException
     */
    void createBucket(String name) throws GenericServiceException;

    /**
     * Inits minio storage with default bucket.
     *
     * @throws GenericServiceException
     */
    void init() throws GenericServiceException;
}
