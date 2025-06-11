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
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public String profile(Model model, Principal principal) {
        User user = userService.getCurrentUser(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute("user") User upd, Principal principal) {
        userService.updateProfile(principal, upd);
        return "redirect:/dashboard";
    }
}
