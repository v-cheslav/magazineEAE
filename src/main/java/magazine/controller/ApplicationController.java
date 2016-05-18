package magazine.controller;



import magazine.Exeptions.ArticleNotFoundException;
import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SeminarNotFoundException;
import magazine.domain.*;
import magazine.servise.*;
import magazine.utils.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class ApplicationController {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Autowired
    UserService userService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ReviewService reviewService;


    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        String message = null;
        List <Article> articles;
        List<List<String>> annotations;
        try {
            articles = articleService.findNewestArticles();
            annotations = new ArrayList<>();
            Annotation annotation;
            for (Article article : articles) {
                annotation = article.getArticleAnnotations();
                List<String> annotationUa = articleService.annotationReader(annotation.getAnnotationUa());
                annotations.add(annotationUa);
            }
        } catch (ArticleNotFoundException e){
            articles = null;
            annotations = null;
            message = e.getMessage();
        }

        List <Seminar> nearestSeminars = null;
        try {
            nearestSeminars = seminarService.findNearestSeminars();
        } catch (SeminarNotFoundException e){
            log.info(e.getMessage());
        }
        map.addAttribute("nearestSeminars", nearestSeminars);
        map.addAttribute("articles", articles);
        map.addAttribute("annotations", annotations);
        map.addAttribute("message", message);

        return "index";
    }



    @RequestMapping(value = "/publication", method = {RequestMethod.GET})
    public String publication(ModelMap map) {
        log.info("/publication controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "publication";
    }

    @RequestMapping(value = "/seminar", method = {RequestMethod.GET})
    public String seminar(ModelMap map) {
        log.info("/seminar controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "seminar";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/publish", method = {RequestMethod.GET})
    public String publish(ModelMap map) {
        log.info("/publish controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "publish";
    }

    @RequestMapping(value = "/contacts", method = {RequestMethod.GET})
    public String contacts() {
        log.info("/contacts controller");
        return "contacts";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/myPage", method = {RequestMethod.GET})
    public String myPage(ModelMap map) {
        log.info("/myPage controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);

            try {
                Seminar seminar = seminarService.findAnnouncedByUser(user);
                if (seminar != null) {
                    Calendar calendar = seminar.getSeminarPublicationDate();
                    DateFormat sdr = new SimpleDateFormat("dd.MM.yyyy");
                    String seminarMessage = sdr.format(calendar.getTime()) +
                            " Ви берете участь в семінарі на тему: \n" +
                            seminar.getSeminarName();
                    map.addAttribute("seminarMessage", seminarMessage);
                }

                Article article = articleService.findUnPublishedByUser(user);
                map.addAttribute("article", article);

                List<Review> reviews = reviewService.findByUser(user);

                if (reviews.get(0) != null) {
                    map.addAttribute("reviews", reviews);
                }

            } catch (Exception e){
//todo
            }
        }
        return "myPage";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/myPageNew", method = {RequestMethod.GET})
    public String myPageNew(ModelMap map) {
        log.info("/myPageNew controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);

            try {
                Seminar seminar = seminarService.findAnnouncedByUser(user);
                if (seminar != null) {
                    Calendar calendar = seminar.getSeminarPublicationDate();
                    DateFormat sdr = new SimpleDateFormat("dd.MM.yyyy");
                    String seminarMessage = sdr.format(calendar.getTime()) +
                            " Ви берете участь в семінарі на тему: \n" +
                            seminar.getSeminarName();
                    map.addAttribute("seminarMessage", seminarMessage);
                }

                Article article = articleService.findUnPublishedByUser(user);
                    map.addAttribute("article", article);

                List<Review> reviews = reviewService.findByUser(user);

                if (reviews.get(0) != null) {
                    map.addAttribute("reviews", reviews);
                }


            } catch (Exception e){

            }
        }
        return "myPageNew";
    }

    @RequestMapping(value = "/authorPage", method = {RequestMethod.GET})
    public String authorPage(@RequestParam String authorId, Model map) {
        log.info("/authorPage controller");

        try {
            User user = userService.getUser(Long.parseLong(authorId));
            map.addAttribute("userDetails", user);
        } catch (Exception e) {
            map.addAttribute("errorMessage", e.getMessage());
        }

        return "authorPage";
    }

    @RequestMapping(value = "/advancedSearch", method = {RequestMethod.GET})
    public String advancedSearch(Model model) {
        log.info("/advancedSearch controller");
        return "advancedSearch";
    }


    @RequestMapping(value = "/report", method = {RequestMethod.GET})
    public String report(Model model) {
        log.info("/report controller");
        return "report";
    }


    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login (Model model) {
        log.info("/login controller");
        return "login";
    }

    @RequestMapping(value = "/confirmRegistration", method = {RequestMethod.GET})
    public String confirmRegistration (@RequestParam String userName, Integer regCode, Model map) {
        log.info("/confirmRegistration controller");

        User user = userService.getUserByUserName(userName);

        if (user.getRestoreCode().equals(regCode)){
            user.setValid(true);
            System.err.println(user.toString());
            userService.changeUser(user);
            map.addAttribute("errorMessage", "Реєстрація відбулася успішно.");
        } else {
            map.addAttribute("errorMessage", "Помилка авторизації.");
        }
        return "login";
    }
    @RequestMapping(value = "/remindPassword", method = RequestMethod.POST)
    public ResponseEntity<String> remindPassword(@RequestBody String email, Model map){
        log.info("/remindPassword controller");
        email = email.replaceAll("\"", "");

        System.err.println("-"+email+"-");

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        try {
            User user = userService.getUserByUserName(email);

            Messenger messenger = new Messenger();

            String message = "Ви надіслали запит на відновлення парлю в журнал Енергетика, автоматика і енергозбереження."
                    + "<br> Ваш пароль:" + user.getPassword()
                    + "<br><br> Regards, Admin";
            System.err.println(message);

        try {
            messenger.sendMessage(email, message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            map.addAttribute("errorMessage", "Неможливо відправити повідомлення на пошту. " +
                    "Перевірте правильність вашої електронної адреси і повторіть спробу.");
        }

            entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
        }
        return entity;
    }

}
