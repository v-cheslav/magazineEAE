package magazine.dao;

import magazine.domain.Article;
import magazine.domain.User;
import magazine.domain.Review;

import java.util.List;

/**
* Created by pvc on 20.10.2015.
*/
public interface ReviewDao {
    public Long create(Review review);
    public Review read(Long id);
    public void update(Review review);
    public void delete(Review review);
    public List<Review> findByArticle(Article article);
    public List<Review> findByUser(User user);
    public Review findByUserAndArticleId(Long articleId, User user);
    public List<Review> findDennyedReview(User user);

}
