package magazine.servise;

import magazine.Exeptions.ReviewCreationException;
import magazine.domain.Article;
import magazine.domain.Review;
import magazine.domain.User;

import java.util.List;

/**
 * Created by pvc on 19.03.2016.
 */
public interface ReviewService {
    public Long createReview(Review review);
    public Review getReview(Long id);
    public void changeReview(Review review);
    public void removeReview(Review review);
    public List<Review> findByUser(User user);
    public void dennyReview(Long articleId, User user);
    public void setReview (User user, String reviewJson) throws ReviewCreationException;
    public List<String> reviewReader (String reviewPath) throws ReviewCreationException;
}
