package com.example.shoplapttop.service;

import com.example.shoplapttop.model.request.review.ReviewSectionRequest;
import com.example.shoplapttop.model.response.review.ReviewResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface ReviewService {
    void insertReview(HttpServletRequest request, long productId, ReviewSectionRequest reviewSectionRequest);

    Page<ReviewResponse> getAll( int offset, int limit, long productId);
    long countReview(long productId);
}
