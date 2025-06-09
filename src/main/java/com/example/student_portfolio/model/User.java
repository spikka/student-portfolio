// User.java
package com.example.student_portfolio.model;

import jakarta.persistence.*;
import com.example.student_portfolio.model.Visibility;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    private String fullName;
    private String groupName;
    private String faculty;
    private String avatarUrl;
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Visibility visibility;  // PRIVATE, TEACHERS, PUBLIC

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    // Здесь мы меняем mappedBy на "author"
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    // геттеры/сеттеры…
}
