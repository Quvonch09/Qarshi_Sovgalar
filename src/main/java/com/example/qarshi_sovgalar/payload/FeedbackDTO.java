package com.example.qarshi_sovgalar.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDTO {

    @Schema(hidden = true)
    private Long id;

    private String message;

    private int ball;

    @Schema(hidden = true)
    private String ownerName;
}
