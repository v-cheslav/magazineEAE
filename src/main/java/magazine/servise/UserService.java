package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SuchUserExistException;
import magazine.domain.Article;
import magazine.domain.Publication;
import magazine.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by pvc on 26.10.2015.
 */
public interface UserService {
    public Long createUser(User user)throws RegistrationException;
//    public User buildUser(Map<String, String> userParameters)throws RegistrationException;
    public User getUser(Long id);
    public User getUserByUserName(String userName);
    public void changeUser(User user);
    public void updateUser(User oldUser, User newUser) throws RegistrationException;
//    public void updateUser(Map<String, String> userParameters, User user) throws RegistrationException;
    public boolean checkIfUserExist (String username) throws SuchUserExistException;
    public void removeUser(User user);
    public List<User> getAllUsers();
    public List<User> getAllDoctorsAndCandidates();
    public List<User> searchUsers(String userStr)throws SearchException;
    public void increaseUserPublications (User user);
}
