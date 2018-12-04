package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.service.UserService;
import project.tables.User;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(User user, Model model){
        String answer = userService.addUser(user);
        if(answer.equals("ok")) {
            return "redirect:/login";
        }
        else if(answer.equals("username"))
            model.addAttribute("message", "User with username " + user.getUsername() + " exists!");
        else if(answer.equals("email"))
            model.addAttribute("message", "User with email" + user.getEmail() + " exists!");
        model.addAttribute("user", null);
        System.out.println(user.getUsername());
        return "registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        System.out.println("it's code: " + code);
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            model.addAttribute("message", "User was activated");
            System.out.println("User was activated");
        } else {
            model.addAttribute("message", "Activation code was not found!");
            System.out.println("Activation code was not found!");
        }
        return "login";
    }
}
