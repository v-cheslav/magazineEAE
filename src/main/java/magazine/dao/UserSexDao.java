package magazine.dao;


import magazine.domain.UserSex;

/**
 * Created by pvc on 28.10.2015.
 */
public interface UserSexDao {
    public Long create(UserSex userSex);
    public UserSex read(Long id);
    public void update(UserSex userSex);
    public void delete(UserSex userSex);
    public UserSex getByString(String userSex);
}
