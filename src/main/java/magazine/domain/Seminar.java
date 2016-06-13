package magazine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
* Created by pvc on 19.10.2015.
*/
@Entity
//@SecondaryTable(name = "Seminars")
@DiscriminatorValue("Seminars")

//@Inheritance(strategy = InheritanceType.JOINED)
public class Seminar extends Publication {

//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "SEMINAR_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
//    @Column(name="seminarId")
//    private Long seminarId;

//    @Column(name="seminarName")
//    private String seminarName;

    /**
     * As for published seminars this date means the date of publication
     * that seminar in magazine, but as for announced - it's a date when
     * user is going to represent his report
     */
//    @Column(name = "seminarPublishDate")
//    private Calendar seminarPublicationDate;

//    @ManyToOne
//    private User user;

//    @Column(name="Report_Address")
    private String seminarReportAddress = super.getPublicationAddress();

    @Column(name="Presentation_Address")
    private String seminarPresentationAddress;



    @Column(name = "unRegUserName")
    private String unRegUserName;

    @Column(nullable=true, name = "Published")
    private Boolean isPublished;

    @OneToMany(mappedBy = "seminar", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @OrderBy (value = "Comment_Date") //todo may be commentDate
    private List<Comment> seminarCommentsSet = new LinkedList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "seminar_semKeyWord", joinColumns = @JoinColumn(name = "seminar_id"),
            inverseJoinColumns = @JoinColumn(name = "semKeyWord_id"))
    @JsonIgnore(true)
    private Set<PublicationKeyWord> seminarKeyWords = new HashSet<>();

    public Seminar() {
    }

//    public Seminar(String seminarName, Calendar seminarPublicationDate, User user) {
//        this.seminarName = seminarName;
//        this.seminarPublicationDate = seminarPublicationDate;
//        this.user = user;
//    }

    public Seminar(String publicationName, Calendar publicationDate, String publicationAddress, User user) {
        super(publicationName, publicationDate, publicationAddress, user);
    }

//    public Long getSeminarId() {
//        return seminarId;
//    }

//    public void setSeminarId(Long seminarId) {
//        this.seminarId = seminarId;
//    }

//    public String getSeminarName() {
//        return seminarName;
//    }

//    public void setSeminarName(String seminarName) {
//        this.seminarName = seminarName;
//    }

    public String getUnRegUserName() {
        return unRegUserName;
    }

    public void setUnRegUserName(String unRegUserName) {
        this.unRegUserName = unRegUserName;
    }

//    public Calendar getSeminarPublicationDate() {
//        return seminarPublicationDate;
//    }

//    public void setSeminarPublicationDate(Calendar seminarPublicationDate) {
//        this.seminarPublicationDate = seminarPublicationDate;
//    }

//    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getSeminarPresentationAddress() {
        return seminarPresentationAddress;
    }

    public void setSeminarPresentationAddress(String seminarPresentationAddress) {
        this.seminarPresentationAddress = seminarPresentationAddress;
    }

    public String getSeminarReportAddress() {
        return seminarReportAddress;
    }

    public void setSeminarReportAddress(String seminarReportAddress) {
        super.setPublicationAddress(seminarReportAddress);
        this.seminarReportAddress = seminarReportAddress;
    }

    public List<Comment> getSeminarCommentsSet() {
        return seminarCommentsSet;
    }

    public void setSeminarCommentsSet(List<Comment> seminarCommentsSet) {
        this.seminarCommentsSet = seminarCommentsSet;
    }

    public Set<PublicationKeyWord> getSeminarKeyWords() {
        return seminarKeyWords;
    }

    public void setSeminarKeyWords(Set<PublicationKeyWord> seminarKeyWords) {
        this.seminarKeyWords = seminarKeyWords;
    }



//    public String seminarDateToString(){
//        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
//        String seminarPublishDate = dateFormat.getDateFormat().format(this.seminarPublicationDate.getTime());
//        return seminarPublishDate;
//    }

}
