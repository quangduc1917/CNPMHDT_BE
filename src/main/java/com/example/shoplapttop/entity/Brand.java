package com.example.shoplapttop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
