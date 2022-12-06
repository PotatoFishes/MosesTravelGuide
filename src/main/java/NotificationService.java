import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class NotificationService {
    public static void notify(User u) {
        String username = "deeznuffs@hotmail.com";
        String password = "deeznuts69";

        // Assuming you are sending email from localhost
        String host = "localhost";


        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("deeznuffs@hotmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(u.getEmail()));
            message.setSubject("Check Calender");

            String msg = "Event Ended";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
