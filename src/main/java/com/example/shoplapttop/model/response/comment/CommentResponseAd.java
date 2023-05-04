package com.example.shoplapttop.model.response.comment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseAd {
    private Long id;
    private String commentText;
    private String userName;
    private String productName;
    private String date;
}
