//package magazine.domain;
//
//import javax.persistence.*;
//
///**
//* Created by pvc on 19.10.2015.
//*/
//@MappedSuperclass
//public class KeyWord {
//
//    @Id
//    @SequenceGenerator(name = "sequence", sequenceName = "KEYWORD_SEQ", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence")
//    @Column(name="ID")
//    private Long keyWordId;
//
//    @Column(name="Key_Word")
//    private String keyWord;
//
//
////    private User user;
////
////    @ManyToOne
//////    @JsonBackReference
////    @OrderColumn(name="articleKeyWords")
////    private Article article;
////
////    @ManyToOne
//////    @JsonBackReference
////    @OrderColumn(name="seminarKeyWords")
////    private Seminar seminar;
//
//    public KeyWord() {
//    }
//
//
//    public KeyWord(String keyWord) {
//        this.keyWord = keyWord;
//    }
//
////    public KeyWord(String keyWord, User user) {
////        this.keyWord = keyWord;
////        this.user = user;
////    }
//
//    public Long getKeyWordId() {
//        return keyWordId;
//    }
//
//    public String getKeyWord() {
//        return keyWord;
//    }
//
//    public void setKeyWord(String keyWord) {
//        this.keyWord = keyWord;
//    }
//
////    public User getUser() {
////        return user;
////    }
////
////    public void setUser(User user) {
////        this.user = user;
////    }
////
////    public Article getArticle() {
////        return article;
////    }
////
////    public void setArticle(Article article) {
////        this.article = article;
////    }
////
////    public Seminar getSeminar() {
////        return seminar;
////    }
////
////    public void setSeminar(Seminar seminar) {
////        this.seminar = seminar;
////    }
//}