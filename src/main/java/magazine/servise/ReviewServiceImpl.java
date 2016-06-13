package magazine.servise;

import magazine.Exeptions.ReviewCreationException;
import magazine.dao.ArticleDao;
import magazine.dao.ReviewDao;
import magazine.domain.Article;
import magazine.domain.Review;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by pvc on 19.03.2016.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    private static Logger log = Logger.getLogger(ReviewServiceImpl.class);

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    ArticleDao articleDao;

    @Value("${initialPath}")
    private String initialPath;

    private String errorMessage = "Виникли проблеми з рецензуванням.\" +\n" +
            "\"Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора";


    public ReviewServiceImpl() {
    }

    @Override
    public Long createReview(Review review) {
        return reviewDao.create(review);
    }

    @Override
    public Review getReview(Long id) {
        return reviewDao.read(id);
    }

    @Override
    public void changeReview(Review review) {
        reviewDao.update(review);
    }

    @Override
    public void removeReview(Review review) {
        reviewDao.delete(review);
    }

    @Override
    public List<Review> findByUser(User user) {
        return reviewDao.findByUser(user);
    }

    @Override
    public void dennyReview(Long articleId, User user) {
        Review review = reviewDao.findByUserAndArticleId(articleId, user);
        review.setStatus(null);
        reviewDao.update(review);
    }

    @Override
    public void setReview(User user, String reviewJson) throws ReviewCreationException{
        log.info("setReview method");
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(reviewJson);
            JSONObject jsonObj = (JSONObject) obj;
            String reviewText = (String) jsonObj.get("review");
            Long articleId = (Long) jsonObj.get("articleId");

            System.err.println("articleId" + articleId);
            Review review = reviewDao.findByUserAndArticleId(articleId, user);
            System.err.println("articleId" + articleId);

            String fileName = "review" + review.getReviewId() + ".xml";

            String articleAddress = review.getArticle().getPublicationAddress();
            int lastIndex = articleAddress.lastIndexOf('/');
            String relativePath = articleAddress.substring(0, lastIndex+1) + fileName;
//            String simplePath = relativePath.replaceAll("../../userResources/", "");
            String absolutePath = initialPath + "userArticles/" + relativePath;
            System.out.println(absolutePath);

            try {
                reviewWriter(reviewText, absolutePath);
            } catch (FileNotFoundException e) {
                log.error("File not found", e);
                throw new ReviewCreationException(errorMessage);
            } catch (UnsupportedEncodingException e) {
                log.error("UnsupportedEncodingException", e);
                throw new ReviewCreationException(errorMessage);
            }

            review.setReview(relativePath);
            review.setStatus(true);
            reviewDao.update(review);
            Article article = review.getArticle();
            List<Review> reviews = article.getArticleReviewers();
            if (reviews.get(0).getStatus() == true && reviews.get(1).getStatus()){
                article.setIsPrintable(true);
                articleDao.update(article);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            throw new ReviewCreationException("Рецензію опублікувати не вдалося. Спробуйте пізніше");

        }
    }

    private void reviewWriter (String reviewText, String absolutePath) throws FileNotFoundException, UnsupportedEncodingException, ReviewCreationException {
        FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
        try {
            bufferedWriter.append(reviewText);
            bufferedWriter.flush();
        } catch (IOException ex) {
            log.error("File not found", ex);
            throw new ReviewCreationException(errorMessage);
        } finally {

            try {
                bufferedWriter.close();
            } catch (IOException e) {
                log.error("BufferedWriter closing error", e);
                throw new ReviewCreationException(errorMessage);
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.error("FileOutputStream closing error", e);
                throw new ReviewCreationException(errorMessage);
            }
        }

    }

    @Override
    public List<String> reviewReader (String reviewPath) throws ReviewCreationException{
        String pathStr = initialPath + "userArticles/" + reviewPath;
        Path path = Paths.get(pathStr);
        List<String> reviewLines = null;
        try {
            reviewLines = Files.readAllLines(path);
        } catch (IOException ex){
            ex.printStackTrace();
            throw new ReviewCreationException("Виникла помилка при читанні рецензії.");
        }
        return reviewLines;
    }

}
