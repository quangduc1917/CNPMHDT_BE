package com.example.shoplapttop.mapper.product;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.request.product.ProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSaveMapper extends BaseMapper<Product, ProductRequest> {
}
