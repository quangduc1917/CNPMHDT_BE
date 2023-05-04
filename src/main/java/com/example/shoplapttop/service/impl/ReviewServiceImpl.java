package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.ReviewSection;
import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.mapper.review.ReviewRequestMapper;
import com.example.shoplapttop.mapper.review.ReviewResponseMapper;
import com.example.shoplapttop.model.request.review.ReviewSectionRequest;
import com.example.shoplapttop.model.response.review.ReviewResponse;
import com.example.shoplapttop.repository.ProductRepository;
import com.example.shoplapttop.repository.ReviewSectionRepository;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.ReviewService;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewSectionRepository reviewSectionRepository;
    private final ReviewRequestMapper reviewRequestMapper;
    private final ReviewResponseMapper reviewResponseMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void insertReview(HttpServletRequest request, long productId, ReviewSectionRequest reviewSectionRequest) {

        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(() -> new UsernameNotFoundException("Don't find user by id = " + userId));

        ReviewSection reviewSection = reviewRequestMapper.to(reviewSectionRequest);

        reviewSection.setUserReview(findUser.get());

        Optional<Product> findProduct = productRepository.findById(productId);
        findProduct.orElseThrow(() -> new UsernameNotFoundException("Don't find product by id = " + productId));

        reviewSection.setProductReview(findProduct.get());

        reviewSectionRepository.save(reviewSection);

    }



    @Override
    public Page<ReviewResponse> getAll(int offset, int limit, long productId) {
        PageRequest pageRequest = PageRequest.of(offset,limit);
        Page<ReviewSection> reviewSections = reviewSectionRepository.findAll(new Specification<ReviewSection>(){
            @Override
            public Predicate toPredicate(Root<ReviewSection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                Predicate predicateProduct = criteriaBuilder.equal(root.join("productReview").get("productId"),productId);
                p = criteriaBuilder.and(p,predicateProduct);
                query.orderBy(criteriaBuilder.desc(root.get("reviewId")));
                return p;
            }
        },pageRequest);

        return reviewSections.map(t->{
            ReviewResponse reviewResponse = reviewResponseMapper.to(t);

            reviewResponse.setUserName(t.getUserReview().getUserName());
            reviewResponse.setStar(t.getStar());
            reviewResponse.setAvatar(t.getUserReview().getImgAvatar());

            return reviewResponse;
        });
    }
    @Override
    public long countReview(long productId) {
        long nCountReview = reviewSectionRepository.findAll().stream().filter( t -> t.getProductReview().getProductId() == productId ).count();
        return nCountReview;
    }
}
