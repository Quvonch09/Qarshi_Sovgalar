package com.example.qarshi_sovgalar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private int ball; // 1-5 gacha ball kitobga

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Product product;

}
