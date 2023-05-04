package com.example.shoplapttop.mapper.product_detail;

import com.example.shoplapttop.entity.ProductDetail;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.request.product_detail.ProductDetailRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailRequestMapper extends BaseMapper<ProductDetail, ProductDetailRequest> {
}
