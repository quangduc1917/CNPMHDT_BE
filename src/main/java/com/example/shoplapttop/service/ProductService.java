package com.example.shoplapttop.service;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.model.request.product.ProductRequest;
import com.example.shoplapttop.model.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {
    void insertProduct(ProductRequest productRequest);



    String insertImage(long productId, MultipartFile[] files);

    String insertImage1(MultipartFile[] files);

    String changeState(Long productId, Integer state);

    void updateProduct(long productId, ProductRequest productUpdateRequest);

    ProductResponse findProductById(long productId);

    Page<ProductResponse> getAllProduct(int offset, int limit, String nameProduct, String nameBrand, Integer sort, Long brandId);

    Page<ProductResponse> getAllProduct1(int offset, int limit, String nameProduct, String nameBrand, Integer sort, Long brandId);

//    Optional<Product> findById(Long productId);
}
