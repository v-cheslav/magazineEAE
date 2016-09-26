package magazine.dao;

import magazine.domain.*;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
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
    public static final Logger log = Logger.getLogger(ArticleDaoImpl.class);


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
                .addOrder(Order.desc("publicationId"))
                .add(Restrictions.eq("articleSection", section))
                .add(Restrictions.eq("isPrintable", true))
                .list();
    }

    @Override
    public List<Article> findAllPublished() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("publicationId"))
                .add(Restrictions.eq("isPrintable", true))
                .list();
    }

    @Override
    public List <Article> findAllUnpublished() {
        return  sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPrintable", false))
                .list();
    }


    @Override
    public List <Article> findWithoutReviewers() {
        return  sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isReviewersAssigned", false))
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
    public List<Article> findNewest() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPrintable", true))
                .addOrder(Order.desc("publicationId"))
                .setMaxResults(10)//todo
                .list();
    }

    @Override
    public Article findUnPublishedByUser(User user) {
                return (Article) sessionFactory.getCurrentSession()
                .createCriteria(Article.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("isPrintable", false))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();
    }

    @Override
    public Article findUnPublishedByReviewer(User reviewer) {
        log.info("findUnPublishedByReviewer");
        return (Article) sessionFactory.getCurrentSession()
                .createCriteria(Article.class, "article")
                .createAlias("article.articleReviews", "review")
                .createAlias("review.user", "user")
                .add(Restrictions
                        .eq("user.userId", reviewer.getUserId()))
                .add(Restrictions.eq("isPrintable", false))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .uniqueResult();
    }

    @Override
    public List<Article> findArticlesByKeywords( Article article) {
        List<PublicationKeyWord> keywords = article.getPublicationKeyWords();
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Article.class, "article");
        criteria.createAlias("article.publicationKeyWords", "keyWord");

        Disjunction or = Restrictions.disjunction();
        for (PublicationKeyWord keyWord : keywords){
            or.add(Restrictions.eq("keyWord.keyWord", keyWord.getArtKeyWord()));
        }
        criteria.add(or);
        criteria.add(Restrictions.ne("publicationId", article.getPublicationId()));
        criteria.add(Restrictions.eq("isPrintable", true));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("publicationId"));
        return criteria.list();
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
            criteria.add(Restrictions.ge("publicationDate", searchQueryMap.get("dateFrom")));
        }

        if (searchQueryMap.containsKey("dateTo")){
            criteria.add(Restrictions.le("publicationDate", searchQueryMap.get("dateTo")));
        }

        if (searchQueryMap.containsKey("keyWords")){
            criteria.createAlias("art.publicationKeyWords", "keyWord")
                    .add(Restrictions
                            .eq("keyWord.keyWord", searchQueryMap.get("keyWords")));
        }

        if (searchQueryMap.containsKey("nameOfArticle")) {
            criteria.add(Restrictions.ilike("publicationName", "%" + searchQueryMap.get("nameOfArticle") + "%"));
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

        criteria.add(Restrictions.eq("isPrintable", true));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("publicationId"));

        return criteria.list();
    }
}
