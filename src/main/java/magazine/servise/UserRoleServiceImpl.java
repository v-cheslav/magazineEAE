package magazine.servise;

import magazine.Exeptions.AdminRegistrationException;
import magazine.dao.UserRoleDao;
import magazine.domain.ListRole;
import magazine.domain.User;
import magazine.domain.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pvc on 18.02.2016.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    public static final Logger log = Logger.getLogger(UserRoleServiceImpl.class);


    @Value("${adminPassword}")
    private String adminPassword;

    @Autowired
    private UserRoleDao userRoleDao;

    public UserRoleServiceImpl() {
    }

    @Override
    public void fillDBWihUserRoles() {
        for(ListRole rolesList: ListRole.values()){
            UserRole userRole = new UserRole(rolesList);
            userRoleDao.create(userRole);
        }
    }

    @Override
    public UserRole findUserRole(ListRole listRole){
        return userRoleDao.getUserRole(listRole);
    }




//    @Override
//    public List<UserRole> setUserRoles(User user, String password, String adminRole) throws AdminRegistrationException {
//        log.info("setUserRoles method");
//        List<UserRole> userRoles = user.getUserRoles();
//
//        if (adminRole != null) {
//            if (!password.equals(adminPassword)) {
//                throw new AdminRegistrationException("Ви не маєте права реєструватись як адміністратор!");
//            }
//            UserRole superAdmin = userRoleDao.getUserRole(ListRole.SUPERADMIN);
//            UserRole admin = userRoleDao.getUserRole(ListRole.ADMIN);
//            userRoles.add(admin);
//            userRoles.add(superAdmin);
//        }
//        UserRole userRole = userRoleDao.getUserRole(ListRole.USER);
//        userRoles.add(userRole);
//        return userRoles;
//    }

}
