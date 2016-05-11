package magazine.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
* Created by pvc on 27.10.2015.
*/
@Entity
@Table(name = "ArticleKeyWords")
public class ArticleKeyWord /*extends KeyWord*/  {


    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "ARTICLEKW_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long ArticleKeyWordId;

    public ArticleKeyWord (){
    }

    public ArticleKeyWord(String artKeyWord) {
        this.artKeyWord = artKeyWord;
    }

    //    public ArticleKeyWord (String keyWord){
//        super(keyWord);
//    }

    @Column(name="artKeyWord")
    private String artKeyWord;

    @ManyToMany(mappedBy = "articleKeyWords", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Article> articles = new HashSet<>();

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
