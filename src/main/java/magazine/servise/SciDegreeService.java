package magazine.servise;

import magazine.domain.UserSciDegree;

/**
 * Created by pvc on 26.10.2015.
 */
public interface SciDegreeService {
    public void fillDBWihSciDegree();
    public UserSciDegree finSciDegree(String sciDegreeStr);
}
