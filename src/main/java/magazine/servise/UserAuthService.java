package magazine.servise;

import magazine.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Created by pvc on 22.10.2015.
*/
@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private SessionFactory sessionFactory;

    public UserAuthService() {
    }

    @Override //todo  перенести в userDao
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == "Administrator"){
            return null;
        } else {
            List <User> users = sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .list();

            User user = (User) sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .uniqueResult();
            if (user == null){
                throw new UsernameNotFoundException("Користувач: " + username +  " не знайдений!");
            }
            return user;
        }


    }
}
