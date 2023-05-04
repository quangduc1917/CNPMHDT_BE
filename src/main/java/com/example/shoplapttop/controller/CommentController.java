package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.comment.CommentSaveRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.comment.CommentResponse;
import com.example.shoplapttop.model.response.comment.CommentResponseAd;
import com.example.shoplapttop.model.response.review.ReviewResponse;
import com.example.shoplapttop.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;//check

    @PostMapping("/api/comment/add/{productId}")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> commentProduct(HttpServletRequest request, @PathVariable long productId, @RequestBody CommentSaveRequest commentSaveRequest){        commentService.insertComment(request, productId, commentSaveRequest);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }

    @GetMapping("/api/public/comment/all")
    public ResponseEntity<Page<CommentResponse>> getAllComments(@RequestParam(required = false) int offset, @RequestParam(required = false) int limit, @RequestParam(required = false) long productId){        return new ResponseEntity<>(commentService.getAllComment(offset, limit, productId),HttpStatus.OK);
    }


    @GetMapping("/api/public/comment/all1")
    public List<CommentResponseAd> getAllComments(){
        return commentService.getAllComment1();
    }

    @GetMapping("/api/public/comment/all2")
    public ResponseEntity<Page<CommentResponseAd>> getAllComments1(@RequestParam(required = false) int offset, @RequestParam(required = false) int limit,
                                                                   @RequestParam(required = false) String keyWord, @RequestParam(required = false) Integer sort,
                                                                   @RequestParam(required = false) Long Id){
        System.out.println(keyWord);
        return new ResponseEntity(commentService.getAllComment2(offset,limit,keyWord,sort), HttpStatus.OK);
    }


    @DeleteMapping("/api/comment/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteComment(@PathVariable long id){
        commentService.deleteComment(id);
        System.out.println(id);
        return new ResponseEntity(new ApiResponse(true,"Delete success"),HttpStatus.OK);
    }
}
