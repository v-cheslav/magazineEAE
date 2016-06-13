package magazine.dao;


import magazine.domain.UserInterest;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
* Created by pvc on 21.10.2015.
*/
@Repository
@Transactional
public class UserInterestDaoImpl implements UserInterestDao {
    private static Logger log = Logger.getLogger(UserInterestDaoImpl.class);

    @Autowired (required = true)
    private SessionFactory sessionFactory;

    public UserInterestDaoImpl() {
    }

    @Override ()
    public Long create(UserInterest interest) {
        return (Long) sessionFactory.getCurrentSession().save(interest);
    }

    @Override
    public UserInterest read(Long id) {
        return (UserInterest) sessionFactory.getCurrentSession().get(UserInterest.class, id);
    }

    @Override
    public void update(UserInterest userInterest) {
        sessionFactory.getCurrentSession().update(userInterest);
    }

    @Override
    public void delete(UserInterest userInterest) {
        sessionFactory.getCurrentSession().delete(userInterest);
    }


    @Override
    public UserInterest getInterest(String userInterest) {
        return (UserInterest) sessionFactory.getCurrentSession()
                .createCriteria(UserInterest.class)
                .add(Restrictions.eq("keyWord", userInterest))
                .uniqueResult();
    }
}
