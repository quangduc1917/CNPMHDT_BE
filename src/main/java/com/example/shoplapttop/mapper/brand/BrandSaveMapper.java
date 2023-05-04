package com.example.shoplapttop.mapper.brand;

import com.example.shoplapttop.entity.Brand;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.request.brand.BrandRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandSaveMapper extends BaseMapper<Brand, BrandRequest> {
}
