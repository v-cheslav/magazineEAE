package magazine.dao;

import magazine.domain.UserAcadStatus;

/**
 * Created by pvc on 26.10.2015.
 */
public interface AcadStatusDao {
    public Long create(UserAcadStatus acadStatus);
    public UserAcadStatus reade(Long id);
    public void update(UserAcadStatus acadStatus);
    public void delete(UserAcadStatus acadStatus);
    public UserAcadStatus getByString(String acadStatusStr);
}
