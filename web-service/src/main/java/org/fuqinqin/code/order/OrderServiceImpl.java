package org.fuqinqin.code.order;

import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private IOrderDAO orderDAO;

    public OrderServiceImpl(){
        this.orderDAO = new OrderDAOImpl();
    }

    public int add(OrderEntity orderEntity) {
        return this.orderDAO.add(orderEntity);
    }

    public int update(OrderEntity orderEntity) {
        return 0;
    }

    public int delete(Long orderId) {
        return 0;
    }

    public OrderEntity getById(Long orderId) {
        return null;
    }

    public List<OrderEntity> findAll() {
        return null;
    }
}
