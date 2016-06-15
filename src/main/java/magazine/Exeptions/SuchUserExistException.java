package magazine.Exeptions;

/**
 * Created by pvc on 15.06.2016.
 */
public class SuchUserExistException extends RegistrationException {

    public SuchUserExistException() {
    }

    public SuchUserExistException(String message) {
        super(message);
    }

}
