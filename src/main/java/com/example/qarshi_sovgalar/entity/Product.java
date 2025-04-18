package com.example.qarshi_sovgalar.entity;

import com.example.qarshi_sovgalar.entity.template.AbsEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ElementCollection
    private List<String> tags;

    private double price;

    private int count;

    @OneToMany(fetch = FetchType.EAGER)
    private List<File> files;

}
