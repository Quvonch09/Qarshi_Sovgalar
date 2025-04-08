package com.example.qarshi_sovgalar.controller;

import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.res.ResUser;
import com.example.qarshi_sovgalar.security.CurrentUser;
import com.example.qarshi_sovgalar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "User uzini profilini kurish")
    public ResponseEntity<ApiResponse> getMe(@CurrentUser User user){
        return ResponseEntity.ok(userService.getMe(user));
    }


    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "User uzini profilini uzgartirish")
    public ResponseEntity<ApiResponse> updateMe(@CurrentUser User user, @RequestBody ResUser resUser){
        return ResponseEntity.ok(userService.updateMe(resUser, user));
    }
}
