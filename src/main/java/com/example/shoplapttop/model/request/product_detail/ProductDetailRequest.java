package com.example.shoplapttop.model.request.product_detail;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDetailRequest {
    private String ram;
    private String color;
    private String partNumber;
    private String processor;
    private String chipSet;
    private int numberOfSlot;
    private String expansionSlot;
    private String maximumCapacity;
    private String vga;
    private String hardDrive;
    private String opticalDrive;
    private String cardReader;
    private String content;
}
