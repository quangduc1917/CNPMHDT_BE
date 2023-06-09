package com.example.shoplapttop.service;


import com.example.shoplapttop.model.response.cart.CartResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService {
    boolean insertItem(HttpServletRequest request, long productId ,int amountItem);

    boolean updateItem(HttpServletRequest request, long cartId, int amountItem);

    void deleteItem(HttpServletRequest request, long cartId);

    List<CartResponse> getAllCart(HttpServletRequest request);


    int countItem(HttpServletRequest request);

    void checkOut(long cartId);

}
