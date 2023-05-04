package com.example.shoplapttop.repository;

import com.example.shoplapttop.entity.Cart;
import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserCartAndProductCart(User user, Product product);

    List<Cart> findAllByUserCart(User user);

    Cart findByProductCart(Product product);
}
