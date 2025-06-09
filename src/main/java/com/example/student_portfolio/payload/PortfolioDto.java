package com.example.student_portfolio.payload;

import com.example.student_portfolio.model.Achievement;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioDto {
    private List<Achievement> achievements;
    private Double averageRating;
    private List<CommentDto> recentComments;
}