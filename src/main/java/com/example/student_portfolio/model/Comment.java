// Comment.java
package com.example.student_portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
