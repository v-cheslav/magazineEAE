package magazine.Exeptions;

/**
 * Created by pvc on 28.03.2016.
 */
public class ReviewCreationException extends Exception {

    public ReviewCreationException() {
    }

    public ReviewCreationException(String message) {
        super(message);
    }
}