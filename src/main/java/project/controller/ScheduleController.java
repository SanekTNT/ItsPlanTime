package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.components.googleAPI.GoogleFiles;
import project.repos.ActivationCodeRepo;
import project.service.FileService;
import project.service.MessageService;
import project.tables.File;
import project.tables.Message;
import project.tables.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ScheduleController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ActivationCodeRepo activationCodeRepo;

    @Autowired
    private GoogleFiles googleFiles;

    @GetMapping("/schedule")
    public String schedule(@AuthenticationPrincipal User user, Model model){
        if(activationCodeRepo.findByUser(user) != null) {
            return "redirect:/accountNotActivated";
        }
        addingWeekSchedule(user, model, null);
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.PUT)
    public String putMessageInDB(@AuthenticationPrincipal User user,
                                 @RequestParam String date, @RequestParam String time,
                                 @RequestParam String subject, @RequestParam String message,
                                 @RequestParam("file") List<MultipartFile> files,
                                 Model model){
        Message curMessage = null;
        /*if(!isTimeGood(time)) { //todo проверка времени отправленных сообщений
            model.addAttribute("alert", "Check time on " +
                    new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime())
                    + "! It is less than now.");
            curMessage = new Message(date, "--:--", subject, message, user);
        }
        else { */
            Message objMessage = new Message(date, time, subject, message, user);
            messageService.addMessage(objMessage);
            if(files != null){
                for(MultipartFile file : files) {
                    if (file.isEmpty())
                        break;
                    else {
                        addFileInDB(files, objMessage, user);
                        break;
                    }
                }
            }
        //}
        addingWeekSchedule(user, model, curMessage);
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.DELETE)
    public String deleteMessageFromDB(@AuthenticationPrincipal User user, @RequestParam String date, Model model){
        fileService.deleteFilesByMessage(messageService.findMessageByUserAndDate(user, date));
        messageService.deleteMessageByUserAndDate(user, date);
        addingWeekSchedule(user, model, null);
        return "schedule";
    }

    private void addingWeekSchedule(User user, Model model, Message messageWithWrongTime){
        SimpleDateFormat dayFormatName = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat dayFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> daysNames = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        HashMap<String, List<String>> hashMapFiles = new HashMap<>();
        if(messageWithWrongTime == null)
            messageWithWrongTime = new Message();
        for(int i=0; i<8; i++) {
            daysNames.add(dayFormatName.format(calendar.getTime()));
            dates.add(dayFormatDate.format(calendar.getTime()));
            if(dayFormatDate.format(calendar.getTime()).equals(messageWithWrongTime.getDate())) {
                messages.add(messageWithWrongTime);
                hashMapFiles.put(dayFormatDate.format(calendar.getTime()), null);
            } else {
                messages.add(messageService.findMessageByUserAndDate(user, dayFormatDate.format(calendar.getTime())));
                if (messages.get(i) == null) {
                    messages.set(i, new Message(dayFormatDate.format(calendar.getTime()), "--:--",
                            "", "", user));
                    hashMapFiles.put(dayFormatDate.format(calendar.getTime()), null);
                } else {
                    List<File> fileList = fileService.findFilesByMessage(messages.get(i));
                    ArrayList<String> fileNames = new ArrayList<>();
                    for(File file : fileList){
                        fileNames.add(file.getFileNameWithoutCode());
                    }
                    hashMapFiles.put(dayFormatDate.format(calendar.getTime()), fileNames);
                }
            }
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }
        model.addAttribute("daysNames", daysNames);
        model.addAttribute("dates", dates);
        model.addAttribute("messages", messages);
        model.addAttribute("files", hashMapFiles);
    }

    private boolean isTimeGood(String time){
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        String timeNow = formatTime.format(calendar.getTime());
        if(timeNow.compareTo(time) >=0 )
            return false;
        else
            return true;
    }

    private void addFileInDB(List<MultipartFile> files, Message message, User user) {
        for(MultipartFile file : files) {
            System.out.println("File!!!!");
            File fileInDB = new File(message);
            if (file != null) {
                String uuidFile = UUID.randomUUID().toString();
                String fileName = uuidFile + "." + file.getOriginalFilename();
                try {
                    java.io.File fileForSending = new java.io.File(fileName);
                    FileOutputStream fos = new FileOutputStream(fileForSending);
                    fos.write(file.getBytes());
                    fos.close();
                    googleFiles.setFile(fileForSending, fileInDB, user);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                fileInDB.setFileName(fileName);
                fileService.saveFile(fileInDB);
            }
        }
    }

}
