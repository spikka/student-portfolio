package com.example.student_portfolio.controller;

import com.example.student_portfolio.model.Achievement;
import com.example.student_portfolio.model.User;
import com.example.student_portfolio.payload.AchievementDto;
import com.example.student_portfolio.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    /**
     * Создать новое достижение.
     * Ожидает две части формы:
     * - "data"   — JSON-представление Achievement (без id, student.id обязателен)
     * - "file"   — (опционально) файл PDF или изображение
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Achievement> createAchievement(
            @RequestPart("data") @Valid AchievementDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // 1) Проверка размера файла
        long maxSize = 5 * 1024 * 1024;
        if (file != null && file.getSize() > maxSize) {
            throw new ResponseStatusException(
                    HttpStatus.PAYLOAD_TOO_LARGE,
                    "Макс. размер файла — 5MB"
            );
        }

        // 2) Маппинг DTO → Entity
        Achievement ach = new Achievement();
        ach.setStudent(new User(dto.getStudentId())); // конструктор User(Long id)
        ach.setTitle(dto.getTitle());
        ach.setDescription(dto.getDescription());
        ach.setType(dto.getType());
        ach.setDate(dto.getDate());
        ach.setTags(dto.getTags());

        Achievement created = achievementService.createAchievement(ach, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Получить достижение по ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable Long id) {
        Achievement ach = achievementService.getById(id);
        return ResponseEntity.ok(ach);
    }

    /**
     * Обновить существующее достижение.
     * Консументы аналогичны POST: можно обновить JSON-данные и/или передать новый файл.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Achievement> updateAchievement(
            @PathVariable Long id,
            @RequestPart("data") Achievement achievement,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Achievement updated = achievementService.updateAchievement(id, achievement, file);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить достижение по ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }

    @ControllerAdvice
    public class RestExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(fe ->
                    errors.put(fe.getField(), fe.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        @ExceptionHandler(FileSizeLimitExceededException.class)
        public ResponseEntity<String> handleFileSize(FileSizeLimitExceededException ex) {
            return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(ex.getMessage());
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handlePayloadTooLarge(ResponseStatusException ex) {
        if (ex.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(ex.getReason());
        }
        throw ex;
    }


}
