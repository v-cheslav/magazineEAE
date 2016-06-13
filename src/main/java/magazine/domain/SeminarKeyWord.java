//package magazine.domain;
//
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
///**
//* Created by pvc on 27.10.2015.
//*/
//@Entity
//@SecondaryTable(name = "SeminarKeyWords")
//public class SeminarKeyWord extends KeyWord {
////
////    @Id
////    @SequenceGenerator(name = "sequence", sequenceName = "SEMINARKW_SEQ", allocationSize = 1, initialValue = 1)
////    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
////    @Column(name="ID")
////    private Long seminarKeyWordId;
//
//    @ManyToMany(mappedBy = "seminarKeyWords", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private Set<Seminar> seminars = new HashSet<>();
//
//    public SeminarKeyWord (){
//    }
//
//    public SeminarKeyWord(String keyWord){
//        super(keyWord);
//    }
////    public SeminarKeyWord(String semKeyWord) {
////        this.semKeyWord = semKeyWord;
////    }
//////    public SeminarKeyWord (String keyWord){
//////        super(keyWord);
//////    }
//
////    @Column(name="semKeyWord")
////    private String semKeyWord;
//
//
//
//    public Set<Seminar> getSeminars() {
//        return seminars;
//    }
//
//    public void setSeminars(Set<Seminar> seminars) {
//        this.seminars = seminars;
//    }
//}
