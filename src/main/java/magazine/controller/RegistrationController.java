package magazine.controller;

import magazine.Exeptions.RegistrationException;
import magazine.domain.*;
import magazine.servise.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;


@Controller
public class RegistrationController {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Value("${adminPassword}")
    private String adminPassword;

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

    @Autowired
    UserBuilder userBuilder;


    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/registration", method = {RequestMethod.GET})
    public String registration() {
        log.info("/registration controller");
        return "registration";
    }


    @RequestMapping(value = "/regUser", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> regUser(MultipartHttpServletRequest request)  {
        log.info("/regUser controller");
        HashMap<String, Object> map = new HashMap<>();

        User user = null;
        try {
            user = userBuilder.buildUser(request);
            log.info("User \'" + user.toString() + "\' successfully built.");
            fileService.saveAndSetUserPhoto(user, request);
            log.info("User image successfully saved.");
        } catch (RegistrationException e) {
            e.printStackTrace();
        }

        try {
            registrationService.regUser(user);
            map.put("success", "success");
        } catch (RegistrationException e) {
            map.put("registrationMassage", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody
    HashMap<String, Object> updateUser(MultipartHttpServletRequest request)  {
        log.info("/updateUser controller");

        User oldUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            oldUser = (User) authentication.getPrincipal();
        }

        HashMap<String, Object> map = new HashMap<>();

        User newUser = null;
        try {
            newUser = userBuilder.buildUser(request);
            log.info("User \'" + newUser.toString() + "\' successfully built.");
            fileService.changeUserPhoto(oldUser, request);
            log.info("User image successfully changed.");
        } catch (RegistrationException e) {
            e.printStackTrace();
        }

        try {
            userService.updateUser(oldUser, newUser);
        } catch (RegistrationException e) {
            map.put("errorMessage", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

}
