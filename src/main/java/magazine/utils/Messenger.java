package magazine.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

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
public class Messenger {
    public static final Logger log = Logger.getLogger(Messenger.class);


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
}
