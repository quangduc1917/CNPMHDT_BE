package com.example.shoplapttop.model.response.order;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponse {
    private long id;
    private String orderName;
    private long order_total;
    private String userName;
    private String date;
    private String codeOrder;
    private String address;
    private String orderInfor;

}
