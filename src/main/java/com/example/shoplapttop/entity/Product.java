package com.example.shoplapttop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column
    private String nameProduct;

    @Column
    private int price;

    @Column
    private int stateProduct;

    @Column
    private int year;

    @Column
    private String origin;

    @Column
    private int amount;

    @Column
    private String imageFirst;

    @Column
    private String imageTwo;

    @Column
    private String imageThree;

    @Column
    private String imageFour;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "productDT")
    private List<ProductDetail> productDetails;


    @OneToMany(mappedBy = "productComment")
    private List<CommentSection> productComments = new ArrayList<>();

    @OneToMany(mappedBy = "productReview")
    private List<ReviewSection> reviewSections = new ArrayList<>();

    @OneToMany(mappedBy = "productCart")
    private List<Cart> carts;
    
}
