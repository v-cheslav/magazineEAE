package magazine.servise;

import magazine.domain.User;
import magazine.domain.UserInterest;

import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 26.10.2015.
 */
public interface UserInterestService {
    public Long addInterest (UserInterest userInterest);
    public UserInterest getByInterest (String userInterest);
    public Set<UserInterest> setUserInterests(String interestsStr, User user);
}
