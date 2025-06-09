package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Comment;
import com.example.student_portfolio.model.Rating;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // средний рейтинг всех достижений студента
    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.achievement.student.id = :studentId")
    Double findAverageByStudentId(@Param("studentId") Long studentId);
}