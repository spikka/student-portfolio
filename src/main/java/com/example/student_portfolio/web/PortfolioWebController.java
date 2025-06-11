package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.User;
import com.example.student_portfolio.payload.PortfolioDto;
import com.example.student_portfolio.repository.UserRepository;
import com.example.student_portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioWebController {

    private final PortfolioService portfolioService;
    private final UserRepository userRepo;

    /** Моё портфолио */
    @GetMapping("/me")
    public String myPortfolio(Model model, Principal principal) {
        // получаем объект User по email из Principal
        User current = userRepo.findByEmail(principal.getName())
                .orElseThrow();
        // вызываем сервис
        PortfolioDto dto = portfolioService.getPortfolioByStudent(current.getId());
        model.addAttribute("portfolio", dto);
        return "portfolio";  // src/main/resources/templates/portfolio.html
    }

    /** Портфолио любого студента (для teacher/admin) */
    @GetMapping("/{studentId}")
    public String studentPortfolio(@PathVariable Long studentId, Model model) {
        PortfolioDto dto = portfolioService.getPortfolioByStudent(studentId);
        model.addAttribute("portfolio", dto);
        return "portfolio";
    }
}
