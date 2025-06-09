package com.example.student_portfolio.service.impl;

import com.example.student_portfolio.model.*;
import com.example.student_portfolio.repository.*;
import com.example.student_portfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final AchievementRepository achievementRepo;
    private final UserRepository userRepo;

    @Override
    public Comment addComment(Long achievementId, Long authorId, String text) {
        // проверяем наличие achievement
        Achievement achievement = achievementRepo.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found: " + achievementId));
        // проверяем пользователя
        User author = userRepo.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + authorId));

        Comment c = new Comment();
        c.setAchievement(achievement);
        c.setAuthor(author);
        c.setText(text);
        // createdAt проставляется в конструкторе или в @Column default

        return commentRepo.save(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsForAchievement(Long achievementId) {
        // можно проверить существование achievement, но необязательно
        return commentRepo.findByAchievementIdOrderByCreatedAtAsc(achievementId);
    }
}
