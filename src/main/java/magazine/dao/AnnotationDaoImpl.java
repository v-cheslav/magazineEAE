package magazine.dao;

import magazine.domain.Annotation;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
* Created by pvc on 20.10.2015.
*/
@Repository
@Transactional
public class AnnotationDaoImpl implements AnnotationDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public AnnotationDaoImpl() {
    }

    @Override
    public Long create(Annotation annotation) {
        return (Long) sessionFactory.getCurrentSession().save(annotation);
    }

    @Override
    public Annotation reade(Long id) {
        return (Annotation) sessionFactory.getCurrentSession().get(Annotation.class, id);
    }

    @Override
    public void update(Annotation annotation) {
        sessionFactory.getCurrentSession().update(annotation);
    }

    @Override
    public void delete(Annotation annotation) {
        sessionFactory.getCurrentSession().delete(annotation);
    }
}
