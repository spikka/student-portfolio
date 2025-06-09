// Achievement.java
package com.example.student_portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="student_id", nullable=false)
    private User student;

    @Column(nullable=false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private AchievementType type;  // ACADEMIC, SPORT, SOCIAL, CREATIVE

    private LocalDate date;
    private String tags;    // comma-separated
    private String fileUrl;

    @OneToMany(mappedBy="achievement", cascade=CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy="achievement", cascade=CascadeType.ALL)
    private List<Rating> ratings;

    // геттеры/сеттеры…
}
