package media.mediastreamer.exception;

public class MissingMediaTypeException extends GenericServiceException {
    public MissingMediaTypeException(String localizedMessage, Exception ex) {
        super(localizedMessage, ex);
    }

    public MissingMediaTypeException(String message) {
        super(message);
    }
}
