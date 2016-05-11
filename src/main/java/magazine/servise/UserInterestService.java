package magazine.servise;

import magazine.domain.UserInterest;

/**
 * Created by pvc on 26.10.2015.
 */
public interface UserInterestService {
    public Long addInterest (UserInterest userInterest);
    public UserInterest getByInterest (String userInterest);
}
