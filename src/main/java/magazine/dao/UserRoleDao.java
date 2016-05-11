package magazine.dao;

import magazine.domain.UserRole;

/**
 * Created by pvc on 13.11.2015.
 */
public interface UserRoleDao {
    public Long create(UserRole userRole);
    public UserRole read(Long id);
    public void update(UserRole userRole);
    public void delete(UserRole userRole);
    public UserRole getUserRole(Enum userRole);
}
