package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.repos.MessageRepo;
import project.tables.Message;
import project.tables.User;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public boolean addMessage(Message message){
        messageRepo.save(message);
        System.out.println("Successful!");
        return true;
    }

    public Message findMessageByUserAndDate(User user, String date){
        return messageRepo.findByUserAndDate(user, date);
    }

    public void deleteMessageByUserAndDate(User user, String date){
        messageRepo.deleteAllByUserAndDate(user, date);
    }
}
