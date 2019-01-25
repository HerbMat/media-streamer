package media.mediastreamer.exception;

/**
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
public class NoAudioInfoException extends GenericServiceException {
    public NoAudioInfoException(String localizedMessage, Exception ex) {
        super(localizedMessage, ex);
    }

    public NoAudioInfoException(String message) {
        super(message);
    }
}
