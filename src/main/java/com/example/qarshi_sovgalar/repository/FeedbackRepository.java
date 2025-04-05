package com.example.qarshi_sovgalar.repository;

import com.example.qarshi_sovgalar.entity.Feedback;
import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = """
    select f.id, f.message, f.ball, u.full_name from feedback f join public.product_feedbacks pf on f.id = pf.feedbacks_id 
            join users u on f.created_by_id = u.id
            where pf.product_id = ?1
""", nativeQuery = true)
    List<FeedbackDTO> findAllByProduct(Long productId);
}
