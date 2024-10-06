package ps.demo.commupload.error;

public class ServiceErrorException extends RuntimeException {

    public ServiceErrorException() {
        super("Service error, please contact admin");
    }

    public ServiceErrorException(Exception cause) {
        super(cause);
    }

    public ServiceErrorException(String message, Exception cause) {
        super(message, cause);
    }


}
