// Comment.java
package com.example.student_portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="achievement_id", nullable=false)
    private Achievement achievement;

    @ManyToOne @JoinColumn(name="author_id", nullable=false)
    private User author;

    @Column(nullable=false)
    private String text;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // геттеры/сеттеры…
}
