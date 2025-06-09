package com.example.student_portfolio.service;

import com.example.student_portfolio.payload.PortfolioDto;

public interface PortfolioService {
    PortfolioDto getPortfolioByStudent(Long studentId);
}
