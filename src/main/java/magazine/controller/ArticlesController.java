package magazine.controller;

import magazine.Exeptions.CommentCreationException;
import magazine.Exeptions.DataNotFoundException;
import magazine.domain.*;
import magazine.servise.*;
import magazine.utils.MySimpleDateFormat;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pvc on 28.01.2016.
 */
@Controller
public class ArticlesController {
    public static final Logger log = Logger.getLogger(ArticlesController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Value("${initialPath}")
    private String initialPath;


    @RequestMapping(value = "/getArticles", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<Article> articleList(@RequestBody String chosenSection) {
        log.info("/getArticles controller");
        List<Article> articles = articleService.findPublishArticleBySection(chosenSection);

//        List<Article> newArticles = new ArrayList<>();
//        for (Article article : articles){
//            Article newArticle = article.getArticleForTables(article);
//            User user = article.getUser();
//            User newUser = new User();
//
//            newUser.setUserId(user.getUserId());
//            newUser.setName(user.getName());
//            newUser.setSurname(user.getSurname());
//            newUser.setMiddleName(user.getMiddleName());
//            newArticle.setUser(newUser);
//            newArticles.add(article);
//        }
        return articles;
    }

    @RequestMapping(value = "/getSeminars", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<Seminar> sectionList(@RequestBody String chosenSection) {
        log.info("/getSeminars controller");
        try {
            return seminarService.findSeminarsBySection(chosenSection);
        } catch (DataNotFoundException dnfEx){
            return null;
        }
    }

    @RequestMapping(value = "/articlePage", method = {RequestMethod.GET})
    public String articleNew(@RequestParam String articleId, ModelMap map) {
        log.info("/articlePage controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }

        try {
            Article article = articleService.getArticle(Long.parseLong(articleId));
            map.addAttribute("article", article);

            Annotation annotation = article.getArticleAnnotations();
            List<String> annotationUa = articleService.annotationReader(annotation.getAnnotationUa());
            List<String> annotationEng = articleService.annotationReader(annotation.getAnnotationEng());
            List<String> annotationRu = articleService.annotationReader(annotation.getAnnotationRu());

            map.addAttribute("annotationUa", annotationUa);
            map.addAttribute("annotationEng", annotationEng);
            map.addAttribute("annotationRu", annotationRu);
//            String articleAddress = article.getArticleAddress();
//            map.addAttribute("articleAddress", articleAddress);

        } catch (Exception e) {
            map.addAttribute("errorMessage", e.getMessage());
        }

        return "articlePage";
    }


    @RequestMapping(value = "/seminarPage", method = {RequestMethod.GET})
    public String seminarPage(@RequestParam String seminarId, ModelMap map) {
        log.info("/seminarPage controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }

        try {
            Seminar seminar = seminarService.getSeminar(Long.parseLong(seminarId));
            map.addAttribute("seminar", seminar);

        } catch (Exception e) {
            map.addAttribute("errorMessage", e.getMessage());
        }

        return "seminarPage";
    }


    @RequestMapping(value = "/addComment", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<String> addComment(@RequestBody String commentJson) {
        log.info("/addComment controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        List<String> list;
        try {
            commentService.createComment(user, commentJson);
            list = Arrays.asList(user.toString());
        } catch (CommentCreationException e){
            e.printStackTrace();
            list = Arrays.asList(e.getMessage());
        }
        return list;
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public ResponseEntity<String> deleteComment(@RequestBody Long commentId){
        log.info("/deleteComment controller");
        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        try {
            Comment comment = commentService.getComment(commentId);
            commentService.removeComment(comment);
            entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
        }

        return entity;
    }


    @RequestMapping(value = "/getUserArticles", method = RequestMethod.GET)
    public @ResponseBody List<Article> getUserArticles(@RequestParam Long userId) {
        log.info("/getUserArticles controller");
        List<Article> articles = null;
        try {
            articles = articleService.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    @RequestMapping(value = "/getReviewerArticles", method = RequestMethod.GET)
    public @ResponseBody List<Article> getReviewerArticles(@RequestParam Long userId) {
        log.info("/getReviewerArticles controller");
        List<Article> articles = null;
        try {
            articles = articleService.findByReviewerId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    @RequestMapping(value = "/getUserSeminars", method = RequestMethod.GET)
    public @ResponseBody List<Seminar> getUserSeminars(@RequestParam Long userId) {
        log.info("/getUserSeminars controller");
        List<Seminar> seminars = null;
        try {
            seminars = seminarService.findPublishedSeminarByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seminars;
    }


    @RequestMapping(value = "/searchArticles", method = RequestMethod.POST)
    public @ResponseBody List<Article> searchArticles(@RequestBody String articleStr, HttpServletResponse response) {
        log.info("/searchArticles controller");
        List<Article> articles = null;
        try {
            articles = articleService.searchArticles(articleStr);
        } catch (Exception e) {
            articles = new ArrayList<>();
            Article article = new Article();
            article.setArticleId(-1l);
            article.setArticleName(e.getMessage());
            articles.add(article);
        }
        return articles;
    }

    @RequestMapping(value = "/searchSeminars", method = RequestMethod.POST)
    public @ResponseBody List<Seminar> searchSeminars(@RequestBody String seminarStr, HttpServletResponse response) {
        log.info("/searchSeminars controller");
        List<Seminar> semiars = null;
        try {
            semiars = seminarService.searchSeminars(seminarStr);
        } catch (Exception e) {
            semiars = new ArrayList<>();
            Seminar seminar = new Seminar();
            seminar.setSeminarId(-1l);
            seminar.setSeminarName(e.getMessage());
            semiars.add(seminar);
        }
        return semiars;
    }

    @RequestMapping(value = "/searchUsers", method = RequestMethod.POST)
    public @ResponseBody List<User> searchUsers(@RequestBody String userStr, HttpServletResponse response) {
        log.info("/searchUsers controller");
        List<User> users = null;
        try {
            users = userService.searchUsers(userStr);
        } catch (Exception e) {
            users = new ArrayList<>();
            User user = new User();
            user.setUserId(-1l);
            user.setUserName(e.getMessage());
            users.add(user);
        }
        for (User user : users){
            System.err.println(user.toString());
        }
        return users;
    }


}
