package org.fuqinqin.code.order;

import org.fuqinqin.code.base.BaseDAO;

import java.util.List;

public class OrderDAOImpl extends BaseDAO implements IOrderDAO {

    public int add(OrderEntity orderEntity) {
        String sql = "insert into `order` (order_no, goods_id, goods_name, user_id) values (?, ?, ?, ?)";
        Object[] params = {orderEntity.getOrderNo(), orderEntity.getGoodsId(), orderEntity.getGoodsName(),
                orderEntity.getUserId()};
        return super.executeUpdate(sql, params);
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
