package magazine.dao;

import magazine.domain.Section;
import org.apache.log4j.Logger;
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
public class SectionDaoImpl implements SectionDao {
    private static Logger log = Logger.getLogger(SectionDaoImpl.class);

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public SectionDaoImpl() {
    }

    @Override
    public Long create(Section section) {
        return (Long) sessionFactory.getCurrentSession().save(section);
    }

    @Override
    public Section read(Long id) {
        return null;
    }

    @Override
    public void update(Section section) {
        sessionFactory.getCurrentSession().update(section);
    }

    @Override
    public void delete(Section section) {

    }

    @Override
    public Section getSectionByName(Enum sectionName) {
        return (Section) sessionFactory.getCurrentSession()
                .createCriteria(Section.class)
                .add(Restrictions.eq("sectionList", sectionName))
                .uniqueResult();
    }
}
