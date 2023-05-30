package com.example.shoplapttop.service;

import com.example.shoplapttop.entity.Order;
import com.example.shoplapttop.model.response.order.OrderResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    void insertOrder(HttpServletRequest request, String orderName,Long orderTotal,String orderInfor);

    List<OrderResponse> getAllOrder();
    Page<OrderResponse> getAllOrder1(int offset, int limit, String orderName,  Integer sort);


    List<Object[]> find(String FromDate,String ToDate);
    List<Object[]> find1();
    void deleteOrder(long id);
    List<OrderResponse> getOrder(HttpServletRequest request);



}
