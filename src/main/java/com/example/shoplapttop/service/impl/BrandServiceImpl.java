package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.Brand;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.mapper.brand.BrandResponseMapper;
import com.example.shoplapttop.mapper.brand.BrandSaveMapper;
import com.example.shoplapttop.model.request.brand.BrandRequest;
import com.example.shoplapttop.model.response.brand.BrandResponse;
import com.example.shoplapttop.repository.BrandRepository;
import com.example.shoplapttop.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandSaveMapper brandSaveMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void insertBrand(BrandRequest brandRequest) {
        Brand brand = brandSaveMapper.to(brandRequest);
        brandRepository.save(brand);

    }

    @Override
    public void updateBrand(long brandId, BrandRequest brandRequest) {
        Optional<Brand> findBrand = brandRepository.findById(brandId);
        findBrand.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID",brandId));

        Brand brand = findBrand.get();
        brand.setBrandName(brandRequest.getBrandName());

        brandRepository.save(brand);
    }

    @Override
    public List<BrandResponse> getAllBrand() {
        List<Brand> brands = brandRepository.findAll();
        return brandResponseMapper.to(brands);
    }


}
