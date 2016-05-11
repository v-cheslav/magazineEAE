package magazine.controller;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;
import magazine.servise.RegistrationService;
import magazine.servise.UserService;
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
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
* Created by pvc on 31.10.2015.
*/

@Controller
public class RegistrationController {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;


    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/registration", method = {RequestMethod.GET})
    public String registration(Model model) {
        log.info("/registration controller");
        return "registration";
    }

    @RequestMapping(value = "/regUser", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> registrationForm (@RequestBody String userStr) {
        log.info("/regUser controller");
        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        try {
            registrationService.regUser(userStr);
        } catch (RegistrationException e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        return entity;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateUser (@RequestBody String userStr) {
        log.info("/updateUser controller");
        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        try {
            registrationService.updateUser(userStr, user);
        } catch (RegistrationException e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
            return entity;
        }
        entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        return entity;
    }




    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile (MultipartHttpServletRequest request,
                              HttpServletResponse response)throws Exception {
        log.info("/upload controller");
        MultipartFile multipartFile = request.getFile("file");
        StringBuilder sb = new StringBuilder();
        String username = multipartFile.getName();
        String type = multipartFile.getContentType();
        sb.append("../userPhotos/").append(username).append(type);
        String photoAddress = sb.toString();
        multipartFile.transferTo(new File(photoAddress));//todo do exception, if it occurs redirect to another page
        return "OK";
    }

}
