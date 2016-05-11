package magazine.dao;

import magazine.domain.Article;
import magazine.domain.User;
import magazine.domain.Review;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Created by pvc on 21.10.2015.
*/
@Repository
@Transactional
public class ReviewDaoImpl implements ReviewDao {

    @Autowired (required = true)
    private SessionFactory sessionFactory;

    public ReviewDaoImpl() {
    }

    @Override
    public Long create(Review review) {
        return (Long) sessionFactory.getCurrentSession().save(review);
    }

    @Override
    public Review read(Long id) {
        return (Review) sessionFactory.getCurrentSession().get(Review.class, id);
    }

    @Override
    public void update(Review review) {
        sessionFactory.getCurrentSession().update(review);
    }

    @Override
    public void delete(Review review) {
        sessionFactory.getCurrentSession().delete(review);
    }

    @Override
    public List<Review> findByArticle(Article article) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("article", article))
                .list();
    }

    @Override
    public List<Review> findByUser(User user) {
        System.out.println("finding by user " + user.toString());
        return sessionFactory.getCurrentSession()
                .createCriteria(Review.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("user", user))
                .list();
    }

    @Override
    public Review findByUserAndArticleId(Long articleId, User user) {
        return (Review) sessionFactory.getCurrentSession()
                .createCriteria(Review.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("user", user))
                .createCriteria("article")
                .add(Restrictions
                        .eq("articleId", articleId))
                .uniqueResult();
    }



    @Override
    public List<Review> findDennyedReview(User user) {
        return  sessionFactory.getCurrentSession()
                .createCriteria(Review.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("status", null))
                .list();
    }

//    @Override
//    public  List<Review> findByUserId(Long userId) {
//        return sessionFactory.getCurrentSession()
//                .createCriteria(Review.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .createCriteria("user")
//                .add(Restrictions
//                        .eq("userId", userId))
//                .list();
//    }
}
