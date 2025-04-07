package com.example.qarshi_sovgalar.payload.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public interface ResProducts {
    Long getId();
    String getName();
    String getDescription();
    String getTags();
    Double getPrice();
    Integer getCount();
    Double getRating();
    List<Long> getImages();
}
