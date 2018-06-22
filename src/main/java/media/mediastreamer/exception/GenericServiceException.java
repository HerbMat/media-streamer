package media.mediastreamer.exception;

/**
 * Generic service exception for internal handling.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class GenericServiceException extends Exception {

    public GenericServiceException(String localizedMessage, Exception ex) {
        super(localizedMessage, ex);
    }
}
