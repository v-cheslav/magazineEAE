package magazine.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import magazine.utils.MySimpleDateFormat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
* Created by pvc on 22.10.2015.
*/
@Entity
@Table(name = "Sections")
public class Section {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "SECTION_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long sectionId;

    @Enumerated(EnumType.STRING)
    private ListSection sectionList;

    @OneToMany(mappedBy = "articleSection", fetch= FetchType.EAGER, cascade = CascadeType.MERGE)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    private Set<Article> articles = new HashSet<>();

    public Section() {
    }

    public Section(ListSection sectionList) {
        this.sectionList = sectionList;
    }

    public ListSection getSectionList() {
        return sectionList;
    }

    public void setSectionList(ListSection sectionList) {
        this.sectionList = sectionList;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getSectionStr() {
        String section;
        switch (this.sectionList) {
            case AUTOMATION: section= "Автоматика та робототехнічні системи";
                break;
            case EXPLOITATION: section= "Експлуатація електрообладнання";
                break;
            case MACHINES: section= "Електричні машини";
                break;
            case SUPPLYING: section= "Електропостачання";
                break;
            case DRIVING: section= "Електропривід";
                break;
            case MATHEMATICS: section= "Математика";
                break;
            case HEATENERGY: section= "Теплоенергетика";
                break;
            case PHYSIC: section= "Фізика";
                break;
            default: section= null;
        }
        return section;
    }



    @Override
    public String toString() {
        return "Section Id = " + sectionId
                + ";\nsection list = " + sectionList;
    }


}
