package magazine.dao;

import magazine.domain.UserAcadStatus;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 28.09.2016.
 */
@Repository
@Transactional
public class AcStatDaoImpl {
    public static final Logger log = Logger.getLogger(ArticleDaoImpl.class);

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public AcStatDaoImpl() {
    }


    public UserAcadStatus findAcadStat (String acadStat){
        return (UserAcadStatus) sessionFactory.getCurrentSession()
                .createCriteria(UserAcadStatus.class)
                .add(Restrictions.eq("acadStatus", acadStat))
                .uniqueResult();
    }

}
