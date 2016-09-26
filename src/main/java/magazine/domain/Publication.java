package magazine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import magazine.utils.MySimpleDateFormat;
import javax.persistence.*;
import java.util.*;

/**
* Created by pvc on 25.05.2016.
*/
@Entity
@Table(name = "Publications")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="PUBL_TYPE")
public class Publication {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "PUBLICATION_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="publicationId")
    private Long publicationId;

    @Column(name = "publicationDate")
    private Calendar publicationDate;

    @Column(name="publicationPath")
    private String publicationPath;

    @Column(name="publicationName")
    private String publicationName;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "publicationKeyWords",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "publicationKeyWord_id"))
    @JsonIgnore(true)
    private List<PublicationKeyWord> publicationKeyWords = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public Publication() {
    }

    public Publication(Calendar publicationDate, String publicationPath, User user) {
        this.publicationPath = publicationPath;
        this.publicationDate = publicationDate;
        this.user = user;
    }


    public String publicationDateToString(){
        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
        String publishDate = dateFormat.getDateFormat().format(this.publicationDate.getTime());
        return publishDate;
    }

    public Long getId() {
        return publicationId;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public void setId(Long id) {
        this.publicationId = id;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationPath() {
        return publicationPath;
    }

    public void setPublicationPath(String publicationPath) {
        this.publicationPath = publicationPath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PublicationKeyWord> getPublicationKeyWords() {
        return publicationKeyWords;
    }

    public void setPublicationKeyWords(List<PublicationKeyWord> publicationKeyWords) {
        this.publicationKeyWords = publicationKeyWords;
    }
}
