package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Role;
import com.example.student_portfolio.model.User;
import com.example.student_portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepo;

    /** GET /users/me */
    @GetMapping("/users/me")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails ud) {
        return userRepo.findByEmail(ud.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /users/me — обновление профиля (без ролей) */
    @PutMapping("/users/me")
    public ResponseEntity<User> updateProfile(
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody User upd
    ) {
        User user = userRepo.findByEmail(ud.getUsername()).orElseThrow();
        // Разрешаем менять только эти поля:
        user.setFullName(upd.getFullName());
        user.setGroupName(upd.getGroupName());
        user.setFaculty(upd.getFaculty());
        user.setAvatarUrl(upd.getAvatarUrl());
        user.setBio(upd.getBio());
        user.setVisibility(upd.getVisibility());
        // Не трогаем user.setRoles(...)
        return ResponseEntity.ok(userRepo.save(user));
    }

    /**
     * PUT /admin/users/{id}/roles
     * Только ADMIN может менять роли любого пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/users/{id}/roles")
    public ResponseEntity<User> updateUserRoles(
            @PathVariable Long id,
            @RequestBody Set<Role> roles
    ) {
        User user = userRepo.findById(id).orElseThrow();
        user.setRoles(roles);
        return ResponseEntity.ok(userRepo.save(user));
    }
}