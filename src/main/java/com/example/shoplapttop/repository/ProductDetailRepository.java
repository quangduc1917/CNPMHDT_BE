package com.example.shoplapttop.repository;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {

    ProductDetail findByProductDT(Product product);
}
