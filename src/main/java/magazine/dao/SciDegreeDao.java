package magazine.dao;

import magazine.domain.UserSciDegree;

/**
 * Created by pvc on 26.10.2015.
 */
public interface SciDegreeDao {
    public UserSciDegree getByString(String sciDegreeStr);
    public Long create (UserSciDegree userSciDegree);
}
