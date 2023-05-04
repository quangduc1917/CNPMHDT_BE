package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.ProductDetail;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.mapper.product_detail.ProductDetailRequestMapper;
import com.example.shoplapttop.mapper.product_detail.ProductDetailResponseMapper;
import com.example.shoplapttop.model.request.product_detail.ProductDetailRequest;
import com.example.shoplapttop.model.response.product_detail.ProductDetailResponse;
import com.example.shoplapttop.repository.ProductDetailRepository;
import com.example.shoplapttop.repository.ProductRepository;
import com.example.shoplapttop.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailResponseMapper productDetailResponseMapper;
    private final ProductDetailRequestMapper productDetailRequestMapper;


    @Override
    public void insertProductDetail(ProductDetailRequest productDetailRequest, long productId) {
        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        ProductDetail productDetail = productDetailRequestMapper.to(productDetailRequest);

        productDetail.setProductDT(resultProduct.get());

        productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetailResponse findProductDetail(long productId) {
        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        ProductDetail productDetail = productDetailRepository.findByProductDT(resultProduct.get());

        return productDetailResponseMapper.to(productDetail);
    }

    @Override
    public void updateProductDetail(long productId, ProductDetailRequest updateProductDetail) {
        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        ProductDetail productDetail = productDetailRepository.findByProductDT(resultProduct.get());

        productDetail.setCardReader(updateProductDetail.getCardReader());
        productDetail.setChipSet(updateProductDetail.getChipSet());
        productDetail.setColor(updateProductDetail.getColor());
        productDetail.setContent(updateProductDetail.getContent());
        productDetail.setExpansionSlot(updateProductDetail.getExpansionSlot());
        productDetail.setHardDrive(updateProductDetail.getHardDrive());
        productDetail.setMaximumCapacity(updateProductDetail.getMaximumCapacity());
        productDetail.setNumberOfSlot(updateProductDetail.getNumberOfSlot());
        productDetail.setOpticalDrive(updateProductDetail.getOpticalDrive());
        productDetail.setProcessor(updateProductDetail.getProcessor());
        productDetail.setPartNumber(updateProductDetail.getPartNumber());
        productDetail.setRam(updateProductDetail.getRam());
        productDetail.setVga(updateProductDetail.getVga());

        productDetailRepository.save(productDetail);
    }


}
