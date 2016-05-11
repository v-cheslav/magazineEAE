package magazine.dao;

import magazine.domain.SeminarKeyWord;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 26.01.2016.
 */
@Repository
@Transactional
public class SeminarKeyWordDaoImpl implements SeminarKeyWordDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public SeminarKeyWordDaoImpl() {
    }

    @Override
    public Long create(SeminarKeyWord seminarKeyWord) {
        return (Long) sessionFactory.getCurrentSession().save(seminarKeyWord);
    }

    @Override
    public SeminarKeyWord reade(Long id) {
        return (SeminarKeyWord) sessionFactory.getCurrentSession().get(SeminarKeyWord.class, id);
    }

    @Override
    public void update(SeminarKeyWord seminarKeyWord) {
        sessionFactory.getCurrentSession().update(seminarKeyWord);
    }

    @Override
    public void delete(SeminarKeyWord seminarKeyWord) {
        sessionFactory.getCurrentSession().delete(seminarKeyWord);
    }

    @Override
    public SeminarKeyWord getKeyWord(String seminarKeyWord) {
        return (SeminarKeyWord) sessionFactory.getCurrentSession()
                .createCriteria(SeminarKeyWord.class)
                .add(Restrictions.eq("semKeyWord", seminarKeyWord))
                .uniqueResult();
    }
}
