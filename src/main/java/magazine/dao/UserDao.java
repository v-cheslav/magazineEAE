package magazine.dao;

import magazine.domain.Article;
import magazine.domain.User;

import java.util.List;
import java.util.Map;

/**
* Created by pvc on 21.10.2015.
*/
public interface UserDao {
    public Long create(User user);
    public User read(Long id);
    public void update(User user);
    public void delete(User user);
//    public User findByArticle(Article article);
    public List<User> findAll();
    public List<User> findAllDoctorsAndCandidates();
    public List<User> findBySurname(String surname);
    public List<User> findByUniversity(String university);
    public List<User> findByAcademicStatus(String acadStatus);
    public List<User> findByScientificDegree(String sciDegree);
    public List<User> findByUsername (String username);
    public List<User> findBySearchQuery(Map searchQueryMap);
}
