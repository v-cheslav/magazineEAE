package magazine.servise;

import magazine.domain.UserAcadStatus;

/**
 * Created by pvc on 26.10.2015.
 */
public interface AcadStatusService {
    public void fillDBWihAcadStatuses();
    public Long createAcadStatus(UserAcadStatus acadStatus);
    public UserAcadStatus getAcadStatus(Long id);
    public void changeAcadStatus(UserAcadStatus acadStatus);
    public void removeAcadStatus(UserAcadStatus acadStatus);
    public UserAcadStatus findAcadStatus(String acadStatusStr);
}
