package com.example.shoplapttop.mapper.review;

import com.example.shoplapttop.entity.ReviewSection;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.request.review.ReviewSectionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper extends BaseMapper<ReviewSection, ReviewSectionRequest> {
}
