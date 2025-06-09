package com.example.student_portfolio.payload;

import com.example.student_portfolio.model.Achievement;
import lombok.Data;

import java.util.List;

@Data
public class CommentDto {
    private Long id;
    private Long achievementId;
    private Long authorId;
    private String text;
    private java.time.LocalDateTime createdAt;
}