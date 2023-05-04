package com.example.shoplapttop.model.response.product;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductResponse {
    private Long productId;
    private String nameProduct;
    private int price;
    private int stateProduct;
    private int year;
    private String origin;
    private int amount;
    private String imageFirst;
    private String imageTwo;
    private String imageThree;
    private String imageFour;
    private String nameBrand;
    private long reviewer;
}
