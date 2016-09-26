package magazine.servise;

import magazine.dao.UserSexDao;
import magazine.domain.UserSex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 28.10.2015.
 */
@Service
public class UserSexServiceImpl implements UserSexService {

    @Autowired
    UserSexDao userSexDao;

    public UserSexServiceImpl() {
    }

    @Override
    public void fillDBWithUserSex() {
        List<String> userSexes = Arrays.asList("MALE", "FEMALE");
        for(String userSexStr: userSexes){
            UserSex userSex = new UserSex(userSexStr);
            userSexDao.create(userSex);
        }
    }

    @Override
    public UserSex findUserSex(String userSexStr) {
        return userSexDao.getByString(userSexStr);
    }
}
