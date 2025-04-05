package com.example.qarshi_sovgalar.repository;

import com.example.qarshi_sovgalar.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    Favourite findByCreatedByIdAndProductId(Long id, Long productId);

    List<Favourite> findAllByCreatedById(Long id);
}
