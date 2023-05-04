package com.example.shoplapttop.mapper.comment;

import com.example.shoplapttop.entity.CommentSection;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.comment.CommentResponseAd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentResponeAdMapper extends BaseMapper<CommentResponseAd, CommentSection> {
}
