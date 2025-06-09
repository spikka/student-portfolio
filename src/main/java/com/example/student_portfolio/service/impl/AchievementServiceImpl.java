package com.example.student_portfolio.service.impl;

import com.example.student_portfolio.model.Achievement;
import com.example.student_portfolio.model.User;
import com.example.student_portfolio.repository.AchievementRepository;
import com.example.student_portfolio.repository.UserRepository;
import com.example.student_portfolio.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    // Папка для хранения файлов (можно вынести в application.properties)
    private final Path storageDir = Paths.get("uploads/achievements");

    @Override
    public Achievement createAchievement(Achievement achievement, MultipartFile file) {
        // 1) Сохраняем файл, если он есть
        if (file != null && !file.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            try {
                Files.createDirectories(storageDir);
                Path target = storageDir.resolve(filename);
                file.transferTo(target);
                achievement.setFileUrl("/uploads/achievements/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }
        // 2) Проставляем дату, если не задана
        if (achievement.getDate() == null) {
            achievement.setDate(LocalDate.now());
        }
        // 3) Подтягиваем пользователя по ID (achievement.getStudent().getId())
        Long userId = achievement.getStudent().getId();
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        achievement.setStudent(student);

        // 4) Сохраняем
        return achievementRepository.save(achievement);
    }

    @Override
    public Achievement updateAchievement(Long id, Achievement updated, MultipartFile file) {
        Achievement existing = achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found: " + id));

        // Обновляем поля
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());
        existing.setDate(updated.getDate());
        existing.setTags(updated.getTags());

        // Обновляем файл, если пришёл новый
        if (file != null && !file.isEmpty()) {
            // (по аналогии с create — сохраняем и ставим existing.setFileUrl(...))
        }

        return achievementRepository.save(existing);
    }

    @Override
    public void deleteAchievement(Long id) {
        if (!achievementRepository.existsById(id)) {
            throw new EntityNotFoundException("Achievement not found: " + id);
        }
        achievementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Achievement getById(Long id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Achievement> getAll() {
        return achievementRepository.findAll();
    }
}
