package project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.repos.ActivationCodeRepo;
import project.repos.UserRepo;
import project.tables.ActivationCode;
import project.tables.User;

import java.util.UUID;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ActivationCodeRepo activationCodeRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public String addUser(User user){
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if(userFromDB != null) {
            return "username";
        }
        userFromDB = userRepo.findByEmail(user.getEmail());
        if(userFromDB != null) {
            return "email";
        }
        saveActivationCode(new ActivationCode(user, UUID.randomUUID().toString()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendMessageWithActivationCode(user);

        return "ok";
    }

    public boolean activateUser(String code) {
        User user = activationCodeRepo.findByCode(code).getUser();
        if(user == null){
            return false;
        }
        activationCodeRepo.deleteByUser(user);
        userRepo.save(user);
        return true;
    }

    private void sendMessageWithActivationCode(User user){
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s!\n" +
                            "Code for account activation: " +
                            "http://its-plan-time.herokuapp.com/activate/%s",
                    user.getUsername(), activationCodeRepo.findByUser(user).getCode());
            mailService.send(user.getEmail(), "Activation code", message, null);
        }
    }

    private void saveActivationCode(ActivationCode activationCode){
        activationCodeRepo.save(activationCode);
    }
}
