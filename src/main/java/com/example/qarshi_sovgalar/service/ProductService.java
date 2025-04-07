package com.example.qarshi_sovgalar.service;

import com.example.qarshi_sovgalar.entity.File;
import com.example.qarshi_sovgalar.entity.Product;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.FeedbackDTO;
import com.example.qarshi_sovgalar.payload.ProductDTO;
import com.example.qarshi_sovgalar.payload.ResponseError;
import com.example.qarshi_sovgalar.payload.res.ResProductDTO;
import com.example.qarshi_sovgalar.payload.res.ResProducts;
import com.example.qarshi_sovgalar.repository.FeedbackRepository;
import com.example.qarshi_sovgalar.repository.FileRepository;
import com.example.qarshi_sovgalar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackService feedbackService;

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
                .tags(productDTO.getTags())
                .description(productDTO.getDescription())
                .count(productDTO.getCount())
                .files(files)
                .build();
        productRepository.save(product);

        return new ApiResponse("Successfully saved product");
    }



    public ApiResponse getAllProducts(String keyword, Double startPrice, Double endPrice){
        List<ResProducts> products = productRepository.searchProducts(keyword, startPrice, endPrice);
        if (products.isEmpty()){
            return new ApiResponse(ResponseError.NOTFOUND("Productlar"));
        }

        return new ApiResponse(products);
    }


    public ApiResponse getOneProduct(Long productId){

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        List<Long> fileIds = product.getFiles().stream()
                .map(File::getId).toList();

        List<FeedbackDTO> feedbackDTOS = feedbackRepository.findAllByProduct(productId).stream()
                .map(feedbackService::getFeedbackDTO).toList();

        ResProductDTO resProductDTO = ResProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .count(product.getCount())
                .images(fileIds)
                .rating(productRepository.rating(product.getId()))
                .feedbacks(feedbackDTOS)
                .build();

        return new ApiResponse(resProductDTO);
    }


    public ApiResponse updateProduct(Long productId, ProductDTO productDTO){
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        List<File> files = productDTO.getFileIds().stream()
                .map(fileId -> fileRepository.findById(fileId).orElse(null)).toList();

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCount(productDTO.getCount());
        product.setTags(productDTO.getTags());
        product.setFiles(files);
        productRepository.save(product);
        return new ApiResponse("Successfully updated product");
    }


    public ApiResponse deleteProduct(Long productId){

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return new ApiResponse(ResponseError.NOTFOUND("Product"));
        }

        productRepository.delete(product);
        return new ApiResponse("Successfully deleted product");
    }
}
