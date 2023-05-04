package com.example.shoplapttop.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="orderName")
    private String orderName;


    @Column(name="orderCode")
    private String code;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userOrder;


    @Column(name="orderTotal")
    private Long order_total;

    @OneToMany(mappedBy = "OrderCart")
    private List<Cart> cartOrder = new ArrayList<>();


    @CreationTimestamp
    private LocalDateTime dateCreate;

    @Column(name="order_address")
    private String orderAddress;

    @Column(name="order_infor")
    private String orderInfor;

}
