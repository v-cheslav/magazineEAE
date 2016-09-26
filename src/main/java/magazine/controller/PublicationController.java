package magazine.controller;

import magazine.Exeptions.PublicationException;
import magazine.domain.*;
import magazine.servise.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @Autowired
    SectionService sectionService;

    @Autowired
    AnnotationService annotationService;

    @Autowired
    KeyWordService keyWordService;

    @Autowired
    FileService fileService;


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> addArticle(MultipartHttpServletRequest request,
                                       HttpServletResponse response)  {
        log.info("/addArticle controller");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        HashMap<String, Object> map = new HashMap<String, Object>();

        Article article = new Article();
        article.setPublicationName(request.getParameter("articleName"));
        article.setUser(currentUser);

        String sectionStr = null;
        try {
            sectionStr = request.getParameter("articleSection");
            Section articleSection = sectionService.getSectionByName(ListSection.valueOf(sectionStr));
            article.setArticleSection(articleSection);
        } catch (Exception e) {
            map.put("articleMassage", "Виникли проблеми з додаванням статті! Не вдалося знайти секцію: " + sectionStr);
            e.printStackTrace();
            return map;
        }

        String keyWords = null;
        try {
            keyWords = request.getParameter("keyWords");
            List<PublicationKeyWord> keyWordSet = keyWordService.splitKeyWords(keyWords, article);
            article.setPublicationKeyWords(keyWordSet);
        } catch (Exception e) {
            map.put("articleMassage", "Виникли проблеми з додаванням статті! Не вдалося додати ключові слова: " + keyWords);
            e.printStackTrace();
            return map;
        }


        try {
            Annotation annotation = new Annotation(
                    request.getParameter("annotationEng"),
                    request.getParameter("annotationUa"),
                    request.getParameter("annotationRu"),
                    article
            );
            article.setArticleAnnotations(annotation);
            annotationService.createAnnotation(annotation);
        } catch (Exception e) {
            map.put("articleMassage", "Виникли проблеми з додаванням статті! Не вдалося додати анотацію.");
            e.printStackTrace();
            return map;
        }


        String relativePath;
        MultipartFile multipartFile;
        try {
            multipartFile = request.getFile("articleFile");
            relativePath = fileService.saveFile(article, multipartFile);
        } catch (PublicationException e) {
            map.put("articleMassage", e.getMessage());
            e.printStackTrace();
            return map;
        }

        article.setPublicationPath(relativePath);
        article.setArticleFileName(multipartFile.getOriginalFilename());

        try {
            articleService.createArticle(article);
        } catch (Exception e) {
            map.put("articleMassage", e.getMessage());
            e.printStackTrace();
            return map;
        }

        map.put("articleId", article.getPublicationId());
        return map;
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/addSeminar", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> addSeminar (MultipartHttpServletRequest request,
                                        HttpServletResponse response)  {
        log.info("/addSeminar controller");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        HashMap<String, Object> map = new HashMap<String, Object>();

        Seminar seminar = seminarService.getSeminar(Long.parseLong(request.getParameter("seminarId")));

        if (seminar.getUser() == null) {
            seminar.setUser(currentUser);
        }

        String keyWords = null;
        try {
            keyWords = request.getParameter("seminarKeyWords");
            List<PublicationKeyWord> keyWordSet = keyWordService.splitKeyWords(keyWords, seminar);
            seminar.setPublicationKeyWords(keyWordSet);
        } catch (Exception e) {
            map.put("seminarMassage", "Не вдалося додати ключові слова: " + keyWords);
            e.printStackTrace();
            return map;
        }

        String relativePath;
        MultipartFile presentation;
        MultipartFile report;
        try {
            presentation = request.getFile("presentation");
            report = request.getFile("report");
            relativePath = fileService.saveFile(seminar, presentation);
            fileService.saveFile(seminar, report);
        } catch (Exception e) {
            map.put("seminarMassage", e.getMessage());
            e.printStackTrace();
            return map;
        }


        seminar.setPublicationPath(relativePath);
        seminar.setPresentationFileName(presentation.getOriginalFilename());
        seminar.setReportFileName(report.getOriginalFilename());

        try {
            seminarService.changeSeminar(seminar, currentUser);
        } catch (Exception e) {
            map.put("seminarMassage", e.getMessage());
            e.printStackTrace();
            return map;
        }

        map.put("seminarId", seminar.getPublicationId());
        return map;
    }


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
        for (Seminar seminar: seminarList){//TODO костиль
            idAndName = new String[2];
            idAndName[0] = seminar.getId().toString();
            idAndName[1] = seminar.getPublicationName();
            seminarsStr.add(idAndName);
        }
        return seminarsStr;
    }





    /**
     * Розміщує матеріали семінару на сайті згідно з аннонсованими адміністратором датою та темою.
     */
//    @RequestMapping(value = "/publishSeminar", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
//    public ResponseEntity<String> seminarPublication (@RequestBody String seminarStr) {
//        log.info("/publishSeminar controller");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//
//        ResponseEntity<String> entity;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "text/html; charset=utf-8");
//
//        try {
//            seminarService.publishSeminar(seminarStr, currentUser);
//        } catch (SeminarException e) {
//            e.printStackTrace();
//            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
//            return entity;
//        }
//        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
//        return entity;
//    }

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



//    @PreAuthorize("hasRole('USER')")
//    @RequestMapping(value = "/articlePublicationContent", method = {RequestMethod.GET})
//    public String publishArticle () {
//        log.info("/articlePublicationContent controller");
//        return "publishArticle";
//    }

//    @PreAuthorize("hasRole('USER')")
//    @RequestMapping(value = "/seminarPublicationContent", method = {RequestMethod.GET})
//    public String publishSeminar () {
//        log.info("/seminarPublicationContent controller");
//        return "publishSeminar";
//    }





    //    @RequestMapping(value = "/publishArticle", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
//    public ResponseEntity<String> articlePublication (@RequestBody String articleJson) {
//        log.info("/publishArticle controller");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//
//        ResponseEntity<String> entity;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "text/html; charset=utf-8");
//
//        try {
//            articleService.createByString(articleJson, currentUser);
//        } catch (Exception e) {
//            e.printStackTrace();
//            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
//            return entity;
//        }
//        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
//        return entity;
//    }
}
