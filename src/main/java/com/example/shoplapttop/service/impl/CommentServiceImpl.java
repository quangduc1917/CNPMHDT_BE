package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.CommentSection;
import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.mapper.comment.CommentRequestMapper;
import com.example.shoplapttop.mapper.comment.CommentResponeAdMapper;
import com.example.shoplapttop.mapper.comment.CommentResponseMapper;
import com.example.shoplapttop.model.request.comment.CommentSaveRequest;
import com.example.shoplapttop.model.response.comment.CommentResponse;
import com.example.shoplapttop.model.response.comment.CommentResponseAd;
import com.example.shoplapttop.repository.CommentSectionRepository;
import com.example.shoplapttop.repository.ProductRepository;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.CommentService;
import com.example.shoplapttop.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentSectionRepository commentSectionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentResponseMapper commentResponseMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentResponeAdMapper commentResponeAdMapper;


    @Override
    public void insertComment(HttpServletRequest request, long productId, CommentSaveRequest commentSaveRequest) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(() -> new UsernameNotFoundException("Don't find user by id = " + userId));

        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        CommentSection commentSection = commentRequestMapper.to(commentSaveRequest);

        commentSection.setUserComment(findUser.get());
        commentSection.setProductComment(resultProduct.get());
        commentSection.setCreateDate(LocalDateTime.now());

        commentSectionRepository.save(commentSection);
    }

    @Override
    public Page<CommentResponse> getAllComment(int offset, int limit, long productId) {
        PageRequest pageRequest = PageRequest.of(offset,limit);

        Page<CommentSection> commentSections = commentSectionRepository.findAll(new Specification<CommentSection>() {
            @Override
            public Predicate toPredicate(Root<CommentSection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                Predicate predicateProduct = criteriaBuilder.equal(root.join("productComment").get("productId"),productId);
                p = criteriaBuilder.and(p,predicateProduct);
                query.orderBy(criteriaBuilder.desc(root.get("cmtId")));
                return p;
            }
        },pageRequest);

        return commentSections.map(t->{
           CommentResponse commentResponse = commentResponseMapper.to(t);
           commentResponse.setUserName(t.getUserComment().getUserName());
           commentResponse.setImgAvatar(t.getUserComment().getImgAvatar());
           return commentResponse;
        });
    }

    @Override
    public List<CommentResponseAd> getAllComment1() {
        List<CommentSection> comment = commentSectionRepository.findAll();

        List<CommentResponseAd> commentResponseAds = comment.stream().map(t -> {
            CommentResponseAd commentResponse = new CommentResponseAd();
            commentResponse.setId(t.getCmtId());
            commentResponse.setCommentText(t.getCommentText());
            commentResponse.setUserName(t.getUserComment().getUserName());
            commentResponse.setProductName(t.getProductComment().getNameProduct());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String date =dateTimeFormatter.format(t.getCreateDate());

            commentResponse.setDate(date);
            return commentResponse;

        }).collect(Collectors.toList());


        return commentResponseAds;
    }



    @Override
    public Page<CommentResponseAd> getAllComment2(int offset, int limit, String orderName, Integer sort) {
        PageRequest pageRequest = PageRequest.of(offset,limit);

        Page<CommentSection> comment =commentSectionRepository.findAll(pageRequest);
        Page<CommentSection> comments = commentSectionRepository.findAll(new Specification<CommentSection>() {
            @Override
            public Predicate toPredicate(Root<CommentSection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!orderName.isBlank()){

                    Predicate pKeyWork=criteriaBuilder.like(root.get("commentText"),"%"+ orderName +"%");

                    p = criteriaBuilder.and(p,pKeyWork);


                }

                if ( sort != null && sort == 0){
                    query.orderBy(criteriaBuilder.asc(root.get("cmtId")));
                }
                if ( sort != null && sort == 1){
                    query.orderBy(criteriaBuilder.desc(root.get("cmtId")));
                }

                return p;
            }
        },pageRequest);


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return comments.map(t->{
            CommentResponseAd commentResponseAd = commentResponeAdMapper.to(t);

            commentResponseAd.setCommentText(t.getCommentText());
            commentResponseAd.setId(t.getCmtId());
            commentResponseAd.setProductName(t.getProductComment().getNameProduct());
            commentResponseAd.setUserName(t.getUserComment().getUserName());

            commentResponseAd.setDate(dateTimeFormatter.format(t.getCreateDate()));
            return commentResponseAd;
        });
    }

    @Override
    public void deleteComment(long userId) {

        Optional<CommentSection> comment =commentSectionRepository.findById(userId);
        commentSectionRepository.delete(comment.get());
    }
}
