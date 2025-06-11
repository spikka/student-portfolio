// src/main/java/com/example/student_portfolio/controller/AuthViewController.java
package com.example.student_portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String login() {
        return "login";  // templates/login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register";  // templates/register.html
    }
}
