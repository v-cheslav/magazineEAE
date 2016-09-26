package magazine.dao;

import magazine.domain.Article;
import magazine.domain.PublicationKeyWord;
import magazine.domain.Section;
import magazine.domain.User;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
* Created by pvc on 20.10.2015.
*/
public interface ArticleDao {
    public Long create(Article article);
    public Article read(Long id);
    public void update(Article article);
    public void delete(Article article);
    public List<Article> findAllPublished();
    public List<Article> findAllPublishedBySection(Section section);
    public List<Article> findNewest();
    public List<Article> findAllUnpublished();
    public Article findUnPublishedByUser(User user);
    public Article  findUnPublishedByReviewer(User reviewer);
    public List<Article> findByUserId(Long userId);
    public List<Article> findByReviewerId(Long userId);
    public List<Article> findWithoutReviewers();
    public List<Article> findBySearchQuery(Map query);
    public List<Article> findArticlesByKeywords(Article article);
    }
