package magazine.dao;


import magazine.domain.UserInterest;

/**
* Created by pvc on 20.10.2015.
*/
public interface UserInterestDao {
    public Long create(UserInterest userInterest);
    public UserInterest read(Long id);
    public void update(UserInterest userInterest);
    public void delete(UserInterest userInterest);
    public UserInterest getInterest (String userInterest);
}
