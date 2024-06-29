package t1.springaop.exception;

public class TrackTimeException extends RuntimeException {

    public TrackTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackTimeException(String message) {
        super(message);
    }
}
