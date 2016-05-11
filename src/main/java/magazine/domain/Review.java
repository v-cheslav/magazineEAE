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
    private String review;//todo rename to ReviewPath

    @Column(name="Review_Status")
    private Boolean status = false;

    @ManyToOne
    @OrderColumn(name="review", insertable = false, updatable = false)
    private Article article;

    @ManyToOne
    private User user;

    public Review() {
    }

//    public Review(String review, Article article, User user) {
//        this.review = review;
//        this.article = article;
//        this.user = user;
//    }

    public Review(Article article, User user) {
        this.article = article;
        this.user = user;
    }



    public Long getReviewId() {
        return reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
