package com.example.student_portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"achievement_id", "author_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с достижением
    @ManyToOne
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    // Автор оценки
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    // Звёзды от 1 до 5
    @Column(nullable = false)
    private int stars;

    // Когда проголосовали
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- геттеры и сеттеры ---
    public Long getId() { return id; }
    public Achievement getAchievement() { return achievement; }
    public void setAchievement(Achievement achievement) { this.achievement = achievement; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    // createdAt можно оставлять без сеттера, если не нужно менять вручную
}
