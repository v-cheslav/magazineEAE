package magazine.dao;

import magazine.domain.ArticleKeyWord;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pvc on 01.12.2015.
 */
@Repository
@Transactional
public class ArticleKeyWordDaoImpl implements ArticleKeyWordDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public ArticleKeyWordDaoImpl() {
    }

    @Override
    public Long create(ArticleKeyWord articleKeyWord) {
        return (Long) sessionFactory.getCurrentSession().save(articleKeyWord);
    }

    @Override
    public ArticleKeyWord reade(Long id) {
        return (ArticleKeyWord) sessionFactory.getCurrentSession().get(ArticleKeyWord.class, id);
    }

    @Override
    public void update(ArticleKeyWord articleKeyWord) {
        sessionFactory.getCurrentSession().update(articleKeyWord);
    }

    @Override
    public void delete(ArticleKeyWord articleKeyWord) {
        sessionFactory.getCurrentSession().delete(articleKeyWord);
    }

    @Override
    public ArticleKeyWord getKeyWord(String articleKeyWord) {
        return (ArticleKeyWord) sessionFactory.getCurrentSession()
                .createCriteria(ArticleKeyWord.class)
                .add(Restrictions.eq("artKeyWord", articleKeyWord))
                .uniqueResult();
    }
}
