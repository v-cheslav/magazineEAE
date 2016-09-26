package magazine.domain;

import javax.persistence.*;

/**
* Created by pvc on 19.10.2015.
*/
@Entity
@Table(name = "Reviews")
public class Review {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "REVIEW_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="Review_ID")
    private Long reviewId;

    @Column(name="Review_Name")
    private String reviewName;

    @Column(name="Review_Status")
    private Boolean status = false;

    @Column(name="Review_Address")
    private String reviewAddress;

    @ManyToOne
    @OrderColumn(name="reviewName", insertable = false, updatable = false)
    private Article article;

    @ManyToOne
    private User user;

    public Review() {
    }

//    public Review(String reviewName, Article article, User user) {
//        this.reviewName = reviewName;
//        this.article = article;
//        this.user = user;
//    }

    public Review(Article article, User user) {
        this.article = article;
        this.user = user;
    }


    public String getReviewAddress() {
        return reviewAddress;
    }

    public void setReviewAddress(String reviewAddress) {
        this.reviewAddress = reviewAddress;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
