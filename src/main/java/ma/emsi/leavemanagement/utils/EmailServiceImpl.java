package ma.emsi.leavemanagement.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * EmailServiceImpl
 */
@Component
@AllArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    public void sendPasswordVerificationEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@emsi.ma");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

}