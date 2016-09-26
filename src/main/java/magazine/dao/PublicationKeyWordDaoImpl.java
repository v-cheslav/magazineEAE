package magazine.dao;

import magazine.domain.PublicationKeyWord;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 17.09.2016.
 */
@Repository
@Transactional
public class PublicationKeyWordDaoImpl implements PublicationKeyWordDao {
    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public PublicationKeyWordDaoImpl() {
    }

    @Override
    public Long create(PublicationKeyWord publicationKeyWord) {
        return (Long) sessionFactory.getCurrentSession().save(publicationKeyWord);
    }

    @Override
    public PublicationKeyWord reade(Long id) {
        return (PublicationKeyWord) sessionFactory.getCurrentSession().get(PublicationKeyWord.class, id);
    }

    @Override
    public void update(PublicationKeyWord publicationKeyWord) {
        sessionFactory.getCurrentSession().update(publicationKeyWord);
    }

    @Override
    public void delete(PublicationKeyWord publicationKeyWord) {
        sessionFactory.getCurrentSession().delete(publicationKeyWord);
    }

    @Override
    public PublicationKeyWord getKeyWord(String publicationKeyWord) {
        return (PublicationKeyWord) sessionFactory.getCurrentSession()
                .createCriteria(PublicationKeyWord.class)
                .add(Restrictions.eq("keyWord", publicationKeyWord))
                .uniqueResult();
    }
}
