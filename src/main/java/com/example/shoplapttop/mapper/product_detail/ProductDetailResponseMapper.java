package com.example.shoplapttop.mapper.product_detail;

import com.example.shoplapttop.entity.ProductDetail;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.product_detail.ProductDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailResponseMapper extends BaseMapper<ProductDetailResponse, ProductDetail> {
}
