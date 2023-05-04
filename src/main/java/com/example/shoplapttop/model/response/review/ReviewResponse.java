package com.example.shoplapttop.model.response.review;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReviewResponse {
    private int star;
    private String contentReview;
    private String userName;
    private String avatar;
}
