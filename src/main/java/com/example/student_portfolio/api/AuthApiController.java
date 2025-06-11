package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Role;
import com.example.student_portfolio.model.User;
import com.example.student_portfolio.model.Visibility;
import com.example.student_portfolio.payload.AuthRequest;
import com.example.student_portfolio.payload.AuthResponse;
import com.example.student_portfolio.repository.UserRepository;
import com.example.student_portfolio.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    /** Регистрация обычного студента */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AuthRequest req) {
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setVisibility(Visibility.PRIVATE);
        // сразу даём роль STUDENT
        u.setRoles(Set.of(Role.STUDENT));
        userRepo.save(u);
    }

    /** Логин, получение JWT */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        String token = jwtProvider.generateToken(req.getEmail());
        return new AuthResponse(token);
    }

    /** Только ADMIN может создать учителя */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-teacher")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTeacher(@RequestBody AuthRequest req) {
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setVisibility(Visibility.PRIVATE);
        // даём роль TEACHER
        u.setRoles(Set.of(Role.TEACHER));
        userRepo.save(u);
    }
}
