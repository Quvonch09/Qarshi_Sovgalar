package com.example.qarshi_sovgalar.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteDTO {
    private Long id;
    private Long productId;
    private LocalDateTime createdAt;
    private Long userId;

}
