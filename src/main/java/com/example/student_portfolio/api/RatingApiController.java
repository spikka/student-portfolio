package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Rating;
import com.example.student_portfolio.payload.RatingRequest;
import com.example.student_portfolio.repository.UserRepository;
import com.example.student_portfolio.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/achievements/{id}/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final UserRepository userRepo;

    /**
     * POST /achievements/{id}/ratings
     */
    @PostMapping
    public ResponseEntity<Rating> rateAchievement(
            @PathVariable("id") Long achievementId,
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody @Validated RatingRequest req
    ) {
        Long authorId = userRepo.findByEmail(ud.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"
                ))
                .getId();

        Rating saved = ratingService.addOrUpdateRating(achievementId, authorId, req.getStars());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /achievements/{id}/ratings/average
     */
    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverage(
            @PathVariable("id") Long achievementId
    ) {
        double avg = ratingService.getAverageRating(achievementId);
        return ResponseEntity.ok(Map.of("averageStars", avg));
    }
}
