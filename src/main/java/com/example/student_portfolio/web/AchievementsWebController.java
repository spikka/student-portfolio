// src/main/java/com/example/student_portfolio/controller/AchievementsWebController.java
package com.example.student_portfolio.controller;

import com.example.student_portfolio.payload.AchievementDto;
import com.example.student_portfolio.service.AchievementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/achievements")
public class AchievementsWebController {

    private final AchievementService achievementService;
    public AchievementsWebController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("achievements", achievementService.getAll());
        return "achievements_list";  // templates/achievements_list.html
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("achievement", new AchievementDto());
        return "achievement_form";  // templates/achievement_form.html
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("achievement", achievementService.getById(id));
        return "achievement_form";
    }
}
