package com.example.student_portfolio.service.impl;

import com.example.student_portfolio.model.*;
import com.example.student_portfolio.repository.*;
import com.example.student_portfolio.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepo;
    private final AchievementRepository achievementRepo;
    private final UserRepository userRepo;

    @Override
    public Rating addOrUpdateRating(Long achievementId, Long authorId, int stars) {
        Achievement ach = achievementRepo.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found: " + achievementId));
        User author = userRepo.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + authorId));

        Rating rating = ratingRepo
                .findByAchievementIdAndAuthorId(achievementId, authorId)
                .orElseGet(() -> {
                    Rating r = new Rating();
                    r.setAchievement(ach);
                    r.setAuthor(author);
                    return r;
                });

        rating.setStars(stars);
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepo.save(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public double getAverageRating(Long achievementId) {
        Double avg = ratingRepo.findAverageByAchievementId(achievementId);
        return avg != null ? avg : 0.0;
    }
}
