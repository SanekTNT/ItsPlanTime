package project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.tables.User;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "greeting";
    }

    @GetMapping("/accountNotActivated")
    public String accountNotActivated(Model model) {
        return "accountNotActivated";
    }

    @GetMapping("/profile")
    public String main(@AuthenticationPrincipal User user) {
        return "profile";
    }

}
