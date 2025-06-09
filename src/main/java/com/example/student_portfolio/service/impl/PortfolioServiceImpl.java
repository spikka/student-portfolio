package com.example.student_portfolio.service.impl;

import com.example.student_portfolio.model.Comment;
import com.example.student_portfolio.payload.*;
import com.example.student_portfolio.repository.*;
import com.example.student_portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioServiceImpl implements PortfolioService {

    private final AchievementRepository achievementRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    @Override
    public PortfolioDto getPortfolioByStudent(Long studentId) {
        // 1) Список достижений
        var achievements = achievementRepository.findAllByStudentId(studentId);

        // 2) Средний рейтинг
        Double avg = ratingRepository.findAverageByStudentId(studentId);
        if (avg == null) avg = 0.0;

        // 3) Последние 5 комментариев
        List<Comment> recent = commentRepository
                .findTop5ByAchievementStudentIdOrderByCreatedAtDesc(studentId);

        // Маппим комменты в DTO
        List<CommentDto> commentDtos = recent.stream().map(c -> {
            var dto = new CommentDto();
            dto.setId(c.getId());
            dto.setAchievementId(c.getAchievement().getId());
            dto.setAuthorId(c.getAuthor().getId());
            dto.setText(c.getText());
            dto.setCreatedAt(c.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        // Собираем портфолио
        PortfolioDto portfolio = new PortfolioDto();
        portfolio.setAchievements(achievements);
        portfolio.setAverageRating(avg);
        portfolio.setRecentComments(commentDtos);
        return portfolio;
    }
}
