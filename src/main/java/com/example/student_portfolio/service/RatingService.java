package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Rating;

public interface RatingService {
    /**
     * Добавляет новую оценку или обновляет существующую.
     */
    Rating addOrUpdateRating(Long achievementId, Long authorId, int stars);

    /**
     * Возвращает среднюю оценку (или 0.0, если нет оценок).
     */
    double getAverageRating(Long achievementId);
}
