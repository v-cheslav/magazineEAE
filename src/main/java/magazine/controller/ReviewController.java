package magazine.controller;

import magazine.Exeptions.ReviewCreationException;
import magazine.domain.Article;
import magazine.domain.Review;
import magazine.domain.User;
import magazine.servise.ArticleService;
import magazine.servise.ReviewService;
import magazine.servise.UserService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 22.03.2016.
 */

@Controller
public class ReviewController {
    public static final Logger log = Logger.getLogger(ReviewController.class);


    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;
    /**
     * Контроллер перевіряє чи має право юзер надавати рецензію
     */

    @RequestMapping(value = "/checkReviewer", method = RequestMethod.POST)
    public @ResponseBody
    List<String> checkReviewer(@RequestBody String reviewerIdJson) {
        log.info("/checkReviewer controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        List<String> list;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();

        } else {
            list = Arrays.asList("Щоб надати рецензію небхідно авторизуватись.");
            return list;
        }

        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(reviewerIdJson);
            JSONObject jsonObj = (JSONObject) obj;
            Long reviewerId = (Long) jsonObj.get("reviewerId");
            if (user.getUserId() == reviewerId) {
                list = Arrays.asList("OK");
            } else {
                list = Arrays.asList("Ви не маєте права надавати рецензію на цю статтю.");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            list = Arrays.asList("error");
        }
        return list;
    }

    @RequestMapping(value = "/dennyReview", method = RequestMethod.POST)
    public @ResponseBody List<String> dennyReview(@RequestBody String articleIdJson){
        log.info("/dennyReview controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        List<String> list;
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(articleIdJson);
            JSONObject jsonObj = (JSONObject) obj;
            Long articleId = (Long) jsonObj.get("articleId");
            reviewService.dennyReview(articleId, user);
            list = Arrays.asList("OK");
        } catch (ParseException e) {
            e.printStackTrace();
            list = Arrays.asList("error");
        }
        return list;
    }

    @RequestMapping(value = "/setNewReviewer", method = RequestMethod.POST)
    public @ResponseBody List<String> setNewReviewer(@RequestBody String userIdJson){
        log.info("/setNewReviewer controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        List<String> list = Arrays.asList("error");
        JSONParser parser = new JSONParser();
        Object obj = null;
        User newReviewer = null;
        try {
            obj = parser.parse(userIdJson);
            JSONObject jsonObj = (JSONObject) obj;
            String newReviewerId = (String) jsonObj.get("userId");
            newReviewer = userService.getUser(Long.parseLong(newReviewerId));
        } catch (ParseException e) {
            e.printStackTrace();
            list = Arrays.asList("error");
            return list;
        }

        Article unpublishedArticle = articleService.findUnPublishedByUser(user);
        List <Review> reviews = unpublishedArticle.getArticleReviewers();

        for (Review review : reviews) {
            if (review.getStatus() == null) {
                User previousReviewer = review.getUser();
                User firstReviewer = unpublishedArticle.getArticleReviewers().get(0).getUser();
                User secondReviewer = unpublishedArticle.getArticleReviewers().get(1).getUser();

                if (previousReviewer.getUserId() == newReviewer.getUserId()) {
                    list = Arrays.asList("theSameReviewer");
                } else if (newReviewer.getUserId() == firstReviewer.getUserId() || newReviewer.getUserId() == secondReviewer.getUserId()) {
                    list = Arrays.asList("Ви не можете обрати двох однакових рецензентів");
                } else {
                    review.setStatus(false);
                    review.setUser(newReviewer);
                    reviewService.changeReview(review);
                    list = Arrays.asList("OK");
                }
                return list;
            }
        }

        return list;
    }

    @RequestMapping(value = "/setNewReviewerFinally", method = RequestMethod.POST)
    public @ResponseBody List<String> setNewReviewerFinally(@RequestBody String userIdJson){
        log.info("/setNewReviewerFinally controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        List<String> list = Arrays.asList("error");
        JSONParser parser = new JSONParser();
        Object obj = null;
        User newReviewer;
        try {
            obj = parser.parse(userIdJson);
            JSONObject jsonObj = (JSONObject) obj;
            String newReviewerId = (String) jsonObj.get("userId");
            newReviewer = userService.getUser(Long.parseLong(newReviewerId));
        } catch (ParseException e) {
            e.printStackTrace();
            list = Arrays.asList("error");
            return list;
        }

        Article unpublishedArticle = articleService.findUnPublishedByUser(user);
        List <Review> reviews = unpublishedArticle.getArticleReviewers();

        for (Review review : reviews) {
            if (review.getStatus() == null) {
                    review.setStatus(false);
                    review.setUser(newReviewer);
                    reviewService.changeReview(review);
                    list = Arrays.asList("OK");
                return list;
            }
        }

        return list;
    }

    @RequestMapping(value = "/setReview", method = RequestMethod.POST)
    public @ResponseBody List<String> setReview(@RequestBody String reviewJson){
        log.info("/setReview controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        List<String> list;
        try {
            reviewService.setReview(user, reviewJson);
            list = Arrays.asList("OK");
        } catch (ReviewCreationException e) {
            list = Arrays.asList(e.getMessage());
        }
        return list;
    }

    @RequestMapping(value = "/getReview", method = RequestMethod.GET)
    public @ResponseBody List<String> getReview(@RequestParam String reviewId) {
        log.info("/getReview controller");
        List<String> list;
        try {
            Review review = reviewService.getReview(Long.parseLong(reviewId));
            list = reviewService.reviewReader(review.getReview());
        } catch (Exception e) {
            e.printStackTrace();
            list = Arrays.asList(e.getMessage());
        }
        return list;
    }




//    @RequestMapping(value = "/getUserReviews", method = RequestMethod.GET)
//    public @ResponseBody List<Review> getUserReviews(@RequestParam Long userId) {
//        log.info("/getUserReviews controller");
//        List<Review> reviews = null;
//        try {
//            reviews = seminarService.findPublishedSeminarByUserId(userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return reviews;
//    }

}
