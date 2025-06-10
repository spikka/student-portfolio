// User.java
package com.example.student_portfolio.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(Long id) {
        this.id = id;
    }

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    // при регистрации по умолчанию:
    public void addDefaultRole() {
        this.roles.add(Role.STUDENT);
    }
}
