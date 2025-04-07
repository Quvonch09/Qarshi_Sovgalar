package com.example.qarshi_sovgalar.payload.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUser {

    @Schema(hidden = true)
    private Long id;

    private String fullName;

    private String phoneNumber;

    @Schema(hidden = true)
    private String role;
}
