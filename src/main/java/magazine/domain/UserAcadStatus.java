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
@Table(name = "academic_status")
public class UserAcadStatus {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "ACAD_STATUS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long acadStatusId;

//    @Enumerated(EnumType.STRING)
    @Column(name="acadStatus")
    private String acadStatus;

    @OneToMany(mappedBy = "acadStatus", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<User> user = new HashSet<>();

    public UserAcadStatus() {
    }

    public UserAcadStatus(String acadStatus) {
        this.acadStatus = acadStatus;
    }

    public String getAcadStatus() {
        return acadStatus;
    }

    public void setAcadStatus(String acadStatus) {
        this.acadStatus = acadStatus;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Long getAcadStatusId() {
        return acadStatusId;
    }

    @Override
    public String toString() {
        if (acadStatus.equals("DOCENT")){
            return "Доцент";
        } else if (acadStatus.equals("PROFESSOR")){
            return "Професор";
        } else if (acadStatus.equals("RESEARCHER")){
            return "Старший науковий співробітник";
        } else {
            return "";
        }
    }

}
