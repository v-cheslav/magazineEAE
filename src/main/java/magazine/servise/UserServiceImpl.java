package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SuchUserExistException;
import magazine.dao.UserDao;
import magazine.domain.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    public static final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Value("${initialPath}")
    private String initialPath;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AcadStatusService acadStatusService;

    @Autowired
    private SciDegreeService sciDegreeService;

    @Autowired
    private CommentService commentService;


    public UserServiceImpl() {
    }


    @Override
    public Long createUser(User user) throws RegistrationException{
        return userDao.create(user);
    }

    @Override
    public void increaseUserPublications (User user) {
        int publNumber = user.getPublicationNumber();
        user.setPublicationNumber(++publNumber);
        changeUser(user);
    }

    @Override
    public void updateUser(User oldUser, User newUser) throws RegistrationException {
        setUpdatedParameters(oldUser, newUser);
        userDao.update(oldUser);
    }


    private void setUpdatedParameters(User oldUser, User newUser) {
        log.info("setUpdatedParameters method");

        if (oldUser == null || newUser == null){
            throw new IllegalArgumentException("User couldn't be null");
        }

        if (!newUser.getUsername().equals("")){
            oldUser.setUserName(newUser.getUsername());
        }
        if (!newUser.getPassword().equals("")){
            System.err.println("password " + newUser.getPassword());
            oldUser.setPassword(newUser.getPassword());
        }
        if (!newUser.getName().equals("")){
            oldUser.setName(newUser.getName());
        }
        if (!newUser.getSurname().equals("")){
            oldUser.setSurname(newUser.getSurname());
        }
        if (!newUser.getMiddleName().equals("")){
            oldUser.setMiddleName(newUser.getMiddleName());
        }
        if (!newUser.getUniversity().equals("")){
            oldUser.setUniversity(newUser.getUniversity());
        }
        if (!newUser.getInstitute().equals("")){
            oldUser.setInstitute(newUser.getInstitute());
        }
        if (!newUser.getChair().equals("")){
            oldUser.setChair(newUser.getChair());
        }
        if (!newUser.getPosition().equals("")){
            oldUser.setPosition(newUser.getPosition());
        }
        if (!newUser.getPhone().equals("")){
            oldUser.setPhone(newUser.getPhone());
        }
        if (newUser.getAcadStatus() != null){
            oldUser.setAcadStatus(newUser.getAcadStatus());
        }
        if (newUser.getSciDegree() != null){
            oldUser.setSciDegree(newUser.getSciDegree());
        }
        if (newUser.getUserSex() != null){
            oldUser.setUserSex(newUser.getUserSex());
        }
    }


    @Override
    public boolean checkIfUserExist (String username) throws SuchUserExistException {
        log.info("checkIfUserExist method");
        if (username.equals("")){
            log.error("Електронна адреса username не вказана.");
            throw new IllegalArgumentException("Електронна адреса username не вказана.");
        }
        try {//спробуємо знайти юзера за username
            getUserByUserName(username);//якщо існує кидаємо RegistrationException
            log.error("Користувач з поштою \"" + username + "\" існує. Спробуйте іншу.");
            throw new SuchUserExistException("Користувач з поштою \"" + username + "\" існує. Спробуйте іншу.");
        } catch (UsernameNotFoundException e) {
            log.debug("Реєстрація нового користувача, або заміна його username: " + username);
            return false;
        }
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
                    acadStatus = acadStatusService.findAcadStatus(acadStatusStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchQueryMap.put("acadStatus", acadStatus);
            }

            if (!sciDegreeStr.equals("")) {
                UserSciDegree sciDegree = null;
                try {
                    sciDegree = sciDegreeService.finSciDegree(sciDegreeStr);
                } catch (Exception e) {
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


    @Override
    public User getUserByUserName(String userName) throws UsernameNotFoundException{
        return userDao.findByUsername(userName);
    }






}