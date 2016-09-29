package magazine.servise;

import magazine.dao.UserInterestDao;
import magazine.domain.User;
import magazine.domain.UserInterest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pvc on 26.10.2015.
 */
@Service
public class UserInterestServiceImpl implements UserInterestService {
    public static final Logger log = Logger.getLogger(UserInterestServiceImpl.class);

    @Autowired
    UserInterestDao userInterestDao;

    @Autowired
    KeyWordService keyWordService;

    public UserInterestServiceImpl() {
    }

    @Override
    public Long addInterest(UserInterest userInterest) {
        return userInterestDao.create(userInterest);
    }


    @Override
    public UserInterest getByInterest(String userInterest) {
        return userInterestDao.getInterest(userInterest);
    }

    @Override
    public Set<UserInterest> setUserInterests(String interestsStr, User user){
        log.info("setUserInterests.class");

        if (!keyWordService.isStringCorrect(interestsStr)) return null;
        String[] interestsStrArr = interestsStr.split("\\,");

        Set<UserInterest> interests = new HashSet<>();
        for (String interest : interestsStrArr) {
            interest = keyWordService.removeBlanksFromBeginning(interest);
            UserInterest userInterest = getInterestByString(interest, user);
            interests.add(userInterest);
        }
        return interests;
    }


    private UserInterest getInterestByString(String interest, User user) {
        log.info("convertStringToInterest.method");
        try {
            return getInterestFromDbAndAddUser(interest, user);
        } catch (NullPointerException e) {//якщо в БД немає interest
            return createNewInterestAndAddUser(interest, user);
        }
    }

    private UserInterest getInterestFromDbAndAddUser(String interest, User user) {
        log.info("getting interest from DB");
        UserInterest userInterest = userInterestDao.getInterest(interest.toLowerCase());
        Set<User> userSet = userInterest.getUsers();
        userSet.add(user);
        userInterestDao.update(userInterest);
        return userInterest;
    }

    private UserInterest createNewInterestAndAddUser(String interest, User user) {
        log.info("creating a new interest");
        UserInterest userInterest = new UserInterest(interest.toLowerCase());
        Set<User> userSet = new HashSet<>();
        userInterest.setUsers(userSet);
        userSet.add(user);
        userInterestDao.create(userInterest);
        return userInterest;
    }


}
