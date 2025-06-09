package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Comment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAchievementIdOrderByCreatedAtAsc(Long achievementId);

    // последние 5 комментариев по всем достижениям студента, в порядке убывания
    List<Comment> findTop5ByAchievementStudentIdOrderByCreatedAtDesc(Long studentId);
}
