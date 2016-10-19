package magazine.controller;

import magazine.Exeptions.CommentCreationException;
import magazine.Exeptions.DataNotFoundException;
import magazine.domain.*;
import magazine.servise.*;
import org.apache.log4j.Logger;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 28.01.2016.
 */
@Controller
public class ArticleController {
    public static final Logger log = Logger.getLogger(ArticleController.class);

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


    @RequestMapping(value = "/getArticlesWithoutReviewers", method = RequestMethod.GET)
    public @ResponseBody List<Article> getArticlesWithoutReviewers() {
        log.info("/getArticlesWithoutReviewers controller");
        List<Article> articles = articleService.findWithoutReviewers();
        for(Article article : articles){
            System.err.println("articleId " +article.getPublicationId());

            System.err.println("articleName " +article.getPublicationName());
        }
        return articles;
    }

    @RequestMapping(value = "/getDeclaredSeminars", method = RequestMethod.GET)
    public @ResponseBody List<Seminar> getDeclaredSeminars() {
        log.info("/getDeclaredSeminars controller");
        List<Seminar> seminars = seminarService.findAllDeclared();
        return seminars;
    }

    @RequestMapping(value = "/getArticles", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<Article> articleList(@RequestBody String chosenSection) {
        log.info("/getArticles controller");
        List<Article> articles = articleService.findPublishArticleBySection(chosenSection);
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
    public String articleNew(@RequestParam String publicationId, ModelMap map) {
        log.info("/articlePage controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        Article article = null;
        try {
            article = articleService.getArticle(Long.parseLong(publicationId));
            map.addAttribute("article", article);
        } catch (Exception e) {
            map.addAttribute("errorMessage", e.getMessage());
        }
        try {
            List<Article> similarArticles = articleService.findArticlesByKeywords(article);
            map.addAttribute("similarArticles", similarArticles);
        } catch (Exception e) {
            e.printStackTrace();
            map.addAttribute("errorMessage", e.getMessage());
        }
        return "articlePage";
    }


    @RequestMapping(value = "/seminarPage", method = {RequestMethod.GET})
    public String seminarPage(@RequestParam String publicationId, ModelMap map) {
        log.info("/seminarPage controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        Seminar seminar = null;
        try {
            seminar = seminarService.getSeminar(Long.parseLong(publicationId));
            map.addAttribute("seminar", seminar);
        } catch (Exception e) {
            map.addAttribute("errorMessage", e.getMessage());
        }
        try {
            List<Seminar> similarSeminars = seminarService.findSeminarsByKeywords(seminar);
            map.addAttribute("similarSeminars", similarSeminars);
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
            entity = new ResponseEntity<>("OK", headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), headers, HttpStatus.OK);
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
    public @ResponseBody List<Article> searchArticles(@RequestBody String articleStr) {
        log.info("/searchArticles controller");
        List<Article> articles = null;
        try {
            articles = articleService.searchArticles(articleStr);
        } catch (Exception e) {
            articles = new ArrayList<>();
            Article article = new Article();
            article.setId(-1l);
            article.setPublicationPath(e.getMessage());
            articles.add(article);
        }
        return articles;
    }


    @RequestMapping(value = "/searchSeminars", method = RequestMethod.POST)
    public @ResponseBody List<Seminar> searchSeminars(@RequestBody String seminarStr) {
        log.info("/searchSeminars controller");
        List<Seminar> semiars = null;
        try {
            semiars = seminarService.searchSeminars(seminarStr);
        } catch (Exception e) {
            semiars = new ArrayList<>();
            Seminar seminar = new Seminar();
            seminar.setId(-1l);
            seminar.setPublicationPath(e.getMessage());
            semiars.add(seminar);
        }
        return semiars;
    }


    @RequestMapping(value = "/searchUsers", method = RequestMethod.POST)
    public @ResponseBody List<User> searchUsers(@RequestBody String userStr) {
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
