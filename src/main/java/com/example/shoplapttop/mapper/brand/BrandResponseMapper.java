package com.example.shoplapttop.mapper.brand;

import com.example.shoplapttop.entity.Brand;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.brand.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper extends BaseMapper<BrandResponse, Brand> {

}
