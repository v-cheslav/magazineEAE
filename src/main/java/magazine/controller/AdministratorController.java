package magazine.controller;

import magazine.domain.Seminar;
import magazine.domain.User;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pvc on 16.02.2016.
 */

@Controller
public class AdministratorController {
    public static final Logger log = Logger.getLogger(AdministratorController.class);

    @Autowired
    private AcadStatusService acadStatusService;

    @Autowired
    private SciDegreeService sciDegreeService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserSexService userSexService;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private SuperAdministratorService superAdministratorService;

    @Value("${superAdmidName}")
    private String superAdmidName;

    @Value("${superAdmidPassword}")
    private String superAdmidPassword;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/administrator", method = RequestMethod.GET)
    public String adminPage(ModelMap map) {
        log.info("/administrator controller");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            map.addAttribute("userDetails", user);
            List<Seminar> seminars = seminarService.findAllAppyied();
            map.addAttribute("applyiedSeminars", seminars);
        }
        return "administrator";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/createSeminar", method = {RequestMethod.GET})
    public String createSeminar () {
        log.info("/createSeminar controller");
        return "createSeminar";
    }


//    @RequestMapping(value = "/fillDataBase", method = RequestMethod.GET)
//    public String superAdminPage(ModelMap map) {
//        log.info("/fillDataBase controller");
//        return "fillDataBase";
//    }



    /**
     * Контроллер перевіряє дані для підтвердження заповнення бази даних
     */
    @RequestMapping(value = "/DBFiller", method = RequestMethod.POST)
    public @ResponseBody List<String> fillDB(@RequestBody String sendingData) {
        log.info("/checkData controller");
        JSONParser parser = new JSONParser();
        Object obj = null;
        List<String> list;
        try {
            obj = parser.parse(sendingData);
            JSONObject jsonObj = (JSONObject) obj;
            String saName = (String) jsonObj.get("saName");
            String saPassword = (String) jsonObj.get("saPassword");
            if (saName.equals(superAdmidName) && saPassword.equals(superAdmidPassword)){
                try {
                    acadStatusService.fillDBWihAcadStatuses();
                    sciDegreeService.fillDBWihSciDegree();
                    sectionService.fillDBWihSections();
                    userRoleService.fillDBWihUserRoles();
                    userSexService.fillDBWithUserSex();
                    list = Arrays.asList("OK");
                } catch (Exception ex){
                    list = Arrays.asList("error");
                }
            } else {
                list = Arrays.asList("error");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            list = Arrays.asList("error");
        }
        return list;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseEntity<String> deleteUser(@RequestBody Long userId){
        log.info("/deleteUser controller");
        ResponseEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=utf-8");
        try {
            User user = userService.getUser(userId);
            userService.removeUser(user);
            entity = new ResponseEntity<String>("OK", headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.OK);
        }
        return entity;
    }

}
