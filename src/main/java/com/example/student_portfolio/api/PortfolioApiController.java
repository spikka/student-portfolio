package com.example.student_portfolio.controller;

import com.example.student_portfolio.payload.PortfolioDto;
import com.example.student_portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * Выдаёт все достижения студента,
     * средний рейтинг по ним и последние 5 комментариев.
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<PortfolioDto> getPortfolio(@PathVariable Long studentId) {
        PortfolioDto dto = portfolioService.getPortfolioByStudent(studentId);
        return ResponseEntity.ok(dto);
    }
}
