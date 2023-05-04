package com.example.shoplapttop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    @Column
    private String ram;

    @Column
    private String color;

    @Column
    private String partNumber;

    @Column
    private String processor;

    @Column
    private String chipSet;

    @Column
    private int numberOfSlot;

    @Column
    private String expansionSlot;

    @Column
    private String maximumCapacity;

    @Column
    private String vga;

    @Column
    private String hardDrive;

    @Column
    private String opticalDrive;

    @Column
    private String cardReader;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product productDT;
}
