package magazine.servise;

import magazine.dao.UserDao;
import magazine.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Created by pvc on 22.10.2015.
*/
@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public UserAuthService() {
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (username == "Administrator") {
                return null;
            }
            return userDao.findByUsername(username);
    }
}
