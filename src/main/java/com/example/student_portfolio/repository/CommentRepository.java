package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Comment;
import com.example.student_portfolio.model.Rating;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // последние 5 комментариев ко всем достижениям студента
    List<Comment> findTop5ByAchievementStudentIdOrderByCreatedAtDesc(Long studentId);
}
