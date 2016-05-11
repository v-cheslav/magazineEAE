//package magazine.domain;
//
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//
//import javax.persistence.*;
//import java.util.*;
//
///**
// * Created by pvc on 29.02.2016.
// */
//@Entity
//@Table(name = "PublishedSeminars")
//public class PublishedSeminar extends Seminar {
//
//    @Column(name="Seminar_Presentation_Address")
//    private String seminarPresentationAddress;
//
//    @Column(name="Seminar_Report_Address")
//    private String seminarReportAddress;
//
//    @OneToMany(mappedBy = "seminar", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SELECT)
////    @JsonManagedReference
//    @OrderBy (value = "Comment_Date") //todo may be commentDate
//    private List<Comment> seminarCommentsSet = new LinkedList<>();
//
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "seminar_semKeyWord", joinColumns = @JoinColumn(name = "seminar_id"),
//            inverseJoinColumns = @JoinColumn(name = "semKeyWord_id"))
//    private Set<SeminarKeyWord> seminarKeyWords = new HashSet<>();
//
//
//    public PublishedSeminar() {
//    }
//
//
//    public PublishedSeminar(String seminarName, Calendar seminarPublicationDate, User user, Set<SeminarKeyWord> seminarKeyWords, String seminarPresentationAddress, String seminarReportAddress, List<Comment> seminarCommentsSet, Set<SeminarKeyWord> seminarKeyWords1) {
//        super(seminarName, seminarPublicationDate, user);
//        this.seminarPresentationAddress = seminarPresentationAddress;
//        this.seminarReportAddress = seminarReportAddress;
//        this.seminarCommentsSet = seminarCommentsSet;
//        seminarKeyWords = seminarKeyWords1;
//    }
//
//
//
//    public String getSeminarPresentationAddress() {
//        return seminarPresentationAddress;
//    }
//
//    public void setSeminarPresentationAddress(String seminarPresentationAddress) {
//        this.seminarPresentationAddress = seminarPresentationAddress;
//    }
//
//    public String getSeminarReportAddress() {
//        return seminarReportAddress;
//    }
//
//    public void setSeminarReportAddress(String seminarReportAddress) {
//        this.seminarReportAddress = seminarReportAddress;
//    }
//
//    public List<Comment> getSeminarCommentsSet() {
//        return seminarCommentsSet;
//    }
//
//    public void setSeminarCommentsSet(List<Comment> seminarCommentsSet) {
//        this.seminarCommentsSet = seminarCommentsSet;
//    }
//
//    public Set<SeminarKeyWord> getSeminarKeyWords() {
//        return seminarKeyWords;
//    }
//
//    public void setSeminarKeyWords(Set<SeminarKeyWord> seminarKeyWords) {
//        this.seminarKeyWords = seminarKeyWords;
//    }
//}
