package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    // при необходимости можно добавить методы поиска, например:
    // List<Achievement> findByType(AchievementType type);
    List<Achievement> findAllByStudentId(Long studentId);
}
