package project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tables.Message;
import project.tables.User;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByDateAndTimeAndAlreadySent(String date, String time, boolean alreadySent);
    List<Message> findByDateAndAlreadySent(String date, boolean alreadySent);
    Message findByUserAndDate(User user, String date);
    void deleteAllByDateAndAlreadySent(String date, boolean alreadySent);
    void deleteAllByUserAndDate(User user, String date);
}

