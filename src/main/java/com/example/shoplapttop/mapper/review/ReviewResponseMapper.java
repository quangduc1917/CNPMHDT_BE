package com.example.shoplapttop.mapper.review;

import com.example.shoplapttop.entity.ReviewSection;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.review.ReviewResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper extends BaseMapper<ReviewResponse, ReviewSection> {
}
