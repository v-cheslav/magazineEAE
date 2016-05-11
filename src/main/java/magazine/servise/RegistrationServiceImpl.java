package magazine.servise;

import magazine.Exeptions.RegistrationException;
//import magazine.dao.MessageDao;
import magazine.dao.UserDao;
import magazine.dao.UserInterestDao;
import magazine.dao.UserRoleDao;
import magazine.domain.*;
//import magazine.utils.TemporaryPhotoAddresses;
//import magazine.domain.Message;
import magazine.utils.PasswordHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;

import java.io.UnsupportedEncodingException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

//    @Autowired
//    MessageDao messageDao;

    @Autowired
    PasswordHelper passwordHelper;

    @Value("${initialPath}")
    private String initialPath;

    @Value("${adminPassword}")
    private String adminPassword;


    public RegistrationServiceImpl() {
    }

    /**
     * Реєстрація користувача.
     * З jsp надходить об'єкт JSON з даними користувача. Клас парсить його, перевіряє чи має
     * юзер права адміна, та передає його в DAO для запису в БД
     */
    @Override
    public void regUser(String userStr) throws RegistrationException{//todo винести окремі задачі в окремі методи
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            User user = new User();
            obj = parser.parse(userStr);
            JSONObject jsonObj = (JSONObject) obj;

            String username = (String) jsonObj.get("username");
            try {//спробуємо знайти юзера за username
                userDao.findByUsername(username).get(0);//якщо існує кидаємо RegistrationException
                throw new RegistrationException("Користувач з поштою \""+ username +"\" існує. Спробуйте іншу.");
           // todo throw new UserExistException and ask if user forgot password, if does - redirect to the page forgot password
            } catch (IndexOutOfBoundsException e) {
                log.info("Реєстрація нового користувача: " + username);
            }

            String password = (String) jsonObj.get("password");
            String encodedPassword = passwordHelper.encode(password);
            user.setPassword(encodedPassword);

            /**
             * Здійснюється перевірка чи відмітив користувач checkbox Адміністратор,
             */
            Set<UserRole> userRoles = user.getUserRoles();
            String adminRole = (String) jsonObj.get("isAdministrator");

            /**
             * перевіряємо чи дійсно має право реєструватись як Адмін,
             * (якщо так, то пароль реєстрації має співпадати із зазначеним у properties файлі).
             */
            if (adminRole.equals("Administrator")){
                UserRole superAdmin = userRoleDao.getUserRole(ListRole.SUPERADMIN);
                UserRole admin = userRoleDao.getUserRole(ListRole.ADMIN);
                userRoles.add(admin);
                userRoles.add(superAdmin);
                if(!password.equals(adminPassword)){
                    throw new RegistrationException("Ви не маєте права реєструватись як адміністратор!");
                }
            }
            UserRole userRole = userRoleDao.getUserRole(ListRole.USER);
            userRoles.add(userRole);

            /**
             * Якщо користувач додав фото photoName (!photoName.equals("")), то перейменовуємо його
             * задля запобігання двох однакових імен файлів. Якщо фотографії немає, то як адреса для
             * фото встановлюється стандартна з images.
             */
            String photoName = (String) jsonObj.get("photo");
            String newPhotoAddress;
            if (!photoName.equals("")) {
                newPhotoAddress = renameImage(photoName, username);
            } else {
                newPhotoAddress = "../../images/noPhotoMan.png";
            }
            user.setPhotoAddress(newPhotoAddress);

            UserAcadStatus acadStatus = null;
            try {
                acadStatus = acadStatusService.findByString((String) jsonObj.get("acadStatus"));
            } catch (Exception e) {
                acadStatus = null;
                e.printStackTrace();
            }

            UserSciDegree sciDegree = null;
            try {
                sciDegree = sciDegreeService.findByString((String) jsonObj.get("sciDegree"));
            } catch (Exception e) {
                sciDegree = null;
                e.printStackTrace();
            }

            UserSex userSex = null;
            try {
                userSex = userSexService.findByString((String)jsonObj.get("userSex"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String interestsStr = (String) jsonObj.get("interests");
            Set<UserInterest> interests = userInterestFormer(interestsStr, user);

            user.setUserName(username);
            user.setName((String) jsonObj.get("name"));
            user.setSurname((String) jsonObj.get("surname"));
            user.setMiddleName((String) jsonObj.get("middleName"));
            user.setUniversity((String) jsonObj.get("university"));
            user.setInstitute((String) jsonObj.get("institute"));
            user.setChair((String) jsonObj.get("chair"));
            user.setPosition((String) jsonObj.get("position"));
            user.setPhone((String) jsonObj.get("phone"));
            user.setAcadStatus(acadStatus);
            user.setSciDegree(sciDegree);
            user.setUserSex(userSex);
            user.setInterests(interests);
            user.setUserRoles(userRoles);

            try {
                userService.createUser(user);
//                sendMessage(user);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RegistrationException("Виникли проблеми з реєстрацією." +
                        "Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RegistrationException("Виникли проблеми з реєстрацією." +
                    "Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора");
        }

    }

    /**
     * Оновлення даних користувача.
     * З jsp надходить об'єкт JSON з даними користувача. Клас парсить його, змінює дані юзера
     * та передає його в DAO для оновлення в БД
     */
    @Override
    public void updateUser(String userStr, User user) throws RegistrationException {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(userStr);
            JSONObject jsonObj = (JSONObject) obj;

            /**
             * Перевірка на існування в БД username. Якщо існує кидаємо RegistrationException.
             */
            String newUsername = (String) jsonObj.get("username");//todo перевірити чи існує username
            if (!newUsername.equals("")){
                try {//спробуємо знайти юзера за username
                    userDao.findByUsername(newUsername).get(0);//якщо існує кидаємо RegistrationException
                    throw new RegistrationException("Користувач з поштою \""+ newUsername +"\" зареєстрований.");
                } catch (IndexOutOfBoundsException e) {
                    user.setUserName(newUsername);
                    log.info("Зміна даних користувача: " + user.getUsername());
                }
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
            System.err.println("newPassword = '"+newPassword+"'");
            if (!newPassword.equals("")) {
                user.setPassword(newPassword);
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
            Set<UserInterest> interests = userInterestFormer(interestsStr, user);
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
    private Set<UserInterest>  userInterestFormer (String interestsStr, User user){
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


    private void sendMessage(User user){

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "HELLO";

        String to = user.getUsername();
        String from = "v.cheslav84@gmail.com";


        try {
            javax.mail.Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, "Example.com Admin"));
            msg.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(to, "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }



//        String to = user.getUsername();
//        String from = "v.cheslav84@gmail.com";
//
//        String host = "localhost";
//
//        Properties properties = System.getProperties();
//
//        properties.setProperty("mail.smtp.host", host);
//
//        Session session = Session.getDefaultInstance(properties);
//
//
//        try{
//            MimeMessage message = new MimeMessage(session);
//
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
//
//            message.setSubject("This is the Subject Line!");
//
//            message.setText("This is actual message");
//
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        }catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
    }
}
