package com.example.shoplapttop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ReviewSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column
    private int star;

    @Column
    private String contentReview;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userReview;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product productReview;


}
