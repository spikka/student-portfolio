package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.User;
import com.example.student_portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileWebController {

    private final UserService userService;

    /** GET /profile — показать форму с текущими данными */
    @GetMapping
    public String profile(Model model, Principal principal) {
        User user = userService.getCurrentUser(principal);
        model.addAttribute("user", user);
        return "profile";  // profile.html
    }

    /** POST /profile — сохранить изменения */
    @PostMapping
    public String update(@ModelAttribute("user") User upd, Principal principal) {
        userService.updateProfile(principal, upd);
        return "redirect:/dashboard";
    }
}
