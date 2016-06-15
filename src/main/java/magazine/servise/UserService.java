package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SearchException;
import magazine.domain.Article;
import magazine.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by pvc on 26.10.2015.
 */
public interface UserService {
    public Long createUser(User user)throws RegistrationException;
    public User getUser(Long id);
    public User getUserByUserName(String userName);
    public void changeUser(User user);
    public void removeUser(User user);
    public List<User> getAllUsers();
    public List<User> getAllDoctorsAndCandidates();
    public List<User> searchUsers(String userStr)throws SearchException;

}
