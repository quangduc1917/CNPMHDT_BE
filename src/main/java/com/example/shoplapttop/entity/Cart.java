package com.example.shoplapttop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column
    private int cartAmount;

    @Column
    private int state;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userCart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product productCart;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order OrderCart;

}
