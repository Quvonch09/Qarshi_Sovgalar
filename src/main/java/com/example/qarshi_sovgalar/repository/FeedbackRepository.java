package com.example.qarshi_sovgalar.repository;

import com.example.qarshi_sovgalar.entity.Feedback;
import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = """
    select f.* from feedback f
            join users u on f.created_by_id = u.id
            where f.product_id = ?1
""", nativeQuery = true)
    List<Feedback> findAllByProduct(Long productId);

    @Query(value = "insert into product_feedbacks(product_id, feedbacks_id) values(?1,?2)", nativeQuery = true)
    Feedback saveFeedback(Long productId, Long feedbackId);

    @Query(value = """
    select f.* from feedback f
            join users u on f.created_by_id = u.id
            where f.created_by_id = ?1
""", nativeQuery = true)
    List<Feedback> findAllByUser(Long userId);
}
