package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.Brand;
import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.mapper.product.ProductResponseMapper;
import com.example.shoplapttop.mapper.product.ProductSaveMapper;
import com.example.shoplapttop.model.request.product.ProductRequest;
import com.example.shoplapttop.model.response.product.ProductResponse;
import com.example.shoplapttop.repository.BrandRepository;
import com.example.shoplapttop.repository.ProductRepository;
import com.example.shoplapttop.service.FileStorageService;
import com.example.shoplapttop.service.ProductService;
import com.example.shoplapttop.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductSaveMapper productSaveMapper;
    private final BrandRepository brandRepository;
    private FileStorageService fileStorageService;
    private final ProductResponseMapper productResponseMapper;
    private final ReviewService reviewService;

    @Override
    public void insertProduct(ProductRequest productRequest) {
        Product product = productSaveMapper.to(productRequest);
        product.setStateProduct(1);
        Brand brand = brandRepository.getById(productRequest.getBrandId());
        product.setBrand(brand);
        productRepository.save(product);
    }

    @Override
    public String insertImage(long productId, MultipartFile[] files) {
        String result = "";
        if (files.length > 4) {
            result = "FAILED";
        }else{
            Optional<Product> findProduct = productRepository.findById(productId);
            findProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));
            Product product  = findProduct.get();
            product.setImageFirst(fileStorageService.storeFile(files[0]));
            product.setImageTwo(fileStorageService.storeFile(files[1]));
            product.setImageThree(fileStorageService.storeFile(files[2]));
            product.setImageFour(fileStorageService.storeFile(files[3]));
            productRepository.save(product);
            result = "SUCCESS";
        }
        return result;
    }

    @Override
    public String insertImage1(MultipartFile[] files) {
        String result = "";

        if (files.length > 4) {
            result = "FAILED";
        }else{
            List<Product> l1= productRepository.findAll();
            Product product=l1.get(l1.size()-1);
            long productId = product.getProductId();
            System.out.println("id"+productId);
//            Optional<Product> findProduct = productRepository.findById(productId);
//            findProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));
//            Product product  = findProduct.get();
            product.setImageFirst(fileStorageService.storeFile(files[0]));
            product.setImageTwo(fileStorageService.storeFile(files[1]));
            product.setImageThree(fileStorageService.storeFile(files[2]));
            product.setImageFour(fileStorageService.storeFile(files[3]));
            productRepository.save(product);

            result = "SUCCESS";
        }
        return result;
    }




    @Override
    public String changeState(Long productId, Integer state) {
        if(productId != null){
            Optional<Product> resultProduct = productRepository.findById(productId);
            resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));
            Product product = resultProduct.get();
            if (state == 1 && state != null){
                product.setStateProduct(state);
                productRepository.save(product);
                return "STILL";
            }else if (state == 0  && state != null){
                product.setStateProduct(state);
                productRepository.save(product);
                return "END";
            }else{
                product.setStateProduct(-1);
                productRepository.save(product);
                return "DELETED";
            }


        }
        return null;
    }

    @Override
    public void updateProduct(long productId, ProductRequest productUpdateRequest) {

        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        Product product = resultProduct.get();
        product.setNameProduct(productUpdateRequest.getNameProduct());
        product.setPrice(productUpdateRequest.getPrice());
        product.setYear(productUpdateRequest.getYear());
        product.setOrigin(productUpdateRequest.getOrigin());
        product.setAmount(productUpdateRequest.getAmount());
        Brand brand = brandRepository.getById(productUpdateRequest.getBrandId());
        product.setBrand(brand);

        productRepository.save(product);

    }

    @Override
    public ProductResponse findProductById(long productId) {
        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));
        ProductResponse productResponse = productResponseMapper.to(resultProduct.get());
        productResponse.setNameBrand(resultProduct.get().getBrand().getBrandName());
        return productResponse;
    }

    @Override
    public Page<ProductResponse> getAllProduct(int offset, int limit, String nameProduct, String nameBrand, Integer sort, Long brandId) {
        PageRequest pageRequest = PageRequest.of(offset,limit);

        Page<Product> products = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!nameProduct.isBlank() && !nameBrand.isBlank()){
                    Predicate pKeyWork = criteriaBuilder.like(root.get("nameProduct"),"%"+ nameProduct +"%");
                    Predicate pNameBrand = criteriaBuilder.like(root.join("brand").get("brandName"),"%"+nameBrand+"%");
                    Predicate predicate = criteriaBuilder.or(pKeyWork,pNameBrand);
                    p = criteriaBuilder.and(p,predicate);
                }

                if ( brandId != null ){
                    Predicate pBrand = criteriaBuilder.equal(root.join("brand").get("brandId"),brandId);
                    p = criteriaBuilder.and(p,pBrand);
                }

                if ( sort != null && sort == 0){
                    query.orderBy(criteriaBuilder.asc(root.get("price")));
                }

                if ( sort != null && sort == 1){
                    query.orderBy(criteriaBuilder.desc(root.get("price")));
                }

                return p;
            }
        },pageRequest);


        return products.map(t->{
           ProductResponse productResponse = productResponseMapper.to(t);
           productResponse.setReviewer(reviewService.countReview(t.getProductId()));
           productResponse.setNameBrand(t.getBrand().getBrandName());
           return productResponse;
        });

    }



    @Override
    public Page<ProductResponse> getAllProduct1(int offset, int limit, String nameProduct, String nameBrand, Integer sort, Long brandId) {
        PageRequest pageRequest = PageRequest.of(offset,limit);

        Page<Product> products = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!nameProduct.isBlank() && !nameBrand.isBlank()){
                    Predicate pKeyWork = criteriaBuilder.like(root.get("nameProduct"),"%"+ nameProduct +"%");
                    Predicate pNameBrand = criteriaBuilder.like(root.join("brand").get("brandName"),"%"+nameBrand+"%");
                    Predicate predicate = criteriaBuilder.or(pKeyWork,pNameBrand);
                    p = criteriaBuilder.and(p,predicate);
                }

                if ( brandId != null ){
                    Predicate pBrand = criteriaBuilder.equal(root.join("brand").get("brandId"),brandId);
                    p = criteriaBuilder.and(p,pBrand);
                }

                if ( sort != null && sort == 0){
                    query.orderBy(criteriaBuilder.asc(root.get("price")));
                }

                if ( sort != null && sort == 1){
                    query.orderBy(criteriaBuilder.desc(root.get("productId")));
                }

                return p;
            }
        },pageRequest);


        return products.map(t->{
            ProductResponse productResponse = productResponseMapper.to(t);
            productResponse.setReviewer(reviewService.countReview(t.getProductId()));
            productResponse.setNameBrand(t.getBrand().getBrandName());
            return productResponse;
        });

    }
//    @Override
//    public Optional<Product> findById(Long productId) {
//        return productRepository.findById(productId);
//    }


}
