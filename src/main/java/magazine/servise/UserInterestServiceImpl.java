package magazine.servise;

import magazine.dao.UserInterestDao;
import magazine.domain.UserInterest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
