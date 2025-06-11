package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Comment;
import com.example.student_portfolio.payload.CommentRequest;
import com.example.student_portfolio.repository.UserRepository;
import com.example.student_portfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/achievements/{id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepo;

    /**
     * POST /achievements/{id}/comments
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(
            @PathVariable("id") Long achievementId,
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody @Validated CommentRequest req
    ) {
        // получаем ID текущего пользователя из UserDetails
        Long authorId = userRepo.findByEmail(ud.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"
                ))
                .getId();

        Comment saved = commentService.addComment(achievementId, authorId, req.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /achievements/{id}/comments
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable("id") Long achievementId
    ) {
        List<Comment> list = commentService.getCommentsForAchievement(achievementId);
        return ResponseEntity.ok(list);
    }
}
