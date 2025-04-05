package com.example.qarshi_sovgalar.payload.res;

import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResProductDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private int count;

    private Double rating;

    private List<Long> images;

    private List<FeedbackDTO> feedbacks;
}
