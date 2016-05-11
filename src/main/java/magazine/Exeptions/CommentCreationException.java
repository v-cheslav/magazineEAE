package magazine.Exeptions;

/**
 * Created by pvc on 30.03.2016.
 */
public class CommentCreationException extends Exception {
    public CommentCreationException() {
    }
    public CommentCreationException(String message) {
        super(message);
    }
}
