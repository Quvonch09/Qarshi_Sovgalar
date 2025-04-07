package com.example.qarshi_sovgalar.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String name;
    private String description;
    private List<String> tags;
    private double price;
    private int count;
    private List<Long> fileIds;
}
