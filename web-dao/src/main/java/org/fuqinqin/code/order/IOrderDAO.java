package org.fuqinqin.code.order;

import java.util.List;

public interface IOrderDAO {

    int add(OrderEntity orderEntity);

    int update(OrderEntity orderEntity);

    int delete(Long orderId);

    OrderEntity getById(Long orderId);

    List<OrderEntity> findAll();

}
