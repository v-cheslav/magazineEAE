//package magazine.dao;
//
//import magazine.domain.Message;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Created by pvc on 14.03.2016.
// */
//@Repository
//@Transactional
//public class MessageDaoImpl implements MessageDao {
//
//    @Autowired(required = true)
//    private SessionFactory sessionFactory;
//
//    public MessageDaoImpl() {
//    }
//
//    @Override
//    public Long create(Message message) {
//        return (Long) sessionFactory.getCurrentSession().save(message);
//    }
//
//    @Override
//    public Message read(Long id) {
//        return (Message) sessionFactory.getCurrentSession().get(Message.class, id);
//    }
//
//    @Override
//    public void update(Message message) {
//        sessionFactory.getCurrentSession().update(message);
//    }
//
//    @Override
//    public void delete(Message message) {
//        sessionFactory.getCurrentSession().delete(message);
//    }
//}
