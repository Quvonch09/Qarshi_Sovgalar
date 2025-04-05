package com.example.qarshi_sovgalar.entity;

import com.example.qarshi_sovgalar.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product extends AbsEntity {

    private String name;

    private String description;

    private double price;

    private int count;

    @OneToMany
    private List<File> files;

    @OneToMany
    private List<Feedback> feedbacks;
}
