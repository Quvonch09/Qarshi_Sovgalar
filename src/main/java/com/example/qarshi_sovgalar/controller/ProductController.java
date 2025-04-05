package com.example.qarshi_sovgalar.controller;

import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.ProductDTO;
import com.example.qarshi_sovgalar.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ADMIN Productlarni saqlash uchun")
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }



    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "ADMIN/USER Productlarni search qilish uchun",
            description = "startPrice va endPrice uzum marketdagi taglarday ishlaydi")
    public ResponseEntity<ApiResponse> searchProduct(@RequestParam(required = false) String keyword,
                                                     @RequestParam(required = false) Double startPrice,
                                                     @RequestParam(required = false) Double endPrice,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(keyword, startPrice, endPrice, page, size));
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "ADMIN/USER Bitta productni kurish uchun",
            description = "Bu apida productga berilgan feedbacklarni kursa buladi")
    public ResponseEntity<ApiResponse> getOneProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getOneProduct(id));
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ADMIN Productni bittasini update qilish uchun")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ADMIN Productni delete qilish uchun")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
