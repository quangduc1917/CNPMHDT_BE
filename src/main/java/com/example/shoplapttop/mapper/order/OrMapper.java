package com.example.shoplapttop.mapper.order;

import com.example.shoplapttop.entity.Order;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.order.Or;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrMapper extends BaseMapper<Or, Order> {
}
