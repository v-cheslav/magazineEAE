package magazine.Exeptions;

/**
 * Created by pvc on 12.05.2016.
 */
public class SeminarNotFoundException extends Exception {
    public SeminarNotFoundException() {
    }

    public SeminarNotFoundException(String message) {
        super(message);
    }
}
