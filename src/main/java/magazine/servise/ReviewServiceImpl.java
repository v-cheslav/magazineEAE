package magazine.servise;

import magazine.Exeptions.ReviewException;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    UserService userService;

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
    public void createReviewers (String reviewersJson) throws ReviewException{

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reviewersJson);
            JSONObject jsonObj = (JSONObject) obj;
            String articleIdStr = (String) jsonObj.get("articleId");
            Long articleId = Long.parseLong(articleIdStr);
            String firstReviewerStr = (String) jsonObj.get("firstReviewer");
            Long firstReviewerLong = Long.parseLong(firstReviewerStr);
            String secondReviewerStr = (String) jsonObj.get("secondReviewer");
            Long secondReviewerLong = Long.parseLong(secondReviewerStr);


            Article article = articleDao.read(articleId);

            User firstReviewer = userService.getUser(firstReviewerLong);
            firstReviewer.setIsReviewer(true);
            userService.changeUser(firstReviewer);
            Review firstReview = new Review(article, firstReviewer);

            User secondReviewer = userService.getUser(secondReviewerLong);
            secondReviewer.setIsReviewer(true);
            userService.changeUser(secondReviewer);
            Review secondReview = new Review(article, secondReviewer);

            List <Review> reviews = article.getArticleReviews();
            reviews.add(firstReview);
            reviews.add(secondReview);
            reviewDao.create(firstReview);
            reviewDao.create(secondReview);
            article.setArticleReviews(reviews);
            article.setIsReviewersAssigned(true);

            articleDao.update(article);

        } catch (ParseException e) {
            //todo видалити завантажені файли і зробити reduce кількості публікацій
            e.printStackTrace();
            throw new ReviewException("Не вдалося призначити рецензентів.");
        }
    }

    @Override
    public void addReview(Review review, MultipartFile multipartFile) throws ReviewException {
        saveReviewFile(review, multipartFile);
        updateReview(review, multipartFile);
    }

    private void saveReviewFile (Review review, MultipartFile multipartFile)throws ReviewException{

        String absolutePath = initialPath
                + review.getArticle().getPublicationPath()
                + multipartFile.getOriginalFilename();
        System.err.println("absolutePath review " +  absolutePath);
        Path path = Paths.get(absolutePath);
        boolean isFileExist = Files.exists(path);
        if (!isFileExist) {
            try {
                multipartFile.transferTo(new File(absolutePath));
            } catch (IOException e) {
                e.printStackTrace();
                throw new ReviewException("Не вдалося зберегти файл рецензії.");
            }
        } else {
            throw new ReviewException("Файл з назвою " + multipartFile.getName()
                    + "існує. Змініть назву файлу!");
        }
    }

    private void updateReview(Review review, MultipartFile multipartFile) throws ReviewException{
        String fileName = multipartFile.getOriginalFilename();
        review.setReviewName(fileName);

        String articlePath = review.getArticle().getPublicationPath();
        int lastIndex = articlePath.lastIndexOf('/');
        String relativePath = articlePath.substring(0, lastIndex+1) + fileName;
        review.setReviewAddress(relativePath);
        review.setStatus(true);

        try {
            reviewDao.update(review);
            Article article = review.getArticle();
            List<Review> reviews = article.getArticleReviews();
            if (reviews.get(0).getStatus() == true && reviews.get(1).getStatus()){
                article.setIsPrintable(true);
                articleDao.update(article);
            }
        } catch (Exception e) {
            throw new ReviewException("Рецензію опублікувати не вдалося. Спробуйте пізніше");
        }
    }

    @Override
    public Review findByUserAndArticleId(Long articleId, User user){
        return reviewDao.findByUserAndArticleId(articleId, user);
    }


//    @Override
//    public void addReview(User user, String reviewJson) throws ReviewException {
//        log.info("addReview method");
//        JSONParser parser = new JSONParser();
//        Object obj = null;
//        try {
//            obj = parser.parse(reviewJson);
//            JSONObject jsonObj = (JSONObject) obj;
//            Long articleId = (Long) jsonObj.get("articleId");
//            String fileName = (String) jsonObj.get("fileName");
//
//            Review review = reviewDao.findByUserAndArticleId(articleId, user);
//            String articleAddress = review.getArticle().getPublicationPath();
//            int lastIndex = articleAddress.lastIndexOf('/');
//            String relativePath = articleAddress.substring(0, lastIndex+1) + fileName;
//
//            review.setReviewAddress(relativePath);
//            review.setReviewName(fileName);
//            review.setStatus(true);
//            reviewDao.update(review);
//            Article article = review.getArticle();
//            List<Review> reviews = article.getArticleReviews();
//            if (reviews.get(0).getStatus() == true && reviews.get(1).getStatus()){
//                article.setIsPrintable(true);
//                articleDao.update(article);
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new ReviewException("Рецензію опублікувати не вдалося. Спробуйте пізніше");
//
//        }
//    }

//    private void reviewWriter (String reviewText, String absolutePath) throws FileNotFoundException, UnsupportedEncodingException, ReviewException {
//        FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
//        try {
//            bufferedWriter.append(reviewText);
//            bufferedWriter.flush();
//        } catch (IOException ex) {
//            log.error("File not found", ex);
//            throw new ReviewException(errorMessage);
//        } finally {
//
//            try {
//                bufferedWriter.close();
//            } catch (IOException e) {
//                log.error("BufferedWriter closing error", e);
//                throw new ReviewException(errorMessage);
//            }
//
//            try {
//                fileOutputStream.close();
//            } catch (IOException e) {
//                log.error("FileOutputStream closing error", e);
//                throw new ReviewException(errorMessage);
//            }
//        }
//
//    }

    @Override
    public List<String> reviewReader (String reviewPath) throws ReviewException {
        String pathStr = initialPath + "userArticles/" + reviewPath;
        Path path = Paths.get(pathStr);
        List<String> reviewLines = null;
        try {
            reviewLines = Files.readAllLines(path);
        } catch (IOException ex){
            ex.printStackTrace();
            throw new ReviewException("Виникла помилка при читанні рецензії.");
        }
        return reviewLines;
    }

}
