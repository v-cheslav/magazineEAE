//package magazine.domain;
//
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//
//import javax.persistence.*;
//import java.util.Calendar;
//
///**
// * Created by pvc on 10.03.2016.
// */
//@Entity
//@Table(name = "Messages")
//public class Message {
//
//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "MESSAGE_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
//    @Column(name="id")
//    private Long messageId;
//
//
//    @OneToOne(mappedBy = "message", fetch= FetchType.LAZY, cascade = CascadeType.MERGE)
//    @Fetch(FetchMode.SELECT)
//    private User user;
//
//    @Column(name = "reporter")
//    private Boolean isReporter = false;
//
////    @Column
////    private String articlePublishMessage;
//
//    @Enumerated(EnumType.STRING)
//    private ReviewStages firstReviewStage;
//
//    @Enumerated(EnumType.STRING)
//    private ReviewStages secondReviewStage;
//
//    public Message() {
//    }
//
//    public Long getMessageId() {
//        return messageId;
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
//    public ReviewStages getFirstReviewStage() {
//        return firstReviewStage;
//    }
//
//    public void setFirstReviewStage(ReviewStages firstReviewStage) {
//        this.firstReviewStage = firstReviewStage;
//    }
//
//    public ReviewStages getSecondReviewStage() {
//        return secondReviewStage;
//    }
//
//    public void setSecondReviewStage(ReviewStages secondReviewStage) {
//        this.secondReviewStage = secondReviewStage;
//    }
//
//    public Boolean getIsReporter() {
//        return isReporter;
//    }
//
//    public void setIsReporter(Boolean isReporter) {
//        this.isReporter = isReporter;
//    }
//}
