package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Comment;

import java.util.List;

public interface CommentService {
    /**
     * Добавить новый комментарий к достижению.
     * @param achievementId — ID достижения
     * @param authorId      — ID пользователя, оставившего комментарий
     * @param text          — текст комментария
     */
    Comment addComment(Long achievementId, Long authorId, String text);

    /**
     * Получить все комментарии для данного достижения.
     * @param achievementId — ID достижения
     */
    List<Comment> getCommentsForAchievement(Long achievementId);
}
