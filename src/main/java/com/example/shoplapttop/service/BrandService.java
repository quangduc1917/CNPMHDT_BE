package com.example.shoplapttop.service;

import com.example.shoplapttop.model.request.brand.BrandRequest;
import com.example.shoplapttop.model.response.brand.BrandResponse;

import java.util.List;

public interface BrandService {
    void insertBrand(BrandRequest brandRequest);

    void updateBrand(long brandId,BrandRequest brandRequest);

    List<BrandResponse> getAllBrand();

}
