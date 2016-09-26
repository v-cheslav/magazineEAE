package magazine.servise;

import magazine.Exeptions.ReviewException;
import magazine.domain.Review;
import magazine.domain.User;
import org.springframework.web.multipart.MultipartFile;

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
    public void addReview(Review review, MultipartFile multipartFile)throws ReviewException;
    public void dennyReview(Long articleId, User user);
    public Review findByUserAndArticleId(Long articleId, User user);
    public void createReviewers (String reviewersJson) throws ReviewException;
//    public void addReview(User user, String reviewJson) throws ReviewException;
    public List<String> reviewReader (String reviewPath) throws ReviewException;
}
