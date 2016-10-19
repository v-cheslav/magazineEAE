package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by pvc on 27.09.2016.
 */
public interface UserBuilder {
    public User buildUser(MultipartHttpServletRequest request) throws RegistrationException;
}
