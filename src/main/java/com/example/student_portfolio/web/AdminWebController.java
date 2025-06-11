// src/main/java/com/example/student_portfolio/controller/AdminWebController.java
package com.example.student_portfolio.controller;

import com.example.student_portfolio.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    private final UserService userService;
    public AdminWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_admin";  // templates/users_admin.html
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        // model.addAttribute("stats", userService.getSystemStats());
        return "reports";  // templates/reports.html
    }
}
