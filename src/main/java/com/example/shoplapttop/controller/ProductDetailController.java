package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.product_detail.ProductDetailRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.product_detail.ProductDetailResponse;
import com.example.shoplapttop.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @PostMapping("/api/productDetail/insert/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertProduct(@RequestBody ProductDetailRequest productDetailRequest, @PathVariable long productId){
        productDetailService.insertProductDetail(productDetailRequest,productId);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }

    @GetMapping("/api/public/productDetail/find/{id}")
    public ResponseEntity<ProductDetailResponse> findProductUseId(@PathVariable long id){
        return new ResponseEntity<>(productDetailService.findProductDetail(id),HttpStatus.OK);
    }

    @PutMapping("/api/productDetail/update/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDetailRequest productDetailRequest, @PathVariable long productId ){
        productDetailService.updateProductDetail(productId,productDetailRequest);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }



}
