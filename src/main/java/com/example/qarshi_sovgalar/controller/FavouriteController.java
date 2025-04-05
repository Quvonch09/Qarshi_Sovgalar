package com.example.qarshi_sovgalar.controller;

import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.security.CurrentUser;
import com.example.qarshi_sovgalar.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favourite")
@RequiredArgsConstructor
public class FavouriteController {
    private final FavouriteService favouriteService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Barcha  favourite saqlash")
    public ResponseEntity<ApiResponse> saveFavourite(@RequestParam Long productId, @CurrentUser User user){
        return ResponseEntity.ok(favouriteService.saveFavourite(productId, user));
    }



    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Barcha uziga tegishli favouritelarni kurish")
    public ResponseEntity<ApiResponse> getFavourite(@CurrentUser User user){
        return ResponseEntity.ok(favouriteService.getFavouriteByUserId(user.getId()));
    }


    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Barcha uzi saqlagan favouritelarni uchirish uchun")
    public ResponseEntity<ApiResponse> deleteFavourite(@CurrentUser User user, @RequestParam Long productId){
        return ResponseEntity.ok(favouriteService.deleteFavourite(productId, user));
    }
}
