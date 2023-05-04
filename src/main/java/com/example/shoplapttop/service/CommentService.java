package com.example.shoplapttop.service;

import com.example.shoplapttop.model.request.comment.CommentSaveRequest;
import com.example.shoplapttop.model.response.comment.CommentResponse;
import com.example.shoplapttop.model.response.comment.CommentResponseAd;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    void insertComment(HttpServletRequest request, long productId, CommentSaveRequest commentSaveRequest);

    Page<CommentResponse> getAllComment(int offset, int limit, long productId);

    List<CommentResponseAd> getAllComment1();

    Page<CommentResponseAd> getAllComment2(int offset, int limit, String orderName, Integer sort);

    void deleteComment(long id);
}
