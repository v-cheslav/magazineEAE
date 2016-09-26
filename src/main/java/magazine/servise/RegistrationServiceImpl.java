package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;
import magazine.utils.PasswordHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by pvc on 30.10.2015.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {
    public static final Logger log = Logger.getLogger(RegistrationServiceImpl.class);

    @Value("${initialPath}")
    private String initialPath;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    MessageService messageService;

    @Autowired
    FileService fileService;

    public RegistrationServiceImpl() {
    }


    @Override
    public void regUser(User user, MultipartFile userImage) throws RegistrationException {
        log.info("RegistrationServiceImpl.regUser method");

        checkIfUserCorrect(user);
        userService.checkIfUserExist(user.getUsername());

        String encodedPassword = passwordHelper.encode(user.getPassword());
        user.setPassword(encodedPassword);

        int registrationCode = messageService.sendRegistrationMessage(user);
        user.setRestoreCode(registrationCode);

        fileService.saveUserImage(user, userImage);

        try {
            userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegistrationException("Виникли проблеми з реєстрацією." +
                    "Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора");
        }
    }


    private boolean checkIfUserCorrect(User user) throws RegistrationException{

        if (user == null){
            throw new IllegalArgumentException("User couldn't be null");
        }

        String username = user.getUsername();
        if (username.toCharArray().length < 5){
            checkIfUserPasswordCorrect(user.getPassword());
            throw new IllegalArgumentException("Електронна адреса username вказана не вірно.");
        }

        String password = user.getPassword();
        if (password.toCharArray().length < 6){
            throw new IllegalArgumentException("Пароль вказаний не вірно.");
        }

        String name = user.getName();
        if (name.toCharArray().length < 2){
            throw new IllegalArgumentException("Ім'я вказане не вірно.");
        }

        String surname = user.getSurname();
        if (surname.toCharArray().length < 2){
            throw new IllegalArgumentException("Прізвище вказане не вірно.");
        }

        String university = user.getUniversity();
        if (university.toCharArray().length < 3){
            throw new IllegalArgumentException("Університет вказаний не вірно.");
        }

        return true;
    }

    @Override
    public boolean checkIfUserPasswordCorrect(String password)throws RegistrationException{
        log.info("checkIfUserPasswordCorrect method");

        //пароль повинен включати великі та малі літери, цифри, та бути не менше 8ми символів
        String regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{8,}$";
        Pattern pattern = Pattern.compile(regexp);

        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()){
            log.error("Пароль не валідний.");
            throw new RegistrationException("Пароль не валідний.");
        }
        return true;
    }


}
