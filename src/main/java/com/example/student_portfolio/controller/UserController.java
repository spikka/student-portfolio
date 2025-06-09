package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.User;
import com.example.student_portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepo;

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal UserDetails ud) {
        return userRepo.findByEmail(ud.getUsername()).orElseThrow();
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal UserDetails ud,
                              @RequestBody User upd) {
        User user = userRepo.findByEmail(ud.getUsername()).orElseThrow();
        // Обновляем только разрешённые поля:
        user.setFullName(upd.getFullName());
        user.setGroupName(upd.getGroupName());
        user.setFaculty(upd.getFaculty());
        user.setAvatarUrl(upd.getAvatarUrl());
        user.setBio(upd.getBio());
        user.setVisibility(upd.getVisibility());
        return userRepo.save(user);
    }
}
