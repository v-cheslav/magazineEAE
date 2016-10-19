package magazine.controller;



import magazine.Exeptions.PublicationException;
import magazine.domain.*;
import magazine.servise.*;
import magazine.utils.Messenger;
import magazine.utils.PasswordHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    @Autowired
    PasswordHelper passwordHelper;


    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(ModelMap map) {
        log.info("/index");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
            log.info("user \"" + user + "\" in the main page.");
        }

        String message = null;
        List <Article> articles = null;
        List <Seminar> nearestSeminars = null;

        try {
            articles = articleService.findNewestArticles();
            log.debug("articles found: " + articles.toString());
        } catch (PublicationException e){
            log.info(e.getMessage());
            message = e.getMessage();
        }

        try {
            nearestSeminars = seminarService.findNearestSeminars();
            log.debug("seminars found: " + nearestSeminars.toString());
        } catch (PublicationException e){
            log.info(e.getMessage());
            message = e.getMessage();
        }

        map.addAttribute("nearestSeminars", nearestSeminars);
        map.addAttribute("articles", articles);
        map.addAttribute("message", message);

        log.debug("return index.jsp");
        return "index";
    }





    @RequestMapping(value = "/articles", method = {RequestMethod.GET})
    public String articles(ModelMap map) {
        log.info("/articles controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "articles";
    }

    @RequestMapping(value = "/seminars", method = {RequestMethod.GET})
    public String seminar(ModelMap map) {
        log.info("/seminars controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "seminars";
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
    public String contacts(ModelMap map) {
        log.info("/contacts controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
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
                    Calendar calendar = seminar.getPublicationDate();
                    DateFormat sdr = new SimpleDateFormat("dd.MM.yyyy");
                    String seminarMessage = sdr.format(calendar.getTime()) +
                            " Ви берете участь в семінарі на тему: \n" +
                            seminar.getPublicationPath();
                    map.addAttribute("seminarMessage", seminarMessage);
                }

                Article article = articleService.findUnPublishedByUser(user);
                map.addAttribute("article", article);

                List<Review> reviews = reviewService.findByUser(user);

                if (reviews.size() != 0) {
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
                    Calendar calendar = seminar.getPublicationDate();
                    DateFormat sdr = new SimpleDateFormat("dd.MM.yyyy");
                    String seminarMessage = sdr.format(calendar.getTime()) +
                            " Ви берете участь в семінарі на тему: \n" +
                            seminar.getPublicationPath();
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
    public String advancedSearch(ModelMap map) {
        log.info("/advancedSearch controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "advancedSearch";
    }

//
//    @RequestMapping(value = "/report", method = {RequestMethod.GET})
//    public String report(Model model) {
//        log.info("/report controller");
//        return "report";
//    }


    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login () {
        log.info("/login controller");
        return "login";
    }

    @RequestMapping(value = "/confirmRegistration", method = {RequestMethod.GET})
    public String confirmRegistration (@RequestParam String userName, Integer regCode, Model map) {
        log.info("/confirmRegistration controller");

        User user = userService.getUserByUserName(userName);
        log.info(user.toString() + ": username " + user.getUsername());
        if (user.getRestoreCode().equals(regCode)){
            user.setValid(true);
            userService.changeUser(user);
            log.info("user " + user.toString() + "set valid");
            map.addAttribute("errorMessage", "Реєстрація відбулася успішно.");
        } else {
            map.addAttribute("errorMessage", "Помилка авторизації.");
        }
        return "login";
    }

    @RequestMapping(value = "/sendRestoreInformation", method = RequestMethod.POST)
    public ResponseEntity<String> sendRestoreInformation(@RequestBody String email, Model map){
        log.info("/sendRestoreInformation controller");
        email = email.replaceAll("\"", "");

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        try {
            User user = userService.getUserByUserName(email);
            Messenger messenger = new Messenger();

            Integer restoreCode = 1111 + (int)(Math.random() * ((9999 - 1111) + 1));
            user.setRestoreCode(restoreCode);
            userService.changeUser(user);

            String message = "Ви надіслали запит на відновлення паролю в журнал Енергетика, автоматика і енергозбереження."
                    + "<br> Ваш код для відновлення паролю: " + restoreCode
                    + "<br><br> Regards, Admin";

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


    @RequestMapping(value = "/restorePassword", method = RequestMethod.POST)
    public ResponseEntity<String> restorePassword(@RequestBody String restoreInformation){
        log.info("/restorePassword controller");
        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(restoreInformation);
            JSONObject jsonObj = (JSONObject) obj;

            String email = (String) jsonObj.get("email");
            String restoreCodeStr = (String) jsonObj.get("restoreCodeStr");
            String newPassword = (String) jsonObj.get("newPassword");

            try {
                User user = userService.getUserByUserName(email);
                Integer restoreCode = Integer.parseInt(restoreCodeStr);
                System.err.println("restoreCode " + restoreCode);
                System.err.println("user.getRestoreCode() " + user.getRestoreCode());

                if (restoreCode.equals(user.getRestoreCode())) {
                    String encodedPassword = passwordHelper.encode(newPassword);
                    user.setPassword(encodedPassword);
                    userService.changeUser(user);
                    entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
                } else {
                    entity = new ResponseEntity<String>("Невірні дані", headers, HttpStatus.OK);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            }
        } catch (ParseException e) {
            log.error(e.getMessage());
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
        }
        return entity;
    }

    @RequestMapping(value = "/restorePasswordPage", method = {RequestMethod.GET})
    public String restorePasswordPage(Model map) {
        log.info("/restorePasswordPage controller");
        return "restorePasswordPage";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/publishArticle", method = {RequestMethod.GET})
    public String publishArticle (ModelMap map) {
        log.info("/publishArticle controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "publishArticle";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/publishSeminar", method = {RequestMethod.GET})
    public String publishSeminar (ModelMap map) {
        log.info("/publishSeminar controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "publishSeminar";
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/publishConference", method = {RequestMethod.GET})
    public String publishConference (ModelMap map) {
        log.info("/publishConference controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "publishConference";
    }

    @RequestMapping(value = "/conference", method = {RequestMethod.GET})
    public String conference (ModelMap map) {
        log.info("/conference controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
        }
        return "conferences";
    }


}
