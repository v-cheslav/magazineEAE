//package magazine.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import magazine.utils.MySimpleDateFormat;
//
//import javax.persistence.*;
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by pvc on 25.05.2016.
// */
//@Entity
//@Table(name = "Publications")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name="PUBL_TYPE")
//public class Publication {
//
//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "PUBLICATION_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
//    @Column(name="publicationId")
//    private Long publicationId;
//
//    @Column(name="publicationName")
//    private String publicationName;
//
//    @Column(name = "publicationDate")
//    private Calendar publicationDate;
//
//    @Column(name="publicationAddress")
//    private String publicationAddress;
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "publicationKeyWords",
//            joinColumns = @JoinColumn(name = "publication_id"),
//            inverseJoinColumns = @JoinColumn(name = "publicationKeyWord_id"))
//    @JsonIgnore(true)
//    private Set<PublicationKeyWord> publicationKeyWords = new HashSet<>();
//
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private User user;
//
//    public Publication() {
//    }
//
//    public Publication(String publicationName, Calendar publicationDate, String publicationAddress, User user) {
//        this.publicationAddress = publicationAddress;
//        this.publicationDate = publicationDate;
//        this.publicationName = publicationName;
//    }
//
//    public Publication getPublicationForTables (Publication publication){
//        this.publicationId = publication.getId();
//        this.publicationName = publication.getPublicationName();
//        this.publicationDate = publication.getPublicationDate();
//        return this;
//    }
//
//    public String publicationDateToString(){
//        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
//        String publishDate = dateFormat.getDateFormat().format(this.publicationDate.getTime());
//        return publishDate;
//    }
//
//    public Long getId() {
//        return publicationId;
//    }
//
//    public void setId(Long id) {
//        this.publicationId = id;
//    }
//
//    public String getPublicationName() {
//
//        return publicationName;
//    }
//
//    public void setPublicationName(String publicationName) {
//        this.publicationName = publicationName;
//    }
//
//    public Calendar getPublicationDate() {
//        return publicationDate;
//    }
//
//    public void setPublicationDate(Calendar publicationDate) {
//        this.publicationDate = publicationDate;
//    }
//
//    public String getPublicationAddress() {
//        return publicationAddress;
//    }
//
//    public void setPublicationAddress(String publicationAddress) {
//        this.publicationAddress = publicationAddress;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Set<PublicationKeyWord> getPublicationKeyWords() {
//        return publicationKeyWords;
//    }
//
//    public void setPublicationKeyWords(Set<PublicationKeyWord> publicationKeyWords) {
//        this.publicationKeyWords = publicationKeyWords;
//    }
//}
