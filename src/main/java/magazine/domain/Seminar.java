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
@DiscriminatorValue("Seminars")

public class Seminar extends Publication {


    private String seminarReportAddress = super.getPublicationPath();

    @Column(name="presentationFileName")
    private String presentationFileName;

    @Column(name="reportFileName")
    private String reportFileName;

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

    public Seminar(Calendar publicationDate, String publicationAddress, User user) {
        super(publicationDate, publicationAddress, user);
    }


    public String getPresentationFileName() {
        return presentationFileName;
    }

    public void setPresentationFileName(String presentationFileName) {
        this.presentationFileName = presentationFileName;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getUnRegUserName() {
        return unRegUserName;
    }

    public void setUnRegUserName(String unRegUserName) {
        this.unRegUserName = unRegUserName;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getSeminarReportAddress() {
        return seminarReportAddress;
    }

    public void setSeminarReportAddress(String seminarReportAddress) {
        super.setPublicationPath(seminarReportAddress);
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



}
