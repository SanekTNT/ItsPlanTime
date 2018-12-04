package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.repos.FileRepo;
import project.tables.File;
import project.tables.Message;

import java.util.List;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepo fileRepo;

    public List<File> findFilesByMessage(Message message){
        return fileRepo.findByMessage(message);
    }

    public void deleteFilesByMessage(Message message){
        fileRepo.deleteByMessage(message);
    }

    public void saveFile(File file){
        fileRepo.save(file);
    }

    public void deleteAllFilesByMessages(List<Message> messages){
        for(Message message : messages){
            fileRepo.deleteByMessage(message);
        }
    }


}
