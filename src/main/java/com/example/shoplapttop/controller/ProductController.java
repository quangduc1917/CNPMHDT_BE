package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.product.ProductRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.product.ProductResponse;
import com.example.shoplapttop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest){
        productService.insertProduct(productRequest);
        return new ResponseEntity(new ApiResponse(true,"Insert success"), HttpStatus.OK);
    }

    @PutMapping(value = "/api/product/uploadImage/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(@PathVariable long productId,@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity(new ApiResponse(true, productService.insertImage(productId, files)),HttpStatus.OK);
    }

    @PutMapping(value = "/api/product/uploadImage1")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile1(@RequestParam("files") MultipartFile[] files) {

        return new ResponseEntity(new ApiResponse(true, productService.insertImage1(files)),HttpStatus.OK);
    }
    @PutMapping("/api/product/changeState")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeState(@RequestParam Long productId,@RequestParam Integer state) {
        return new ResponseEntity(new ApiResponse(true, productService.changeState(productId, state)),HttpStatus.OK);
    }


    @PutMapping("/api/product/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long id){
        productService.updateProduct(id,productRequest);
        return new ResponseEntity(new ApiResponse(true,"Insert success"), HttpStatus.OK);
    }

    @GetMapping("/api/public/product/find")
    public ResponseEntity<List<ProductResponse>> getProductById(@RequestParam long productId){
        return new ResponseEntity(productService.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/api/public/product/all")
    ResponseEntity<Page<ProductResponse>> getAllUser( @RequestParam(required = false) int offset,@RequestParam(required = false) int limit,
                                                      @RequestParam(required = false) String keyWork, @RequestParam(required = false) Integer sort,
                                                      @RequestParam(required = false) Long brandId){
        return new ResponseEntity(productService.getAllProduct(offset,limit,keyWork,keyWork,sort,brandId),HttpStatus.OK);
    }


    @GetMapping("/api/public/product/allnew")
    ResponseEntity<Page<ProductResponse>> getAllProductNew( @RequestParam(required = false) int offset,@RequestParam(required = false) int limit,
                                                      @RequestParam(required = false) String keyWork, @RequestParam(required = false) Integer sort,
                                                      @RequestParam(required = false) Long brandId){
        return new ResponseEntity(productService.getAllProduct1(offset,limit,keyWork,keyWork,1,brandId),HttpStatus.OK);
    }










}
