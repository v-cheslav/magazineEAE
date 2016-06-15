package magazine.dao;

import magazine.domain.Article;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* Created by pvc on 21.10.2015.
*/
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoImpl.class);
    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public UserDaoImpl() {
    }

    @Override
    public Long create(User user) {
        return (Long) sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User read(Long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public List<User> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("surname"))
                .list();
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = (User) sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .add(Restrictions.eq("username", username))
                    .uniqueResult();
        if (user == null){
            throw new UsernameNotFoundException("username: " + username +  " not found!");
        }
        return user;
    }

    @Override
    public List<User> findBySurname(String surname) {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("surname", surname))
                .list();
    }

//    @Override
//    public User findByArticle(Article article) {
//        return (User) sessionFactory.getCurrentSession()
//                .createCriteria(User.class)
//                .add(Restrictions.eq("article", article))
//                .uniqueResult();
//    }

    @Override
    public List<User> findByUniversity(String university) {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("university", university))
                .list();

    }

    @Override
    public List<User> findByAcademicStatus(String acadStatus) {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("acadStatus", acadStatus))
                .list();
    }

    @Override
    public List<User> findByScientificDegree(String sciDegree) {
        return sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("sciDegree", sciDegree))
                .list();
    }

    @Override
    public List<User> findAllDoctorsAndCandidates() {
        List<User> users = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                        .createCriteria("sciDegree")
                .add(Restrictions.disjunction()
                                .add(Restrictions.eq("sciDegree", "PHD"))
                                .add(Restrictions.eq("sciDegree", "DOCTOR"))
                                .add(Restrictions.eq("sciDegree", "CANDIDATE"))
                ).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        .list();
        return users;
    }


    @Override
    public List<User> findBySearchQuery(Map searchQueryMap) {

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);


//        if (searchQueryMap.containsKey("acadStatus")
//                || searchQueryMap.containsKey("sciDegree")
//                || searchQueryMap.containsKey("nameOfUser")) {
//            criteria.createAlias("user", "user");
//        }

        if (searchQueryMap.containsKey("acadStatus")) {
            criteria.add(Restrictions.eq("acadStatus", searchQueryMap.get("acadStatus")));
        }

        if (searchQueryMap.containsKey("sciDegree")) {
            criteria.add(Restrictions.eq("sciDegree", searchQueryMap.get("sciDegree")));
        }

        if (searchQueryMap.containsKey("nameOfUser")) {
            Criterion name = Restrictions.ilike("name", "%" + searchQueryMap.get("nameOfUser") + "%");
            Criterion surname = Restrictions.ilike("surname", "%" + searchQueryMap.get("nameOfUser") + "%");
            Criterion middleName = Restrictions.ilike("middleName", "%" + searchQueryMap.get("nameOfUser") + "%");
            criteria.add(Restrictions.or(name, surname, middleName));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("userId"));

        return criteria.list();
    }


}
