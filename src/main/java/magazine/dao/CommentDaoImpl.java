package magazine.dao;

import magazine.domain.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
* Created by pvc on 21.10.2015.
*/
@Repository
@Transactional
public class CommentDaoImpl implements CommentDao {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public CommentDaoImpl() {
    }

    @Override
    public Long create(Comment comment) {
        return (Long) sessionFactory.getCurrentSession().save(comment);
    }

    @Override
    public Comment read(Long id) {
        return (Comment) sessionFactory.getCurrentSession().get(Comment.class, id);
    }

    @Override
    public void update(Comment comment) {
        sessionFactory.getCurrentSession().update(comment);
    }

    @Override
    public void delete(Comment comment) {
        sessionFactory.getCurrentSession().delete(comment);
    }
}
