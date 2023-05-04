package com.example.shoplapttop.model.response.cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartResponse {
    private long productId;
    private long cartId;
    private String nameProduct;
    private String imageProduct;
    private int price;
    private int amountItem;
    private int totalPrice;
    private int state;
}
