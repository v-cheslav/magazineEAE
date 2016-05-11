package magazine.servise;

import magazine.dao.AcadStatusDao;
import magazine.domain.UserAcadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 26.10.2015.
 */
@Service
public class AcadStatusServiceImpl implements AcadStatusService {

    @Autowired
    AcadStatusDao acadStatusDao;

    public AcadStatusServiceImpl() {
    }

    @Override
    public void fillDBWihAcadStatuses() {
        List <String> acadStatuses = Arrays.asList("DOCENT", "RESEARCHER", "PROFESSOR");
        for(String acadStatuse: acadStatuses){
            UserAcadStatus userAcadStatus = new UserAcadStatus(acadStatuse);
            acadStatusDao.create(userAcadStatus);
        }
    }

    @Override
    public Long createAcadStatus(UserAcadStatus acadStatus) {
        return acadStatusDao.create(acadStatus);
    }

    @Override
    public UserAcadStatus getAcadStatus(Long id) {
        return acadStatusDao.reade(id);
    }

    @Override
    public void changeAcadStatus(UserAcadStatus acadStatus) {
        acadStatusDao.update(acadStatus);
    }

    @Override
    public void removeAcadStatus(UserAcadStatus acadStatus) {
        acadStatusDao.delete(acadStatus);
    }

        @Override
    public UserAcadStatus findByString(String acadStatusStr) {
        return acadStatusDao.getByString(acadStatusStr);
    }
}
