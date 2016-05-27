package magazine.controller;

import magazine.Exeptions.SeminarCreationException;
import magazine.domain.Seminar;
import magazine.domain.User;
import magazine.servise.ArticleService;
import magazine.servise.SeminarService;
import magazine.servise.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pvc on 23.11.2015.
 */
@Controller
public class PublicationController {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    SeminarService seminarService;


    @Autowired
    UserService userService;

    @RequestMapping(value = "/getAllReviewers", method = RequestMethod.GET)
    public @ResponseBody List<String []> reviewers() {
        log.info("/getAllReviewers controller");
        List<User> userList = userService.getAllDoctorsAndCandidates();
        List<String []> usersStr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String [] idAndName;
        for (User user: userList){
            sb.append(user.getSurname() + " " + user.getName());
            if (user.getMiddleName() != null){
                sb.append(" " + user.getMiddleName());
            }
            idAndName = new String[2];
            idAndName[0] = user.getUserId().toString();
            idAndName[1] = sb.toString();
            sb.delete(0, sb.length());
            usersStr.add(idAndName);
        }
//        for (String [] str : usersStr){
//            System.err.println("userId=" + str[0] +" userName=" + str[1]);
//        }
        return usersStr;
    }
    //todo put a link on the publish page to see a detail information about Reviewers

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public @ResponseBody List<String []> users() {
        log.info("/getAllUsers controller");
        List<User> userList = userService.getAllUsers();
        List<String []> usersStr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String [] idAndName;
        for (User user: userList){
            sb.append(user.getSurname() + " " + user.getName());
            if (user.getMiddleName() != null){
                sb.append(" " + user.getMiddleName());
            }
            idAndName = new String[2];
            idAndName[0] = user.getUserId().toString();
            idAndName[1] = sb.toString();
            sb.delete(0, sb.length());
            usersStr.add(idAndName);
        }

        return usersStr;
    }
    //todo put a link on the publish page to see a detail information about Reviewers


    @RequestMapping(value = "/getAllUnpublishedSeminars", method = RequestMethod.GET)
    public @ResponseBody List<String []> seminars() {
        log.info("/getAllUnpublishedSeminars controller");
        List<Seminar> seminarList = seminarService.findAllAnnounced();
        List<String []> seminarsStr = new ArrayList<>();
        String [] idAndName;
        for (Seminar seminar: seminarList){
            idAndName = new String[2];
            idAndName[0] = seminar.getId().toString();
            idAndName[1] = seminar.getPublicationName();
            seminarsStr.add(idAndName);
        }
        return seminarsStr;
    }


    @RequestMapping(value = "/publishArticle", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> articlePublication (@RequestBody String articleStr) {
        log.info("/publishArticle controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        try {
            articleService.createByString(articleStr, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        return entity;
    }

    /**
     * Розміщує матеріали семінару на сайті згідно з аннонсованими адміністратором датою та темою.
     */
    @RequestMapping(value = "/publishSeminar", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> seminarPublication (@RequestBody String seminarStr) {
        log.info("/publishSeminar controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        try {
            seminarService.publishSeminar(seminarStr, currentUser);
        } catch (SeminarCreationException e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        return entity;
    }

    /**
     * додається в список анонсованих на основі надісланої юзером інформації
     * або інформації наданої іншим чином.
     */
    @RequestMapping(value = "/announceSeminar", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> unRegSeminarPublication (@RequestBody String seminarStr) {
        log.info("/announceSeminar controller");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        try {
            seminarService.advertiseSeminar(seminarStr);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
                return entity;
    }

    /**
     * додає заявку на участь в семінарі.
     */
    @RequestMapping(value = "/applySeminar", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> applySeminar (@RequestBody String seminarStr) {
        log.info("/applySeminar controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");

        try {
            seminarService.applySeminar(currentUser, seminarStr);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        return entity;
    }



    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/articlePublicationContent", method = {RequestMethod.GET})
    public String publishArticle () {
        log.info("/articlePublicationContent controller");
        return "publishArticle";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/seminarPublicationContent", method = {RequestMethod.GET})
    public String publishSeminar () {
        log.info("/seminarPublicationContent controller");
        return "publishSeminar";
    }
}
