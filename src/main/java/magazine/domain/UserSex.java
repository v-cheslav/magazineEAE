package magazine.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
* Created by pvc on 22.10.2015.
*/
@Entity
@Table(name = "sex")
public class UserSex {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "USER_SEX_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long userSexId;

//    @Enumerated(EnumType.STRING)
@Column(name="userSex")
    private String userSex;

    @OneToMany(mappedBy = "userSex", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
//    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    public UserSex() {
    }

    public UserSex(Set<User> users) {
        this.users = users;
    }



    public Long getUserSexId() {
        return userSexId;
    }

    public UserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
