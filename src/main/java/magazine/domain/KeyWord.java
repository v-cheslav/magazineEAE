package magazine.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pvc on 27.10.2015.
 */
@Entity
@Table(name = "KeyWords")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="KeyWords_TYPE")
public class KeyWord {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "KEYWORDS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long keyWordId;

    @Column(name="KeyWord")
    private String keyWord;

    public KeyWord (){
    }

    public KeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getKeyWordId() {
        return keyWordId;
    }

    public void setKeyWordId(Long keyWordId) {
        keyWordId = keyWordId;
    }

    public String getArtKeyWord() {
        return keyWord;
    }

    public void setArtKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
