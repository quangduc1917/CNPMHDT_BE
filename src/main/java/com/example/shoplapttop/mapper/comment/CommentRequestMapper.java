package com.example.shoplapttop.mapper.comment;

import com.example.shoplapttop.entity.CommentSection;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.request.comment.CommentSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper extends BaseMapper<CommentSection, CommentSaveRequest> {
}
