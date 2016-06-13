package magazine.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pvc on 26.10.2015.
 */
@Entity
@DiscriminatorValue("UsersInterests")
//@SecondaryTable(name = "UsersInterests")
public class UserInterest extends KeyWord {


//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "USERINTEREST_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
//    @Column(name="ID")
//    private Long userInterestId;

    @ManyToMany (mappedBy = "interests", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();

    public UserInterest (){
    }

    public UserInterest(String keyWord){
        super(keyWord);
    }
//    public UserInterest (String keyWord){
//        super(keyWord);
//    }

//    @Column(name="Interest")
//    private String interest;



//    public Long getUserInterestId() {
//        return userInterestId;
//    }

//    public UserInterest(String interest) {
//        this.interest = interest;
//    }

//    public String getInterest() {
//        return interest;
//    }

//    public void setInterest(String interest) {
//        this.interest = interest;
//    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
