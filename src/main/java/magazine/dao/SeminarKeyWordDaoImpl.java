package magazine.dao;

import magazine.domain.PublicationKeyWord;
//import magazine.domain.SeminarKeyWord;
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
    public Long create(PublicationKeyWord seminarKeyWord) {
        return (Long) sessionFactory.getCurrentSession().save(seminarKeyWord);
    }

    @Override
    public PublicationKeyWord reade(Long id) {
        return (PublicationKeyWord) sessionFactory.getCurrentSession().get(PublicationKeyWord.class, id);
    }

    @Override
    public void update(PublicationKeyWord seminarKeyWord) {
        sessionFactory.getCurrentSession().update(seminarKeyWord);
    }

    @Override
    public void delete(PublicationKeyWord seminarKeyWord) {
        sessionFactory.getCurrentSession().delete(seminarKeyWord);
    }

    @Override
    public PublicationKeyWord getKeyWord(String seminarKeyWord) {
        return (PublicationKeyWord) sessionFactory.getCurrentSession()
                .createCriteria(PublicationKeyWord.class)
                .add(Restrictions.eq("keyWord", seminarKeyWord))
                .uniqueResult();
    }
}
