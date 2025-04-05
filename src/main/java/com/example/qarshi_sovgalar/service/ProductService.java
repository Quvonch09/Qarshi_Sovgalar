package com.example.qarshi_sovgalar.service;

import com.example.qarshi_sovgalar.entity.File;
import com.example.qarshi_sovgalar.entity.Product;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.ProductDTO;
import com.example.qarshi_sovgalar.payload.ResponseError;
import com.example.qarshi_sovgalar.payload.res.ResPageable;
import com.example.qarshi_sovgalar.payload.res.ResProductDTO;
import com.example.qarshi_sovgalar.payload.res.ResProducts;
import com.example.qarshi_sovgalar.repository.FeedbackRepository;
import com.example.qarshi_sovgalar.repository.FileRepository;
import com.example.qarshi_sovgalar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;
    private final FeedbackRepository feedbackRepository;

    public ApiResponse saveProduct(ProductDTO productDTO){

        List<File> files = productDTO.getFileIds().stream()
                .map(fileId -> fileRepository.findById(fileId).orElse(null))
                .collect(Collectors.toList());

        if (files.isEmpty()){
            return new ApiResponse(ResponseError.NOTFOUND("File"));
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .count(productDTO.getCount())
                .files(files)
                .build();
        productRepository.save(product);

        return new ApiResponse("Successfully saved product");
    }



    public ApiResponse getAllProducts(String keyword, double startPrice, double endPrice, int page, int size){
        Page<ResProducts> products = productRepository.searchProducts(keyword, startPrice, endPrice, PageRequest.of(page, size));
        if (products.getTotalElements() == 0){
            return new ApiResponse(ResponseError.NOTFOUND("Productlar"));
        }

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(products.getTotalElements())
                .totalPage(products.getTotalPages())
                .body(products.getContent())
                .build();
        return new ApiResponse(resPageable);
    }


    public ApiResponse getOneProduct(Long productId){

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        List<Long> fileIds = product.getFiles().stream()
                .map(File::getId).toList();

        ResProductDTO resProductDTO = ResProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .count(product.getCount())
                .images(fileIds)
                .rating(productRepository.rating(product.getId()))
                .feedbacks(feedbackRepository.findAllByProduct(productId))
                .build();

        return new ApiResponse(resProductDTO);
    }
}
