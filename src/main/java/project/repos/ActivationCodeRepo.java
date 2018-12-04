package project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tables.ActivationCode;
import project.tables.User;

public interface ActivationCodeRepo extends JpaRepository<ActivationCode, Long> {
    ActivationCode findByUser(User user);
    ActivationCode findByCode(String code);
    void deleteByUser(User user);
}
