package com.example.shoplapttop.model.request.product;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductRequest {

    private String nameProduct;
    private int price;
    private int year;
    private String origin;
    private int amount;
    private long brandId;

}
