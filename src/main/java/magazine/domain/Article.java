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
//@SecondaryTable(name = "Articles")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Article extends Publication {

//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "ARTICLE_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
//    @Column(name="ARTICLE_ID")
//    private Long articleId;

//    @Column(name="articleName")
//    private String articleName;

//    @Column(name = "article_Pub_Date")
//    private Calendar articlePublicationDate;

//    @Column(name="articleAddress")
//    private String articleAddress;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private User user;

//    @Column(name="Image")
//    private String articleImgAddress;

    @Column(name="Printable")
    private Boolean isPrintable = false;

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
    private List<Review> articleReviewers = new LinkedList<>();//todo rename to articleReviews

    @OneToMany(mappedBy = "article", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @OrderBy (value = "Comment_Date")
    private List<Comment> articleCommentsSet = new LinkedList<>();

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "articleKeyWords", joinColumns = @JoinColumn(name = "article_id"),
//            inverseJoinColumns = @JoinColumn(name = "articleKeyWord_id"))
//    @JsonIgnore(true)
//    private Set<PublicationKeyWord> articlesKeyWordSet/* = new HashSet<>()*/;

    public Article() {
    }

    public Article(String publicationName, Calendar publicationDate, String publicationAddress, User user) {
        super(publicationName, publicationDate, publicationAddress, user);
    }

//    public Article(String articleName, Section articleSection) {
//        super. = articleName;
//        this.articleSection = articleSection;
//    }

//    public Article(String articleName, String articleAddress, Calendar articlePublicationDate, Section articleSection, String articleImgAddress, Annotation articleAnnotations, User user, Set<ArticleKeyWord> articleKeyWords) {
//        this.articleName = articleName;
//        this.articleAddress = articleAddress;
//        this.articlePublicationDate = articlePublicationDate;
//        this.articleSection = articleSection;
//        this.articleImgAddress = articleImgAddress;
//        this.articleAnnotations = articleAnnotations;
//        this.user = user;
//        this.articleKeyWords = articleKeyWords;
//    }

//    public Article getArticleForTables (Article article){
//        this.articleId = article.getArticleId();
//        this.articleName = article.getArticleName();
//        this.articlePublicationDate = article.getArticlePublicationDate();
//        return this;
//    }

//    public void setArticleId(Long articleId) {
//        this.articleId = articleId;
//    }

//    public Long getArticleId() {
//        return articleId;
//    }

//    public String getArticleName() {
//        return articleName;
//    }

//    public void setArticleName(String articleName) {
//        this.articleName = articleName;
//    }

//    public String getArticleAddress() {
//        return articleAddress;
//    }

//    public void setArticleAddress(String articleAddress) {
//        this.articleAddress = articleAddress;
//    }

//    public Calendar getArticlePublicationDate() {
//        return articlePublicationDate;
//    }

//    public void setArticlePublicationDate(Calendar articlePublicationDate) {
//        this.articlePublicationDate = articlePublicationDate;
//    }



    public Section getArticleSection() {
        return articleSection;
    }

    public void setArticleSection(Section articleSection) {
        this.articleSection = articleSection;
    }

//    public String getArticleImgAddress() {
//        return articleImgAddress;
//    }

//    public void setArticleImgAddress(String articleImgAddress) {
//        this.articleImgAddress = articleImgAddress;
//    }

    public Annotation getArticleAnnotations() {
        return articleAnnotations;
    }

    public void setArticleAnnotations(Annotation articleAnnotations) {
        this.articleAnnotations = articleAnnotations;
    }

    public List<Review> getArticleReviewers() {
        return articleReviewers;
    }

    public void setArticleReviewers(List<Review> articleReviewers) {
        this.articleReviewers = articleReviewers;
    }

    public Boolean getIsPrintable() {
        return isPrintable;
    }

    public void setIsPrintable(Boolean isPrintable) {
        this.isPrintable = isPrintable;
    }

//    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public List<Comment> getArticleCommentsSet() {
        return articleCommentsSet;
    }

    public void setArticleCommentsSet(List<Comment> articleCommentsSet) {
        this.articleCommentsSet = articleCommentsSet;
    }

//    public Set<PublicationKeyWord> getPublicationKeyWords() {
//        return publicationKeyWords;
//    }
//
//    public void setPublicationKeyWords(Set<PublicationKeyWord> publicationKeyWords) {
//        this.publicationKeyWords = publicationKeyWords;
//    }


//    public Set<PublicationKeyWord> getArticlesKeyWordSet() {
//        return articlesKeyWordSet;
//    }
//
//    public void setArticlesKeyWordSet(Set<PublicationKeyWord> articlesKeyWordSet) {
//        this.articlesKeyWordSet = articlesKeyWordSet;
//    }

    @Override
    public String toString() {
        return "Article{" + '\'' +
                "articleId=" + super.getId() + '\'' +
                ", articleName='" + super.getPublicationName() + '\'' +
                ", user='" + super.getUser().toString() + '\'' +
                ", articleAddress='" + super.getPublicationAddress() + '\'' +
                ", articlePublicationDate='" + super.getPublicationDate() + '\'' +
                ", articleSection='" + articleSection + '\'' +
                ", articleKeyWords='" +  + '\'' +
                '}';
    }


//    public String articleDateToString(){
//        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
//        String articlePublishDate = dateFormat.getDateFormat().format(this.articlePublicationDate.getTime());
//        return articlePublishDate;
//    }
}
