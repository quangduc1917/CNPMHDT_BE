package com.example.shoplapttop.model.request.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {
    private String orderName;
    private Long orderTotal;
    private String orderInfor;
}
