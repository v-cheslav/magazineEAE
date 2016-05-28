package magazine.domain;

import magazine.utils.MySimpleDateFormat;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by pvc on 25.05.2016.
 */
@Entity
@Table(name = "Publications")
@Inheritance(strategy = InheritanceType.JOINED)
public class PublicationTemp {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "PUBLICATION_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="publicationId")
    private Long publicationId;

    @Column(name="publicationName")
    private String publicationName;

    @Column(name = "publicationDate")
    private Calendar publicationDate;

    @Column(name="publicationAddress")
    private String publicationAddress;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public PublicationTemp() {
    }

    public PublicationTemp(String publicationName, Calendar publicationDate, String publicationAddress, User user) {
        this.publicationAddress = publicationAddress;
        this.publicationDate = publicationDate;
        this.publicationName = publicationName;
    }

    public PublicationTemp getPublicationForTables (PublicationTemp publicationTemp){
        this.publicationId = publicationTemp.getId();
        this.publicationName = publicationTemp.getPublicationName();
        this.publicationDate = publicationTemp.getPublicationDate();
        return this;
    }

    public String publicationDateToString(){
        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
        String publishDate = dateFormat.getDateFormat().format(this.publicationDate.getTime());
        return publishDate;
    }

    public Long getId() {
        return publicationId;
    }

    public void setId(Long id) {
        this.publicationId = id;
    }

    public String getPublicationName() {

        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationAddress() {
        return publicationAddress;
    }

    public void setPublicationAddress(String publicationAddress) {
        this.publicationAddress = publicationAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
