package com.example.qarshi_sovgalar.service;

import com.example.qarshi_sovgalar.entity.Favourite;
import com.example.qarshi_sovgalar.entity.Product;
import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.FavouriteDTO;
import com.example.qarshi_sovgalar.payload.ResponseError;
import com.example.qarshi_sovgalar.repository.FavouriteRepository;
import com.example.qarshi_sovgalar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final ProductRepository productRepository;

    public ApiResponse saveFavourite(Long productId, User user){
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        Favourite favourite = Favourite.builder()
                .createdBy(user)
                .product(product)
                .createdAt(LocalDateTime.now())
                .build();
        favouriteRepository.save(favourite);

        return new ApiResponse("Successfully saved Favourite");
    }


    public ApiResponse getFavouriteByUserId(Long userId){
        List<FavouriteDTO> favouriteDTOS = favouriteRepository.findAllByCreatedById(userId).stream()
                .map(this::favouriteDTO).toList();

        return new ApiResponse(favouriteDTOS);
    }


    public ApiResponse deleteFavourite(Long productId, User user){
        Favourite byCreatedByIdAndProductId = favouriteRepository.findByCreatedByIdAndProductId(user.getId(), productId);
        if(byCreatedByIdAndProductId == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        favouriteRepository.delete(byCreatedByIdAndProductId);
        return new ApiResponse("Successfully deleted Favourite");
    }


    public FavouriteDTO favouriteDTO(Favourite favourite){
        return FavouriteDTO.builder()
                .id(favourite.getId())
                .userId(favourite.getCreatedBy().getId())
                .productId(favourite.getProduct().getId())
                .createdAt(favourite.getCreatedAt())
                .build();
    }
}
