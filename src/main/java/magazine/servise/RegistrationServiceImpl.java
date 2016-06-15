package magazine.servise;

import magazine.Exeptions.AdminRegistrationException;
import magazine.Exeptions.RegistrationException;
import magazine.Exeptions.SuchUserExistException;
import magazine.dao.UserDao;
import magazine.dao.UserInterestDao;
import magazine.dao.UserRoleDao;
import magazine.domain.*;
import magazine.utils.Messenger;
import magazine.utils.PasswordHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.io.File;

import java.util.HashSet;
import java.util.Set;
import javax.mail.*;


/**
 * Created by pvc on 30.10.2015.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {
    public static final Logger log = Logger.getLogger(RegistrationServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    AcadStatusService acadStatusService;

    @Autowired
    SciDegreeService sciDegreeService;

    @Autowired
    UserInterestDao userInterestDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserSexService userSexService;

    @Autowired
    PasswordHelper passwordHelper;

    @Value("${initialPath}")
    private String initialPath;

    @Value("${adminPassword}")
    private String adminPassword;

    @Value("${domainName}")
    private String domainName;

    public RegistrationServiceImpl() {
    }

    @Override
    public void regUser(String userInformationJSON) throws RegistrationException{//todo винести окремі задачі в окремі методи

        User user = formUserFromJSONString(userInformationJSON);

        //registrationException
        int regCode = sentRegistrationMessage(user);
        user.setRestoreCode(regCode);

        try {
            userService.createUser(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RegistrationException("Виникли проблеми з реєстрацією." +
                    "Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора");
        }
    }

    @Override
    public void updateUser(String userStr, User user) throws RegistrationException {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(userStr);
            JSONObject jsonObj = (JSONObject) obj;

            String newUsername = (String) jsonObj.get("username");
            boolean isUserExist = checkIfUserExist(newUsername);
            if (isUserExist){
                user.setUserName(newUsername);
            }

            String newName = (String) jsonObj.get("name");
            if (!newName.equals("")){
                user.setName(newName);
            }

            String newSurname = (String) jsonObj.get("surname");
            if (!newSurname.equals("")){
                user.setSurname(newSurname);
            }

            String newMiddleName = (String) jsonObj.get("middleName");
            if (!newMiddleName.equals("")){
                user.setMiddleName(newMiddleName);
            }

            String newUniversity = (String) jsonObj.get("university");
            if (!newUniversity.equals("")){
                user.setUniversity(newUniversity);
            }

            String newInstitute = (String) jsonObj.get("institute");
            if (!newInstitute.equals("")){
                user.setInstitute(newInstitute);
            }

            String newChair = (String) jsonObj.get("chair");
            if (!newChair.equals("")){
                user.setChair(newChair);
            }

            String newPosition = (String) jsonObj.get("position");
            if (!newPosition.equals("")){
                user.setPosition(newPosition);
            }

            String newPhone = (String) jsonObj.get("phone");
            if (!newPhone.equals("")){
                user.setPhone(newPhone);
            }

            String newPassword = (String) jsonObj.get("password");
            if (!newPassword.equals("")) {
                String encodedPassword = passwordHelper.encode(newPassword);
                user.setPassword(encodedPassword);
            }

            String newAcadStatus = (String) jsonObj.get("acadStatus");
            if (!newAcadStatus.equals("")) {
                try {
                    UserAcadStatus acadStatus = acadStatusService.findByString(newAcadStatus);
                    user.setAcadStatus(acadStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String newSciDegree = (String) jsonObj.get("sciDegree");
            if (!newSciDegree.equals("")) {
                try {
                    UserSciDegree sciDegree = sciDegreeService.findByString(newSciDegree);
                    user.setSciDegree(sciDegree);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String newUserSex = (String) jsonObj.get("userSex");
            if (!newSciDegree.equals("")) {
                try {
                    UserSex userSex = userSexService.findByString(newUserSex);
                    user.setUserSex(userSex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String interestsStr = (String) jsonObj.get("interests");
            Set<UserInterest> interests = setUserInterests(interestsStr, user);
            user.setInterests(interests);

            /**
             * Оновлення фото, видалення старого і запис нового.
             */
            String oldFileName = (String) jsonObj.get("photo");
            if (!oldFileName.equals("")) {
                String photoAddress = user.getPhotoAddress();
                deleteImage(photoAddress);
                String fileName;
                if (newUsername.equals("")){
                    fileName = user.getUsername();
                } else {
                    fileName = newUsername;
                }
                String newPhotoAddress = renameImage(oldFileName, fileName);
                user.setPhotoAddress(newPhotoAddress);
            }

            userService.changeUser(user);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RegistrationException("Виникли проблеми зі зміною даних." +
                    "Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора");
        }
    }


    private int sentRegistrationMessage(User user) throws RegistrationException {

        //set restoration/registration code
        int regCode = 1111 + (int) (Math.random() * ((9999 - 1111) + 1));

        String receiver = user.getUsername();
        String message = "Ви реєструвались в журналі Енергетика, автоматика і енергозбереження."
                + "<br> для підтердження реєстрації перейдіть за посиланням "
                + domainName + "confirmRegistration?" + "userName=" + receiver + "&regCode=" + regCode
                + "<br><br> Regards, Admin";//todo here write proper jsp page

        try {
            Messenger messenger = new Messenger();
            messenger.sendMessage(receiver, message);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
            throw new RegistrationException("Неможливо відправити повідомлення на пошту. " +
                    "Перевірте правильність вашої електронної адреси та підключення до інтернету");
        }
        return regCode;
    }



    private User formUserFromJSONString (String userInformationJSON)throws SuchUserExistException, AdminRegistrationException{

        User user;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(userInformationJSON);
            JSONObject jsonObj = (JSONObject) obj;

            String username = (String) jsonObj.get("username");
            String password = (String) jsonObj.get("password");
            String name = (String) jsonObj.get("name");
            String surname = (String) jsonObj.get("surname");
            String middleName = (String) jsonObj.get("middleName");
            String university = (String) jsonObj.get("university");
            String institute = (String) jsonObj.get("institute");
            String chair = (String) jsonObj.get("chair");
            String position = (String) jsonObj.get("position");
            String phone = (String) jsonObj.get("phone");
            String acadStatusStr = (String) jsonObj.get("acadStatus");
            String sciDegreeStr = (String) jsonObj.get("sciDegree");
            String userSexStr =(String) jsonObj.get("userSex");
            String photoName = (String) jsonObj.get("photo");
            String adminRole = (String) jsonObj.get("isAdministrator");
            String interestsStr = (String) jsonObj.get("interests");

            //throws SuchUserExistException
            checkIfUserExist(username);

            String encodedPassword = passwordHelper.encode(password);

            UserAcadStatus acadStatus = getAcadStatus(acadStatusStr);
            UserSciDegree sciDegree = getSciDegree(sciDegreeStr);
            UserSex userSex = getUserSex(userSexStr);
            String photoAddress = setPhotoAddress(username, photoName);

            user = new User(username, encodedPassword, name, surname, middleName, university, institute, chair, position, phone, photoAddress, acadStatus, sciDegree, userSex);

            //throws AdminRegistrationException
            Set<UserRole> userRoles = setUserRoles(user, password, adminRole);
            user.setUserRoles(userRoles);

            Set<UserInterest> interests = setUserInterests(interestsStr, user);
            user.setInterests(interests);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Неможливо зареєструватись. Перевірте вірність введених даних.");
        }

        return user;
    }


    private UserAcadStatus getAcadStatus(String acadStatusStr) {
        try {
          return acadStatusService.findByString(acadStatusStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private UserSciDegree getSciDegree(String sciDegreeStr) {
        try {
            return sciDegreeService.findByString(sciDegreeStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private UserSex getUserSex(String userSexStr) {
        try {
            return userSexService.findByString(userSexStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException();//todo перевірити
        }
    }

    /**
     * Якщо користувач додав фото photoName (!photoName.equals("")), то перейменовуємо його
     * задля запобігання двох однакових імен файлів. Якщо фотографії немає, то як адреса для
     * фото встановлюється стандартна з images.
     */
    private String setPhotoAddress(String username, String photoName) {
        if (!photoName.equals("")) {
            return renameImage(photoName, username);
        } else {
            return "../../images/noPhotoMan.png";
        }
    }

    private Set<UserRole> setUserRoles(User user, String password, String adminRole) throws AdminRegistrationException {
        Set<UserRole> userRoles = user.getUserRoles();
        if (adminRole.equals("Administrator")) {
            if (!password.equals(adminPassword)) {
                throw new AdminRegistrationException("Ви не маєте права реєструватись як адміністратор!");
            }
            UserRole superAdmin = userRoleDao.getUserRole(ListRole.SUPERADMIN);
            UserRole admin = userRoleDao.getUserRole(ListRole.ADMIN);
            userRoles.add(admin);
            userRoles.add(superAdmin);
        }
        UserRole userRole = userRoleDao.getUserRole(ListRole.USER);
        userRoles.add(userRole);
        return userRoles;
    }



    private void deleteImage (String oldPhotoAddress){
        int index = oldPhotoAddress.lastIndexOf("userPhotos/");
        if (index == -1) return;
        String simplePath = oldPhotoAddress.substring(index);
        String photoAddress =  initialPath + simplePath;
        File file = new File(photoAddress);
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Перейменовує ім'я фотографії задля запобігання двох однакових імен файлів,
     * і додає до назви рандомне число, для того щоб браузер не брав файл з кеша при зміні фото
     * (назва при зміні залишається тою ж, змінюється лише файл).
     */
    private String renameImage (String oldFileName, String username){
        String photoAddress =  initialPath + "userPhotos/" + oldFileName;
        int index = oldFileName.lastIndexOf(".");
        String fileType = oldFileName.substring(index + 1);
        String newPhotoName = username + (int) (Math.random()*100+100) +  "." + fileType;
        String newPhotoAddress = initialPath + "userPhotos/" + newPhotoName;
        File file = new File(photoAddress);
        try {
            file.renameTo(new File(newPhotoAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPhotoName;
    }

    /**
     * Парсить String інтересів користувача, строку ділить на частини відоркремлені комою,
     * і заносить інтерес до БД.
     */
    private Set<UserInterest> setUserInterests(String interestsStr, User user){
        String[] interestsArrStr = interestsStr.split("\\,");
        Set<UserInterest> interests = new HashSet<>();
        if (interestsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            for (String interest : interestsArrStr) {
                if (interest.charAt(0) == ' ') {
                    interest = interest.replaceFirst(" ", "");
                }
                UserInterest userInterest;
                try {
                    userInterest = userInterestDao.getInterest(interest.toLowerCase());
                    Set <User> userSet = userInterest.getUsers();//todo catch exception above and throw another one
                    userSet.add(user);
                    userInterestDao.update(userInterest);
                } catch (NullPointerException e) {//якщо в БД немає interest
                    userInterest = new UserInterest(interest.toLowerCase());
                    Set<User> userSet = new HashSet<>();
                    userInterest.setUsers(userSet);
                    userSet.add(user);
                    userInterestDao.create(userInterest);
                }
                interests.add(userInterest);
            }
        } else {
            interests = null;
        }
        return interests;
    }

    private boolean checkIfUserExist (String username) throws SuchUserExistException {
        if (username.equals("")){
           throw new IllegalArgumentException("Електронна адреса username не вказана.");
        }
        try {//спробуємо знайти юзера за username
            userDao.findByUsername(username);//якщо існує кидаємо RegistrationException
            throw new SuchUserExistException("Користувач з поштою \"" + username + "\" існує. Спробуйте іншу.");
        } catch (UsernameNotFoundException e) {
            log.info("Реєстрація нового користувача, або заміна його username: " + username);
            return true;
        }
    }

}
