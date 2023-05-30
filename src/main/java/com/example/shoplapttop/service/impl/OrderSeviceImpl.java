package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.CommentSection;
import com.example.shoplapttop.entity.Order;
import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.mapper.order.OrderResponseMapper;
import com.example.shoplapttop.model.response.order.OrderResponse;
import com.example.shoplapttop.repository.OrderRepository;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.OrderService;
import com.example.shoplapttop.utils.EmailServiceImpl;
import com.example.shoplapttop.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderSeviceImpl implements OrderService {


    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrderRepository orderRepository;

    private final OrderResponseMapper orderResponseMapper;
    private final EmailServiceImpl emailService;


    @Override
    public void insertOrder(HttpServletRequest request, String orderName,Long orderTotal,String orderInfor) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        Optional<User> findUser = userRepository.findById(userId);

        LocalDateTime dateTime = LocalDateTime.now();
        Order order = new Order();

        order.setOrder_total(orderTotal);
        order.setUserOrder(findUser.get());
        order.setOrderName(orderName);
        order.setDateCreate(dateTime);
        order.setOrderAddress(findUser.get().getAddress());
        order.setOrderInfor(orderInfor);


        emailService.sendSimpleEmail(findUser.get().getEmail(), "Đơn đặt hàng",orderName);

        orderRepository.save(order);

    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();

        List<OrderResponse> orderResponses = orders.stream().map(t->{
            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setId(t.getId());
            orderResponse.setOrderName(t.getOrderName());
            orderResponse.setOrder_total(t.getOrder_total());
            orderResponse.setUserName(t.getUserOrder().getUserName());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String date =dateTimeFormatter.format(t.getDateCreate());
            orderResponse.setDate(date);
            orderResponse.setAddress(t.getOrderAddress());
            orderResponse.setOrderInfor(t.getOrderInfor());
            return orderResponse;

        }).collect(Collectors.toList());


    return orderResponses;

    }

    @Override
    public Page<OrderResponse> getAllOrder1(int offset, int limit, String orderName, Integer sort) {
        PageRequest pageRequest = PageRequest.of(offset,limit);

        Page<Order> order =orderRepository.findAll(pageRequest);
        Page<Order> orders = orderRepository.findAll(new Specification<Order>() {
            @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate p = criteriaBuilder.conjunction();
                if (!orderName.isBlank()){

                    Predicate pKeyWork=criteriaBuilder.like(root.get("orderName"),"%"+ orderName +"%");

                    p = criteriaBuilder.and(p,pKeyWork);


                }

                if ( sort != null && sort == 0){
                    query.orderBy(criteriaBuilder.asc(root.get("order_total")));
                }
                if ( sort != null && sort == 1){
                    query.orderBy(criteriaBuilder.desc(root.get("order_total")));
                }

                return p;
            }
        },pageRequest);


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return orders.map(t->{
            OrderResponse orderResponse = orderResponseMapper.to(t);
            orderResponse.setOrderName(t.getOrderName());
            orderResponse.setUserName(t.getUserOrder().getUserName());
            orderResponse.setOrder_total(t.getOrder_total());
            orderResponse.setAddress(t.getOrderAddress());
            orderResponse.setDate(dateTimeFormatter.format(t.getDateCreate()));
            orderResponse.setOrderInfor(t.getOrderInfor());
            return orderResponse;
        });
    }

    @Override
    public List<Object[]> find(String FromDate,String ToDate) {



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate1 = LocalDate.parse(FromDate, formatter);
        LocalDate localDate2 = LocalDate.parse(ToDate, formatter);
        return orderRepository.find(localDate1,localDate2);


    }


    @Override
    public List<Object[]> find1() {
        return orderRepository.find1();

    }
    public void deleteOrder(long userId) {

        Optional<Order> order =orderRepository.findById(userId);
        orderRepository.delete(order.get());
//        Optional<CommentSection> comment =commentSectionRepository.findById(userId);
//        commentSectionRepository.delete(comment.get());
    }

    @Override
    public List<OrderResponse> getOrder(HttpServletRequest request) {

        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);
        System.out.println("ten la"+findUser.get().getUserName()+" "+findUser.get().getEmail());


        List<Order> orders = orderRepository.findAllByUserOrder(findUser.get());

        System.out.println("sl la "+orders.size());
        List<OrderResponse> orderResponses = orders.stream().map(t->{
            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setId(t.getId());
            orderResponse.setOrderName(t.getOrderName());
            orderResponse.setOrder_total(t.getOrder_total());
            orderResponse.setUserName(t.getUserOrder().getUserName());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String date =dateTimeFormatter.format(t.getDateCreate());
            orderResponse.setDate(date);
            orderResponse.setAddress(t.getOrderAddress());
            orderResponse.setOrderInfor(t.getOrderInfor());
            return orderResponse;

        }).collect(Collectors.toList());


        return orderResponses;

    }

}
