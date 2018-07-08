package media.mediastreamer.exception;

/**
 * Exception for unsupported file types.
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class BadFileTypeException extends GenericServiceException {
    public BadFileTypeException(String message) {
        super(message);
    }
}
