package magazine.dao;

import magazine.domain.UserSex;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 28.10.2015.
 */
@Repository
@Transactional
public class UserSexDaoImpl implements UserSexDao {

    @Autowired
    SessionFactory sessionFactory;

    public UserSexDaoImpl() {
    }


    @Override
    public Long create(UserSex userSex) {//todo
        return (Long) sessionFactory.getCurrentSession().save(userSex);
    }

    @Override
    public UserSex read(Long id) {//todo
        return null;
    }//todo

    @Override
    public void update(UserSex userSex) {

    }

    @Override
    public void delete(UserSex userSex) {//todo

    }

    @Override
    public UserSex getByString(String userSex) {
        return (UserSex) sessionFactory.getCurrentSession()
                .createCriteria(UserSex.class)
                .add(Restrictions.eq("userSex", userSex))
                .uniqueResult();
    }
}
