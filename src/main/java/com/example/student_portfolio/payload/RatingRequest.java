package com.example.student_portfolio.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
    @NotNull(message = "stars обязателен")
    @Min(value = 1, message = "минимум 1 звезда")
    @Max(value = 5, message = "максимум 5 звезд")
    private Integer stars;
}
