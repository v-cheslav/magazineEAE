package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SuchUserExistException;
import magazine.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
