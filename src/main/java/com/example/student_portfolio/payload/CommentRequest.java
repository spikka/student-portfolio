package com.example.student_portfolio.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Тело запроса при создании комментария.
 */
@Data
public class CommentRequest {
    @NotBlank(message = "text не может быть пустым")
    private String text;
}
