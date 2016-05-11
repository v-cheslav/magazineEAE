package magazine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import magazine.utils.MySimpleDateFormat;

import javax.persistence.*;
import java.util.Calendar;

/**
* Created by pvc on 19.10.2015.
*/
@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "COMMENT_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="Comment_ID")
    private Long commentId;

    @Column(name = "Comment_Date")
    private Calendar commentDate;

    @Column(name = "Comment_Txt")
    private String comment;

    @ManyToOne
//    @JsonBackReference
    @JsonIgnore(true)
    private User user;

    @ManyToOne
//    @JsonBackReference
    @JsonIgnore(true)
    private Article article;

    @ManyToOne
//    @JsonBackReference
    @JsonIgnore(true)
    private Seminar seminar;

    public Comment() {
    }

    public Comment(Calendar commentDate, String comment, User user) {
        this.commentDate = commentDate;
        this.comment = comment;
        this.user = user;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Calendar getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Calendar commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public String commentDateToString(){
        MySimpleDateFormat dateFormat = new MySimpleDateFormat();
        String publishDate = dateFormat.getDateFormat().format(this.commentDate.getTime());
        return publishDate;
    }

}
