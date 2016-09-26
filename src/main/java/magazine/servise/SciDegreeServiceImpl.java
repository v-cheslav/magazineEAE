package magazine.servise;

import magazine.dao.SciDegreeDao;
import magazine.domain.UserSciDegree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 26.10.2015.
 */
@Service
public class SciDegreeServiceImpl implements SciDegreeService {

    @Autowired
    SciDegreeDao sciDegreeDao;

    public SciDegreeServiceImpl() {
    }

    @Override
    public void fillDBWihSciDegree() {
        List<String> sciDegrees = Arrays.asList("PHD", "CANDIDATE", "DOCTOR");
        for(String sciDegree: sciDegrees){
            UserSciDegree userUserSciDegree = new UserSciDegree(sciDegree);
            sciDegreeDao.create(userUserSciDegree);
        }
    }

    @Override
    public UserSciDegree finSciDegree(String sciDegreeStr) {
        return sciDegreeDao.getByString(sciDegreeStr);
    }
}
