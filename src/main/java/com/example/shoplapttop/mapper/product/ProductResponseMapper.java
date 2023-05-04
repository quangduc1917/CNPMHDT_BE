package com.example.shoplapttop.mapper.product;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.product.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper extends BaseMapper<ProductResponse, Product> {

}
