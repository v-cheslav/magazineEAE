package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;

/**
 * Created by pvc on 21.10.2015.
 */
public interface RegistrationService {
    public void regUser(String user) throws RegistrationException;
    public void updateUser(String userStr, User user) throws RegistrationException;
}
