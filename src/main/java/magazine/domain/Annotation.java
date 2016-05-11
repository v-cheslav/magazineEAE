package magazine.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
* Created by pvc on 19.10.2015.
*/
@Entity
@Table(name = "Annotations")
public class Annotation {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "ANNOTATION_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="ID")
    private Long annotationId;

    @Column(name="Annotation_Eng")
    private String annotationEng;

    @Column(name="Annotation_Ua")
    private String annotationUa;

    @Column(name="Annotation_Ru")
    private String annotationRu;

    @OneToOne(mappedBy = "articleAnnotations", fetch= FetchType.LAZY, cascade = CascadeType.MERGE)
    @Fetch(FetchMode.SELECT)
    private Article article;

    public Annotation() {
    }


    public Annotation(String annotationEng, String annotationUa, String annotationRu, Article article) {
        this.annotationEng = annotationEng;
        this.annotationUa = annotationUa;
        this.annotationRu = annotationRu;
        this.article = article;
    }

    public Annotation(String annotationEng, String annotationUa, String annotationRu) {
        this.annotationEng = annotationEng;
        this.annotationUa = annotationUa;
        this.annotationRu = annotationRu;
    }

    public Long getAnnotationId() {
        return annotationId;
    }

    public String getAnnotationEng() {
        return annotationEng;
    }

    public void setAnnotationEng(String annotationEng) {
        this.annotationEng = annotationEng;
    }

    public String getAnnotationUa() {
        return annotationUa;
    }

    public void setAnnotationUa(String annotationUa) {
        this.annotationUa = annotationUa;
    }

    public String getAnnotationRu() {
        return annotationRu;
    }

    public void setAnnotationRu(String annotationRu) {
        this.annotationRu = annotationRu;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
