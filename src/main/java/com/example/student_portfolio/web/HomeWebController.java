// src/main/java/com/example/student_portfolio/controller/HomeController.java
package com.example.student_portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {

    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "dashboard";  // templates/dashboard.html
    }
}
