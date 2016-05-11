package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SearchException;
import magazine.dao.UserDao;
import magazine.dao.UserRoleDao;
import magazine.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by pvc on 26.10.2015.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    AcadStatusService acadStatusService;

    @Autowired
    SciDegreeService sciDegreeService;

    @Autowired
    ArticleService articleService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserRoleDao userRoleDao;

    public UserServiceImpl() {
    }

    @Override //todo  перенести в userDao
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
        if (user == null){
            throw new UsernameNotFoundException("username: " + username +  " not found!");
        }
        return user;
    }

    @Override
    public Long createUser(User user) throws RegistrationException{
        return userDao.create(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.read(id);
    }

    @Override
    public void changeUser(User user) {
        userDao.update(user);
    }

    @Override
    public void removeUser(User user) {
        Set<Comment> comments =  user.getAuthorCommentsSet();
        for (Comment comment : comments){
            commentService.removeComment(comment);
        }
        Set<UserRole> userRoles = user.getUserRoles();
        for (UserRole userRole : userRoles){
            userRoles.remove(userRole);
        }
        userDao.update(user);
        userDao.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getAllDoctorsAndCandidates() {
        return userDao.findAllDoctorsAndCandidates();
    }

    @Override
    public List<User> searchUsers(String userStr) throws SearchException {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(userStr);
            JSONObject jsonObj = (JSONObject) obj;

            String acadStatusStr = (String) jsonObj.get("acadStatus");
            String nameOfUserStr = (String) jsonObj.get("nameOfUser");
            String sciDegreeStr = (String) jsonObj.get("sciDegree");

            Map<String, Object> searchQueryMap = new HashMap<>();

            if (!acadStatusStr.equals("")) {
                UserAcadStatus acadStatus = null;
                try {
                    acadStatus = acadStatusService.findByString(acadStatusStr);
                } catch (Exception e) {
                    acadStatus = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("acadStatus", acadStatus);
            }

            if (!sciDegreeStr.equals("")) {
                UserSciDegree sciDegree = null;
                try {
                    sciDegree = sciDegreeService.findByString(sciDegreeStr);
                } catch (Exception e) {
                    sciDegree = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("sciDegree", sciDegree);
            }

            if (!nameOfUserStr.equals("")) {
                searchQueryMap.put("nameOfUser", nameOfUserStr);
            }

            return userDao.findBySearchQuery(searchQueryMap);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SearchException("Не вдалося виконати пошук. " + e.getMessage());
        }
    }

}
