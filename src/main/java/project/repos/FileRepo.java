package project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tables.File;
import project.tables.Message;

import java.util.List;

public interface FileRepo extends JpaRepository<File, Long> {
    List<File> findByMessage(Message message);
    void deleteByMessage(Message message);
}
