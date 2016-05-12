package magazine.dao;

import magazine.Exeptions.SeminarNotFoundException;
import magazine.domain.Seminar;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by pvc on 29.02.2016.
 */
@Repository
@Transactional
public class SeminarDaoImpl implements SeminarDao {
    private static Logger log = Logger.getLogger(SeminarDaoImpl.class);

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public SeminarDaoImpl() {
    }

    @Override
    public Long create(Seminar seminar) {
        return (Long) sessionFactory.getCurrentSession().save(seminar);
    }

    @Override
    public Seminar read(Long id) {
        return (Seminar) sessionFactory.getCurrentSession().get(Seminar.class, id);
    }

    @Override
    public void update(Seminar seminar) {
        sessionFactory.getCurrentSession().update(seminar);
    }

    @Override
    public void createOrUpdate(Seminar seminar){
        sessionFactory.getCurrentSession().saveOrUpdate(seminar);
    }

    @Override
    public void delete(Seminar seminar) {
        sessionFactory.getCurrentSession().delete(seminar);
    }

    @Override
    public List<Seminar> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .addOrder(Order.desc("seminarPublicationDate"))
                .list();
    }


    @Override
    public List<Seminar> findAllPublished() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", true))
                .addOrder(Order.desc("seminarPublicationDate"))
                .list();
    }




    @Override
    public List<Seminar> findAllAnnounced() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", false))
                .addOrder(Order.desc("seminarPublicationDate"))
                .list();
    }

    @Override
    public List<Seminar> findAllAnnouncedByDate(Calendar date) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("seminarPublicationDate", date))
//                .addOrder(Order.desc("seminarPublicationDate"))
                .list();
    }

    @Override
    public List<Seminar> findSeminarsByDate(Calendar date) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", false))
                .add(Restrictions.eq("seminarPublicationDate", date))
                .addOrder(Order.asc("seminarPublicationDate"))
                .list();
    }


    @Override
    public Seminar findNearestSeminar(Calendar currentDate) throws SeminarNotFoundException{
         List<Seminar> seminars = sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", false))
                .add(Restrictions.ge("seminarPublicationDate", currentDate))
                .addOrder(Order.asc("seminarPublicationDate"))
                .setMaxResults(1)
                .list();
        if (seminars.size() == 0){
            throw new SeminarNotFoundException("Семінар не знайдено");
        }
        Seminar seminar = seminars.get(0);
        return seminar;
    }

    @Override
    public List<Seminar> findAllApplyied() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.isNull("isPublished"))
        .list();
    }

    @Override
    public Seminar findSeminarByName(String seminarName) {
        return (Seminar) sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", false))
                .add(Restrictions.eq("seminarName", seminarName))
                .addOrder(Order.desc("seminarPublicationDate"))
                .uniqueResult();
    }

    @Override
    public Seminar findAnnouncedByUser(User user) {
        return (Seminar)  sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", false))
                .add(Restrictions.eq("user", user))
                .addOrder(Order.desc("seminarPublicationDate"))
                .uniqueResult();
    }

    @Override
    public  List<Seminar> findByUserId(Long userId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Seminar.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("isPublished", true))
                .createCriteria("user")
                .add(Restrictions
                        .eq("userId", userId))
                .list();
    }




    @Override
    public List<Seminar> findBySearchQuery(Map searchQueryMap) {

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Seminar.class, "sem");
        criteria.createAlias("user", "user");

        if (searchQueryMap.containsKey("dateFrom")){
            criteria.add(Restrictions.ge("seminarPublicationDate", searchQueryMap.get("dateFrom")));
        }

        if (searchQueryMap.containsKey("dateTo")){
            criteria.add(Restrictions.le("seminarPublicationDate", searchQueryMap.get("dateTo")));
        }

        if (searchQueryMap.containsKey("keyWords")){
            criteria.createAlias("sem.seminarKeyWords", "keyWord")
                    .add(Restrictions
                            .eq("keyWord.semKeyWord", searchQueryMap.get("keyWords")));
        }

        if (searchQueryMap.containsKey("nameOfSeminar")) {
            criteria.add(Restrictions.ilike("seminarName", "%" + searchQueryMap.get("nameOfSeminar") + "%"));
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

        criteria.add(Restrictions.eq("isPublished", true));
//        criteria.add(Restrictions.isNotNull("user.userId"));//todo only published

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("seminarId"));

        return criteria.list();
    }
}
