package ps.demo.commupload.error;

import java.util.Set;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book id not found : " + id);
    }
}
