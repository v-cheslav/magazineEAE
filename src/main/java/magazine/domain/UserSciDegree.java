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
@Table(name = "scientific_degree")
public class UserSciDegree {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "SCI_DEGREE_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long sciDegreeId;

//    @Enumerated(EnumType.STRING)
    @Column(name="sciDegree")
    private String sciDegree;

    @OneToMany(mappedBy = "sciDegree", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
//    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    public UserSciDegree() {
    }


    public UserSciDegree(String sciDegree) {
        this.sciDegree = sciDegree;
    }

    public UserSciDegree(String sciDegree, Set<User> users) {
        this.sciDegree = sciDegree;
        this.users = users;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getSciDegree() {
        return sciDegree;
    }

    public void setSciDegree(String sciDegree) {
        this.sciDegree = sciDegree;
    }

    public Long getSciDegreeId() {
        return sciDegreeId;
    }


    @Override
    public String toString() {
        if (sciDegree.equals("CANDIDATE")){
            return "Кандидат технічних наук";
        } else if (sciDegree.equals("DOCTOR")){
            return "Доктор технічних наук";
        } else if (sciDegree.equals("PHD")){
            return "Doctor of Philosophy";
        } else {
            return "";
        }
    }
}
