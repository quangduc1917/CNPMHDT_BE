package com.example.shoplapttop.service;

import com.example.shoplapttop.model.request.product_detail.ProductDetailRequest;
import com.example.shoplapttop.model.response.product_detail.ProductDetailResponse;

public interface ProductDetailService {
    void insertProductDetail(ProductDetailRequest productDetailRequest, long productId);

    ProductDetailResponse findProductDetail(long productId);

    void updateProductDetail(long productId, ProductDetailRequest updateProductDetail);
}
