package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Achievement;
import com.example.student_portfolio.model.AchievementType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface AchievementService {
    Achievement createAchievement(Achievement achievement, MultipartFile file);
    Achievement updateAchievement(Long id, Achievement updated, MultipartFile file);
    void deleteAchievement(Long id);
    Achievement getById(Long id);
    List<Achievement> getAll();

    @Transactional(readOnly = true)
    List<Achievement> search(
            AchievementType type,
            String tag,
            String faculty,
            String group,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sort
    );

    /**
     * Фильтрация без сортировки.
     */
    List<Achievement> filterAchievements(
            AchievementType type,
            String tag,
            String faculty,
            String group,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    /**
     * Поиск + сортировка.
     *
     * @param sort "date" или "popularity"
     */
    List<Achievement> searchAchievements(
            AchievementType type,
            String tag,
            String faculty,
            String group,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sort
    );
}
