package magazine.servise;

import magazine.Exeptions.RegistrationException;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by pvc on 16.05.2016.
 */
@Service
public class MessageService {
    public static final Logger log = Logger.getLogger(MessageService.class);

    @Value("${domainName}")
    private String domainName;

    public void sendMessage(String receiver, String message) throws MessagingException{
        log.info("sendMessage.method");

//        String receiver = user.getUsername();
//        String receiver = "v_cheslav@ukr.net";

        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        mailServerProperties = System.getProperties();

        mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailServerProperties.put("mail.stmp.user", "magazineeae@gmail.com");
        //TLS
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.password", "eaepassword");
        //SSL
        mailServerProperties.put("mail.smtp.socketFactory.port", "465");
        mailServerProperties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.port", "465");


//        mailServerProperties.put("mail.smtp.socketFactory.port", "465");
//        mailServerProperties.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//
//
//        mailServerProperties.put("mail.smtp.port", "465");
//        mailServerProperties.put("mail.smtp.auth", "true");
//        mailServerProperties.put("mail.smtp.starttls.enable", "true");

//        mailServerProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//        mailServerProperties.put("mail.smtp.connectiontimeout", 10000000);


        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        generateMailMessage.setSubject("Журнал Енергетика, автоматика і енергозбереження");
        String emailBody = message;
        generateMailMessage.setContent(emailBody, "text/html; charset=UTF-8");

        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "magazineeae@gmail.com", "eaepassword");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();

    }

    public int sendRegistrationMessage(User user) throws RegistrationException {

        //set restoration/registration code
        int regCode = 1111 + (int) (Math.random() * ((9999 - 1111) + 1));

        String receiver = user.getUsername();
        String message = "Ви реєструвались в журналі Енергетика, автоматика і енергозбереження."
                + "<br> для підтердження реєстрації перейдіть за посиланням "
                + domainName + "confirmRegistration?" + "userName=" + receiver + "&regCode=" + regCode
                + "<br><br> Regards, Admin";//todo here write proper jsp page

        try {
            MessageService messageService = new MessageService();
            messageService.sendMessage(receiver, message);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
            throw new RegistrationException("Неможливо відправити повідомлення на пошту. " +
                    "Перевірте правильність вашої електронної адреси та підключення до інтернету");
        }
        return regCode;
    }

}
