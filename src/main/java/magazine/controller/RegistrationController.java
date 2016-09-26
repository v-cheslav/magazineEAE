package magazine.controller;

import magazine.Exeptions.AdminRegistrationException;
import magazine.Exeptions.RegistrationException;
import magazine.domain.User;
import magazine.domain.UserInterest;
import magazine.domain.UserRole;
import magazine.servise.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Set;

/**
* Created by pvc on 31.10.2015.
*/

@Controller
public class RegistrationController {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Autowired
    JsonParseService jsonParseService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;

    @Autowired
    AcadStatusService acadStatusService;

    @Autowired
    SciDegreeService sciDegreeService;

    @Autowired
    UserSexService userSexService;

    @Autowired
    FileService fileService;

    @Autowired
    UserInterestService userInterestService;

    @Autowired
    UserRoleService userRoleService;


    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/registration", method = {RequestMethod.GET})
    public String registration() {
        log.info("/registration controller");
        return "registration";
    }


    @RequestMapping(value = "/regUser", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> regUser(MultipartHttpServletRequest request,
                                      HttpServletResponse response)  {
        log.info("/regUser controller");
        HashMap<String, Object> map = new HashMap<String, Object>();

        MultipartFile multipartFile = request.getFile("userPhoto");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(username, password,
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("middleName"),
                request.getParameter("university"),
                request.getParameter("institute"),
                request.getParameter("chair"),
                request.getParameter("position"),
                request.getParameter("phone"),
                acadStatusService.findAcadStatus(request.getParameter("acadStatus")),
                sciDegreeService.finSciDegree(request.getParameter("sciDegree")),
                userSexService.findUserSex(request.getParameter("userSex"))
        );

        String interestsStr = request.getParameter("keyWords");
        Set<UserInterest> interests = userInterestService.setUserInterests(interestsStr, user);
        user.setInterests(interests);

        String adminRole = request.getParameter("adminChBox");
        Set<UserRole> userRoles;
        try {
            userRoles = userRoleService.setUserRoles(user, password, adminRole);//throws AdminRegistrationException
            user.setUserRoles(userRoles);
        } catch (AdminRegistrationException e) {
            map.put("registrationMassage", "Ви не маєте права реєструватись як адміністратор!");
            e.printStackTrace();
        }

        try {
            registrationService.regUser(user, multipartFile);
        } catch (RegistrationException e) {
            map.put("registrationMassage", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> updateUser(MultipartHttpServletRequest request,
                                    HttpServletResponse response)  {
        log.info("/updateUser controller");

        User oldUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            oldUser = (User) authentication.getPrincipal();
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        MultipartFile multipartFile = request.getFile("userPhoto");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User newUser = new User(username, password,
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("middleName"),
                request.getParameter("university"),
                request.getParameter("institute"),
                request.getParameter("chair"),
                request.getParameter("position"),
                request.getParameter("phone"),
                acadStatusService.findAcadStatus(request.getParameter("acadStatus")),
                sciDegreeService.finSciDegree(request.getParameter("sciDegree")),
                userSexService.findUserSex(request.getParameter("userSex"))
        );

        String interestsStr = request.getParameter("keyWords");
        Set<UserInterest> interests = userInterestService.setUserInterests(interestsStr, newUser);
        newUser.setInterests(interests);

        try {
            userService.updateUser(oldUser, newUser, multipartFile);
        } catch (RegistrationException e) {
            map.put("errorMessage", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public String uploadFile (MultipartHttpServletRequest request)throws Exception {
//        log.info("/upload controller");
//        MultipartFile multipartFile = request.getFile("file");
//        StringBuilder sb = new StringBuilder();
//        String username = multipartFile.getName();
//        String type = multipartFile.getContentType();
//        sb.append("../userPhotos/").append(username).append(type);
//        String photoAddress = sb.toString();
//        multipartFile.transferTo(new File(photoAddress));//todo do exception, if it occurs redirect to another page
//        return "OK";
//    }

}
