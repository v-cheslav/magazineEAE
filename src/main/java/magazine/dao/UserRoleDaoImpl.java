package magazine.dao;

import magazine.domain.UserRole;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 13.11.2015.
 */
@Repository
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {

    @Autowired
    SessionFactory sessionFactory;

    public UserRoleDaoImpl() {
    }

    @Override
    public Long create(UserRole userRole) {
        System.err.println("UserRole creating");
        return (Long) sessionFactory.getCurrentSession().save(userRole);
    }

    @Override
    public UserRole read(Long id) {
        return null;
    }

    @Override
    public void update(UserRole userRole) {

    }

    @Override
    public void delete(UserRole userRole) {

    }

    @Override
    public UserRole getUserRole(Enum userRole) {
        return (UserRole) sessionFactory.getCurrentSession()
                .createCriteria(UserRole.class)
                .add(Restrictions.eq("listRole", userRole))
                .uniqueResult();
    }
}
