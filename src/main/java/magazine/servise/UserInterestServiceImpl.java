package magazine.servise;

import magazine.dao.UserInterestDao;
import magazine.domain.User;
import magazine.domain.UserInterest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pvc on 26.10.2015.
 */
@Service
public class UserInterestServiceImpl implements UserInterestService {

    @Autowired
    UserInterestDao userInterestDao;

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
        String[] interestsArrStr = interestsStr.split("\\,");
        Set<UserInterest> interests = new HashSet<>();
        if (interestsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            for (String interest : interestsArrStr) {
                if (interest.charAt(0) == ' ') {
                    interest = interest.replaceFirst(" ", "");
                }
                UserInterest userInterest;
                try {
                    userInterest = userInterestDao.getInterest(interest.toLowerCase());
                    Set <User> userSet = userInterest.getUsers();//todo catch exception above and throw another one
                    userSet.add(user);
                    userInterestDao.update(userInterest);
                } catch (NullPointerException e) {//якщо в БД немає interest
                    userInterest = new UserInterest(interest.toLowerCase());
                    Set<User> userSet = new HashSet<>();
                    userInterest.setUsers(userSet);
                    userSet.add(user);
                    userInterestDao.create(userInterest);
                }
                interests.add(userInterest);
            }
        } else {
            interests = null;
        }
        return interests;
    }

}
