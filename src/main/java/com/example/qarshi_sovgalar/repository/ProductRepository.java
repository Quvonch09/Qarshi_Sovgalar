package com.example.qarshi_sovgalar.repository;

import com.example.qarshi_sovgalar.entity.Product;
import com.example.qarshi_sovgalar.payload.res.ResProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = """
        SELECT
                    p.id,
                    p.name,
                    p.description,
                    p.price,
                    p.count,
        
                    (
                        SELECT AVG(f.ball)
                        FROM feedback f
                        WHERE f.product_id = p.id
                    ) AS rating,
        
                    (
                        SELECT ARRAY_AGG(pf2.files_id)
                        FROM product_files pf2
                        WHERE pf2.product_id = p.id
                    ) AS images
        
                FROM product p
        
                WHERE
                    (:keyword IS NULL OR
                     LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                     LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
        
                    AND (:startPrice IS NULL OR p.price >= :startPrice)
                    AND (:endPrice IS NULL OR p.price <= :endPrice)
        
                ORDER BY rating DESC;
""", nativeQuery=true)
    Page<ResProducts> searchProducts(@Param("keyword") String keyword,
                                     @Param("startPrice") Double startPrice,
                                     @Param("endPrice") Double endPrice, Pageable pageable);


    @Query(value = """
        SELECT AVG(f.ball) from feedback f where f.product_id = ?1
""", nativeQuery=true)
    Double rating(Long productId);
}
