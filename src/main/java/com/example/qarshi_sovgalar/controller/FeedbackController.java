package com.example.qarshi_sovgalar.controller;

import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import com.example.qarshi_sovgalar.security.CurrentUser;
import com.example.qarshi_sovgalar.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SeparatorUI;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Feedback saqlash hamma uchun")
    public ResponseEntity<ApiResponse> addFeedback(@RequestBody FeedbackDTO feedback,
                                                   @CurrentUser User user) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedback,user));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Barcha userlar uzi yozgan feedbacklarni kurish uchun")
    public ResponseEntity<ApiResponse> getFeedback(@CurrentUser User user) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUser(user));
    }



    @DeleteMapping("/{feedbackId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Barcha uzi qushgan feedbacklarni uchirish uchun")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Long feedbackId, @CurrentUser User user) {
        return ResponseEntity.ok(feedbackService.deleteFeedback(feedbackId, user));
    }
}
