package magazine.servise;

import magazine.Exeptions.AdminRegistrationException;
import magazine.Exeptions.RegistrationException;
import magazine.domain.*;
import magazine.utils.PasswordHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserBuilderImpl implements UserBuilder {
    public static final Logger log = Logger.getLogger(UserBuilderImpl.class);


    @Value("${adminPassword}")
    private String adminPassword;

    @Autowired
    AcadStatusService acadStatusService;
    @Autowired
    SciDegreeService sciDegreeService;
    @Autowired
    UserSexService userSexService;
    @Autowired
    PasswordHelper passwordHelper;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserInterestService userInterestService;

    User user;

    @Override
    public User buildUser(MultipartHttpServletRequest userHttpRequest) throws RegistrationException {
        log.info("buildUser.method");

        user = new User();
        user.setUserName(userHttpRequest.getParameter("username"));
        user.setName(userHttpRequest.getParameter("name"));
        user.setSurname(userHttpRequest.getParameter("surname"));
        user.setMiddleName(userHttpRequest.getParameter("middleName"));
        user.setUniversity(userHttpRequest.getParameter("university"));
        user.setInstitute(userHttpRequest.getParameter("institute"));
        user.setChair(userHttpRequest.getParameter("chair"));
        user.setPosition(userHttpRequest.getParameter("position"));
        user.setPhone(userHttpRequest.getParameter("phone"));
        user.setIsReviewer(false);
        user.setValid(false);
        user.setPublicationNumber(0);
        user.setInterests(new HashSet<>());
        user.setArticlesSet(new HashSet<>());
        user.setReviewSet(new HashSet<>());
        user.setAuthorCommentsSet(new HashSet<>());

        user.setPassword(passwordHelper.encode(userHttpRequest.getParameter("password")));

        user.setAcadStatus(acadStatusService.findAcadStatus(userHttpRequest.getParameter("acadStatus")));
        log.info("User academic status added");
        user.setSciDegree(sciDegreeService.finSciDegree(userHttpRequest.getParameter("sciDegree")));
        log.info("User scientific degree added");
        user.setUserSex(userSexService.findUserSex(userHttpRequest.getParameter("userSex")));
        log.info("User sex added");

        Set<UserRole> userRoles = getUserRoles(userHttpRequest);
        user.setUserRoles(userRoles);

        String interestsStr = userHttpRequest.getParameter("keyWords");
        log.info("User interests: \'" + interestsStr + "\'");
        Set<UserInterest> interests = userInterestService.setUserInterests(interestsStr, user);
        user.setInterests(interests);

        return user;
    }


    private Set<UserRole> getUserRoles(MultipartHttpServletRequest userHttpRequest) throws AdminRegistrationException{
        log.info("getUserRoles.method");
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = userRoleService.findUserRole(ListRole.USER);
        userRoles.add(userRole);
        log.info("Set \'USER\' role");

        boolean hasUserAdminRights = checkAdminRights(userHttpRequest);
        if (hasUserAdminRights) {
            UserRole adminRole = userRoleService.findUserRole(ListRole.ADMIN);
            userRoles.add(adminRole);
            log.info("Set \'ADMIN\' role");
            UserRole superadminRole = userRoleService.findUserRole(ListRole.SUPERADMIN);
            userRoles.add(superadminRole);
            log.info("Set \'SUPERADMIN\' role");
        }
        return userRoles;
    }

    private boolean checkAdminRights (MultipartHttpServletRequest userHttpRequest) throws AdminRegistrationException{
        log.info("checkAdminRights.method");
        String adminRole = userHttpRequest.getParameter("adminChBox");
        if (adminRole == null) {
            log.info("User don't has \'ADMIN\' rights.");
            return false;
        }

        String password = userHttpRequest.getParameter("password");
        if (!password.equals(adminPassword)) {
            log.warn("Attempt to register as admin without admin rights!");
            throw new AdminRegistrationException("Ви не маєте права реєструватись як адміністратор!");
        }
        log.info("User has \'ADMIN\' rights.");
        return true;
    }


}
