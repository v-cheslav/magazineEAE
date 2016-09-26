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
@DiscriminatorValue("Articles")
public class Article extends Publication {

    @Column(name="Printable")
    private Boolean isPrintable = false;

    @Column(name="ReviewersAssigned")
    private Boolean isReviewersAssigned = false;

    @Column(name="articleFileName")
    private String articleFileName;

    @ManyToOne
    @OrderColumn(name="section")
    @JsonIgnore(true)
    private Section articleSection;

    @OneToOne
    @JoinColumn(name = "annotation")
    @JsonIgnore(true)
    private Annotation articleAnnotations;

    @OneToMany(mappedBy = "article", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore(true)
    private List<Review> articleReviews = new ArrayList<>(2);

    @OneToMany(mappedBy = "article", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @OrderBy (value = "Comment_Date")
    private List<Comment> articleCommentsSet = new LinkedList<>();

    public Article() {
    }

    public Article(Calendar publicationDate, String publicationAddress, User user) {
        super(publicationDate, publicationAddress, user);
    }


    public String getArticleFileName() {
        return articleFileName;
    }

    public void setArticleFileName(String articleFileName) {
        this.articleFileName = articleFileName;
    }

    public Boolean getIsReviewersAssigned() {
        return isReviewersAssigned;
    }

    public void setIsReviewersAssigned(Boolean isReviewersAssigned) {
        this.isReviewersAssigned = isReviewersAssigned;
    }

    public Section getArticleSection() {
        return articleSection;
    }

    public void setArticleSection(Section articleSection) {
        this.articleSection = articleSection;
    }

    public Annotation getArticleAnnotations() {
        return articleAnnotations;
    }

    public void setArticleAnnotations(Annotation articleAnnotations) {
        this.articleAnnotations = articleAnnotations;
    }

    public List<Review> getArticleReviews() {
        return articleReviews;
    }

    public void setArticleReviews(List<Review> articleReviews) {
        this.articleReviews = articleReviews;
    }

    public Boolean getIsPrintable() {
        return isPrintable;
    }

    public void setIsPrintable(Boolean isPrintable) {
        this.isPrintable = isPrintable;
    }

    public List<Comment> getArticleCommentsSet() {
        return articleCommentsSet;
    }

    public void setArticleCommentsSet(List<Comment> articleCommentsSet) {
        this.articleCommentsSet = articleCommentsSet;
    }

    @Override
    public String toString() {
        return "Article{" + '\'' +
                "articleId=" + super.getId() + '\'' +
                ", articleFileName='" + super.getPublicationPath() + '\'' +
                ", user='" + super.getUser().toString() + '\'' +
                ", articleAddress='" + super.getPublicationPath() + '\'' +
                ", articlePublicationDate='" + super.getPublicationDate() + '\'' +
                ", articleSection='" + articleSection + '\'' +
                ", articleKeyWords='" +  + '\'' +
                '}';
    }

}
