package com.example.student_portfolio.payload;

import com.example.student_portfolio.model.AchievementType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AchievementDto {
    @NotNull(message = "studentId обязателен")
    private Long studentId;

    @NotBlank(message = "title не может быть пустым")
    @Size(max = 255, message = "title не может быть длиннее 255 символов")
    private String title;

    @Size(max = 10_000, message = "description слишком длинное")
    private String description;

    @NotNull(message = "type обязателен")
    private AchievementType type;

    @NotNull(message = "date обязателен")
    @PastOrPresent(message = "date не может быть в будущем")
    private LocalDate date;

    @Size(max = 255, message = "tags слишком длинные")
    private String tags;
}
