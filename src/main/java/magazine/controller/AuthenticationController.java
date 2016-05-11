//package magazine.controller;
//
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
//* Created by pvc on 06.10.2015.
//*/
//
//
//@Controller
//public class AuthenticationController {
//
//    public static final Logger log = Logger.getLogger(AuthenticationController.class);
//
//    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String index(Model model) {
//        log.info("/login controller");
////        model.addAttribute("operator", new Operator());
//        return "indexSpringTutorial";
//    }
////
//    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String indexJsp(Model model) {
//        log.info("/index controller");
////        model.addAttribute("operator", new Operator());
//        return "index";
//    }
//
//    @RequestMapping(value = "/publication", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String publication(Model model) {
//        log.info("/publication controller");
////        model.addAttribute("operator", new Operator());
//        return "publication";
//    }
//
//    @RequestMapping(value = "/seminar", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String seminar(Model model) {
//        log.info("/seminar controller");
////        model.addAttribute("operator", new Operator());
//        return "seminar";
//    }
//
//    @RequestMapping(value = "/user/publish", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String publish(Model model) {
//        log.info("/publish controller");
////        model.addAttribute("operator", new Operator());
//        return "publish";
//    }
//
//    @RequestMapping(value = "/contacts", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String contacts(Model model) {
//        log.info("/contacts controller");
////        model.addAttribute("operator", new Operator());
//        return "contacts";
//    }
//
//    @RequestMapping(value = "/user/myPage", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String myPage(Model model) {
//        log.info("/myPage controller");
////        model.addAttribute("operator", new Operator());
//        return "myPage";
//    }
//
//    @RequestMapping(value = "/authorPage", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String authorPage(Model model) {
//        log.info("/authorPage controller");
////        model.addAttribute("operator", new Operator());
//        return "authorPage";
//    }
//
//    @RequestMapping(value = "/advancedSearch", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String advancedSearch(Model model) {
//        log.info("/advancedSearch controller");
////        model.addAttribute("operator", new Operator());
//        return "advancedSearch";
//    }
//
//    @RequestMapping(value = "/article", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String article(Model model) {
//        log.info("/article controller");
////        model.addAttribute("operator", new Operator());
//        return "article";
//    }
//
//    @RequestMapping(value = "/report", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String report(Model model) {
//        log.info("/report controller");
////        model.addAttribute("operator", new Operator());
//        return "report";
//    }
//
//    @RequestMapping(value = "/registration", method = {RequestMethod.GET, RequestMethod.HEAD})
//    public String registration(Model model) {
//        log.info("/registration controller");
////        model.addAttribute("operator", new Operator());
//        return "registration";
//    }
//
//}
