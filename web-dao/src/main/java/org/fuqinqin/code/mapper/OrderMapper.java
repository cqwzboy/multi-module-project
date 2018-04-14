package org.fuqinqin.code.mapper;

import org.fuqinqin.code.order.OrderEntity;

import java.util.List;

public interface OrderMapper {

    public OrderEntity findById(Long orderId);

    public List<OrderEntity> findAll();

}
