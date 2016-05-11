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
@Table(name = "Role")
public class UserRole {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "ROLE_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long roleId;


    @Enumerated(EnumType.STRING)
    private ListRole listRole;

    @ManyToMany (mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();

    public UserRole() {
    }

    public UserRole(ListRole listRole) {
        this.listRole = listRole;
    }

    public ListRole getListRole() {
        return listRole;
    }

    public void setListRole(ListRole listRole) {
        this.listRole = listRole;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Set<User> getUsers() {
        return users;
    }
}
