package ps.demo.commupload.error;

public class ClientErrorException extends RuntimeException {

    public ClientErrorException() {
        super("Client error, please retry");
    }

    public ClientErrorException(Exception cause) {
        super(cause);
    }

    public ClientErrorException(String message, Exception cause) {
        super(message, cause);
    }

}
