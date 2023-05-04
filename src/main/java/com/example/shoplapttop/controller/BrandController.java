package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.brand.BrandRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.brand.BrandResponse;
import com.example.shoplapttop.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/api/brand/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBrand(@RequestBody BrandRequest brandRequest){
        brandService.insertBrand(brandRequest);
        return new ResponseEntity(new ApiResponse(true,"Insert success"), HttpStatus.OK);
    }

    @PutMapping("/api/brand/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@PathVariable long id ,@RequestBody BrandRequest brandRequest){
        brandService.updateBrand(id,brandRequest);
        return new ResponseEntity(new ApiResponse(true,"Update success"), HttpStatus.OK);
    }

    @GetMapping("/api/public/brand/all")
    public List<BrandResponse> getAllBrand(){
        return brandService.getAllBrand();
    }

}
