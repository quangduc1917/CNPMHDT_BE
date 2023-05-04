package com.example.shoplapttop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
public class CommentSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmtId;

    @Column
    private String commentText;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime createDate;



    @ManyToOne
    @JoinColumn(name = "userId")
    private User userComment;


    @ManyToOne
    @JoinColumn(name = "productId")
    private Product productComment;
}
