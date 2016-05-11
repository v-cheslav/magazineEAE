package magazine.servise;

import magazine.dao.UserRoleDao;
import magazine.domain.ListRole;
import magazine.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pvc on 18.02.2016.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

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
}
