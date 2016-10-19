package magazine.servise;

import magazine.domain.UserRole;
import magazine.domain.ListRole;

/**
 * Created by pvc on 18.02.2016.
 */
public interface UserRoleService {
    public UserRole findUserRole(ListRole listRole);
    public void fillDBWihUserRoles();
//    public List<UserRole> setUserRoles(User user, String password, String adminRole) throws AdminRegistrationException;
}
