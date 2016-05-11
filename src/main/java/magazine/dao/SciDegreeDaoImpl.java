package magazine.dao;

import magazine.domain.UserSciDegree;
import org.apache.log4j.Logger;
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
public class SciDegreeDaoImpl implements SciDegreeDao  {
    private static Logger log = Logger.getLogger(SciDegreeDaoImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    public SciDegreeDaoImpl() {
    }

    @Override
    public Long create(UserSciDegree userSciDegree) {

        return (Long) sessionFactory.getCurrentSession().save(userSciDegree);
    }

    @Override
    public UserSciDegree getByString(String sciDegreeStr) {
        return (UserSciDegree) sessionFactory.getCurrentSession()
                .createCriteria(UserSciDegree.class)
                .add(Restrictions.eq("sciDegree" , sciDegreeStr))
                .uniqueResult();
    }
}
