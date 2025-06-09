package com.example.student_portfolio.repository;

import com.example.student_portfolio.model.Rating;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("""
      SELECT AVG(r.stars) 
      FROM Rating r 
      WHERE r.achievement.student.id = :studentId
    """)
    Double findAverageByStudentId(@Param("studentId") Long studentId);

    Optional<Rating> findByAchievementIdAndAuthorId(Long achievementId, Long authorId);

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.achievement.id = :achievementId")
    Double findAverageByAchievementId(@Param("achievementId") Long achievementId);
}
