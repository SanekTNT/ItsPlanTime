package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import project.tables.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void send(String mail, String subject, String text, List<File> files){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(mail);
            helper.setSubject(subject);
            helper.setText(text);
            if(files.size() != 0){
                text += "\nFiles:";
                for(File file : files){
                    text = text + "\n" + file.getGoogleLink();
                    /*FileSystemResource fileSystemResource = new FileSystemResource(
                            uploadPath + "/" + file.getFilePath());
                    helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
                    */
                }
                helper.setText(text);
            }
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new MailParseException(e);
        }
        javaMailSender.send(message);
    }

}
