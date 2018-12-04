package project.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.repos.MessageRepo;
import project.service.FileService;
import project.service.MailService;
import project.tables.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Component
@Transactional
public class MailThread {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Scheduled(fixedRate = 5000)
    public void getAllMessagesFromDB() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        //todo разобраться с ебучими часовыми поясами
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
        String date = formatDate.format(calendar.getTime());
        String time = formatTime.format(calendar.getTime());
        System.out.println("it's scheduled with time " + time);
        Iterable<Message> messages;
        messages = messageRepo.findByDateAndTimeAndAlreadySent(date, time, false);
        for(Message message : messages){
            System.out.println("Sending message to " + message.getUser().getEmail());
            mailService.send(message.getUser().getEmail(), message.getSubject(), message.getText(),
                    fileService.findFilesByMessage(message));
            message.setAlreadySent(true);
            messageRepo.save(message);
        }
        if(time.equals("00:00")) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
            deleteFromDB(formatDate.format(calendar.getTime()));
        }
    }

    private void deleteFromDB(String date){
        List<Message> messages = messageRepo.findByDateAndAlreadySent(date, true);
        fileService.deleteAllFilesByMessages(messages);
        messageRepo.deleteAllByDateAndAlreadySent(date, true);
    }
}
