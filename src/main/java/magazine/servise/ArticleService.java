package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.ArticleNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.domain.Article;
import magazine.domain.Section;
import magazine.domain.User;
import magazine.domain.UserAcadStatus;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pvc on 29.11.2015.
 */
public interface ArticleService {
    public Long createArticle(Article article);
    public Article getArticle(Long id);
    public void changeArticle(Article article);
    public void removeArticle(Article article);
    public List<Article> findPublishArticleBySection(String section);//todo throw Exception connection error or so.
    public Article findUnPublishedByUser(User user);
    public List<Article> findNewestArticles() throws ArticleNotFoundException;
    public Long createByString(String article, User currentUser) throws ArticleCreationException;
    public List<String> annotationReader (String annotationPath);
    public List<Article> findByUserId(Long userId);
    public List<Article> findByReviewerId(Long userId);
    public List<Article> searchArticles(String articleStr) throws SearchException;
}
