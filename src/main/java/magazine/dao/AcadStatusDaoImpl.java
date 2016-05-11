package magazine.dao;

import magazine.domain.UserAcadStatus;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 26.10.2015.
 */
@Repository
@Transactional
public class AcadStatusDaoImpl implements AcadStatusDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public AcadStatusDaoImpl() {
    }

    @Override
    public Long create(UserAcadStatus acadStatus) {
        return (Long) sessionFactory.getCurrentSession().save(acadStatus);
    }

    @Override
    public UserAcadStatus reade(Long id) {
        return (UserAcadStatus) sessionFactory.getCurrentSession().get(UserAcadStatus.class, id);
    }

    @Override
    public void update(UserAcadStatus acadStatus) {
        sessionFactory.getCurrentSession().update(acadStatus);
    }

    @Override
    public void delete(UserAcadStatus acadStatus) {
        sessionFactory.getCurrentSession().delete(acadStatus);
    }

    @Override
    public UserAcadStatus getByString(String acadStatusStr) {
        return (UserAcadStatus) sessionFactory.getCurrentSession()
                .createCriteria(UserAcadStatus.class)
                .add(Restrictions.eq("acadStatus", acadStatusStr))
                .uniqueResult();
    }
}
