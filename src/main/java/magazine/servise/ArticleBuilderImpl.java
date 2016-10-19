package magazine.servise;

import magazine.Exeptions.PublicationException;
import magazine.domain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Created by pvc on 29.09.2016.
 */
@Service
public class ArticleBuilderImpl implements ArticleBuilder {
    public static final Logger log = Logger.getLogger(UserBuilderImpl.class);

    @Autowired
    SectionService sectionService;
    @Autowired
    KeyWordService keyWordService;
    @Autowired
    AnnotationService annotationService;

    private Article article;

    public ArticleBuilderImpl() {
    }

    @Override
    public Article buildPublication (MultipartHttpServletRequest request) throws PublicationException {

        article = new Article();
        article.setPublicationName(request.getParameter("articleName"));
        article.setArticleSection(getArticleSection(request));
        article.setPublicationKeyWords(getPublicationKeyWords(request));
        article.setArticleAnnotations(getAnnotation(request));
        return article;
    }

    private List<PublicationKeyWord> getPublicationKeyWords (MultipartHttpServletRequest request){
        log.info("getPublicationKeyWords.method");
        String keyWords = request.getParameter("keyWords");
        return keyWordService.getAndSetKeyWordsFromString(keyWords, article);
    }

    private Section getArticleSection (MultipartHttpServletRequest request){
        log.info("getArticleSection.method");
        String section = request.getParameter("articleSection");
        return sectionService.getSectionByName(ListSection.valueOf(section));
    }


    private Annotation getAnnotation (MultipartHttpServletRequest request){
        log.info("getAnnotation.method");

        Annotation annotation = new Annotation(
                request.getParameter("annotationEng"),
                request.getParameter("annotationUa"),
                request.getParameter("annotationRu"),
                article
        );
        annotationService.createAnnotation(annotation);
        return annotation;
    }

}
