package com.example.qarshi_sovgalar.service;

import com.example.qarshi_sovgalar.entity.Feedback;
import com.example.qarshi_sovgalar.entity.Product;
import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import com.example.qarshi_sovgalar.payload.ResponseError;
import com.example.qarshi_sovgalar.repository.FeedbackRepository;
import com.example.qarshi_sovgalar.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;


    public ApiResponse saveFeedback(FeedbackDTO feedbackDTO, User user){

        Product product = productRepository.findById(feedbackDTO.getProductId()).orElse(null);
        if(product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        Feedback feedback = Feedback.builder()
                .message(feedbackDTO.getMessage())
                .ball(feedbackDTO.getBall())
                .createdBy(user)
                .product(product)
                .build();
        feedbackRepository.save(feedback);
        return new ApiResponse("Successfully saved feedback");
    }


    public ApiResponse getFeedbackByUser(User user){
        List<FeedbackDTO> feedbackDTOS = feedbackRepository.findAllByUser(user.getId()).stream()
                .map(this::getFeedbackDTO).toList();
        return new ApiResponse(feedbackDTOS);
    }


    public ApiResponse deleteFeedback(Long feedbackId, User user){

        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
        if(feedback == null){
            return new ApiResponse(ResponseError.NOTFOUND("Feedback"));
        }

        if (!feedback.getCreatedBy().equals(user)) {
            return new ApiResponse(ResponseError.ACCESS_DENIED());
        }

        feedbackRepository.delete(feedback);
        return new ApiResponse("Successfully deleted");
    }


    public FeedbackDTO getFeedbackDTO(Feedback feedback){
         return FeedbackDTO.builder()
                .id(feedback.getId())
                .ball(feedback.getBall())
                .message(feedback.getMessage())
                .ownerName(feedback.getCreatedBy().getFullName())
                .productId(feedback.getProduct().getId())
                .build();
    }
}
