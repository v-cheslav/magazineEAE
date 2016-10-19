package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;

/**
 * Created by pvc on 21.10.2015.
 */
public interface RegistrationService {
    public void regUser(User user) throws RegistrationException;
//    public void regUser(Map<String, String> userParameters) throws RegistrationException;
//    public boolean checkIfUserExist(String username)throws SuchUserExistException;
    public boolean checkIfUserPasswordCorrect(String password)throws RegistrationException;
//    public void updateUser(String userStr, User user) throws RegistrationException;
}
