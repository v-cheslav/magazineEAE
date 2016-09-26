package magazine.servise;

import magazine.domain.UserSex;

/**
 * Created by pvc on 28.10.2015.
 */
public interface UserSexService {
    public void fillDBWithUserSex();
    public UserSex findUserSex(String userSexStr);
}
