package com.example.shoplapttop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String userName;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String numberPhone;

    @Column
    private String address;

    @Column
    private Integer status;

    @Column
    private String imgAvatar;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permission",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userCart")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "userComment")
    private List<CommentSection> commentSections = new ArrayList<>();

    @OneToMany(mappedBy = "userReview")
    private List<ReviewSection> reviewSections = new ArrayList<>();

    public User() {
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


}
