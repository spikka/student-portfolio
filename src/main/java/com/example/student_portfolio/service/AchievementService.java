package com.example.student_portfolio.service;

import com.example.student_portfolio.model.Achievement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AchievementService {
    Achievement createAchievement(Achievement achievement, MultipartFile file);
    Achievement updateAchievement(Long id, Achievement updated, MultipartFile file);
    void deleteAchievement(Long id);
    Achievement getById(Long id);
    List<Achievement> getAll();
}
