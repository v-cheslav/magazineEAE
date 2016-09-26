package magazine.servise;

import magazine.Exeptions.AdminRegistrationException;
import magazine.domain.ListRole;
import magazine.domain.User;
import magazine.domain.UserRole;

import java.util.Set;

/**
 * Created by pvc on 18.02.2016.
 */
public interface UserRoleService {
//    public UserRole findUserRole(ListRole listRole);
    public void fillDBWihUserRoles();
    public Set<UserRole> setUserRoles(User user, String password, String adminRole) throws AdminRegistrationException;
}
