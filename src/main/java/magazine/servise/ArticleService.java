package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.ArticleNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 29.11.2015.
 */
public interface ArticleService {
    public Long createArticle(Article article)throws ArticleCreationException;
    public Article getArticle(Long id);
    public void changeArticle(Article article);
    public void removeArticle(Article article);
    public List<Article> findPublishArticleBySection(String section);//todo throw Exception connection error or so.
    public List<Article> findAllUnpublished();
    public List<Article> findWithoutReviewers();
    public Article findUnPublishedByUser(User user);
    public Article findUnPublishedByReviewer(User reviewer);
    public List<Article> findNewestArticles() throws ArticleNotFoundException;
//    public Long createByString(String article, User currentUser) throws ArticleCreationException;
    public List<String> annotationReader (String annotationPath);
    public Set<PublicationKeyWord> userInterestFormer (String keyWordsStr, Article article);
    public List<Article> findByUserId(Long userId);
    public List<Article> findByReviewerId(Long userId);
    public List<Article> searchArticles(String articleStr) throws SearchException;
    public List<Article> findArticlesByKeywords(Article article);
//    public void saveFile (Publication article, MultipartFile multipartFile) throws ArticleCreationException;
}
