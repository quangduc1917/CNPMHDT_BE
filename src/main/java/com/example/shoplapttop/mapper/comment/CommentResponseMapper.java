package com.example.shoplapttop.mapper.comment;

import com.example.shoplapttop.entity.CommentSection;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.comment.CommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentResponseMapper extends BaseMapper<CommentResponse, CommentSection> {
}
