package magazine.dao;

import magazine.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

/**
* Created by pvc on 20.10.2015.
*/
@Repository
@Transactional
public class ArticleDaoImpl implements ArticleDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public ArticleDaoImpl() {
    }

    @Override
    public Long create(Article article) {
        return (Long) sessionFactory.getCurrentSession().save(article);
    }

    @Override
    public Article read(Long id) {
        return (Article) sessionFactory.getCurrentSession().get(Article.class, id);
    }

    @Override
    public void update(Article article) {
        sessionFactory.getCurrentSession().update(article);
    }

    @Override
    public void delete(Article article) {
        sessionFactory.getCurrentSession().delete(article);
    }

    @Override
    public List<Article> findAllPublishedBySection(Section section) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("articleId"))
                .add(Restrictions.eq("articleSection", section))
                .add(Restrictions.eq("isPrintable", true))
                .list();
    }

    @Override
    public List<Article> findAllPublished() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("articleId"))
                .add(Restrictions.eq("isPrintable", true))
                .list();
    }

    @Override
    public Article findUnPublishedByUser(User user) {
        return (Article) sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("isPrintable", false))
                .uniqueResult();
    }

    @Override
    public List<Article> findNewest() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .add(Restrictions.eq("isPrintable", true))
                .addOrder(Order.desc("articleId"))
                .setMaxResults(10)//todo
                .list();
    }

    @Override
    public  List<Article> findByUserId(Long userId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .createCriteria("user")
                .add(Restrictions.eq("userId", userId))
                .list();
    }

    @Override
    public  List<Article> findByReviewerId(Long userId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Review.class)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("article")))
                .createCriteria("user")
                .add(Restrictions.eq("userId", userId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }


    @Override
    public List<Article> findBySearchQuery(Map searchQueryMap) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Article.class, "art");
        criteria.createAlias("user", "user");

        if (searchQueryMap.containsKey("section")){
            criteria.add(Restrictions.eq("articleSection", searchQueryMap.get("section")));
        }

        if (searchQueryMap.containsKey("dateFrom")){
            criteria.add(Restrictions.ge("articlePublicationDate", searchQueryMap.get("dateFrom")));
        }

        if (searchQueryMap.containsKey("dateTo")){
            criteria.add(Restrictions.le("articlePublicationDate", searchQueryMap.get("dateTo")));
        }

        if (searchQueryMap.containsKey("keyWords")){
            criteria.createAlias("art.articleKeyWords", "keyWord")
                    .add(Restrictions
                            .eq("keyWord.artKeyWord", searchQueryMap.get("keyWords")));
        }

        if (searchQueryMap.containsKey("nameOfArticle")) {
            criteria.add(Restrictions.ilike("articleName", "%" + searchQueryMap.get("nameOfArticle") + "%"));
        }

        if (searchQueryMap.containsKey("acadStatus")) {
            criteria.add(Restrictions.eq("user.acadStatus", searchQueryMap.get("acadStatus")));
        }

        if (searchQueryMap.containsKey("sciDegree")) {
            criteria.add(Restrictions.eq("user.sciDegree", searchQueryMap.get("sciDegree")));
        }

        if (searchQueryMap.containsKey("nameOfUser")) {
            Criterion name = Restrictions.ilike("user.name", "%" + searchQueryMap.get("nameOfUser") + "%");
            Criterion surname = Restrictions.ilike("user.surname", "%" + searchQueryMap.get("nameOfUser") + "%");
            Criterion middleName = Restrictions.ilike("user.middleName", "%" + searchQueryMap.get("nameOfUser") + "%");
            criteria.add(Restrictions.or(name, surname, middleName));
        }

//        criteria.add(Restrictions.eq("isPrintable", true)); //todo only published

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("articleId"));

        return criteria.list();
    }
}
