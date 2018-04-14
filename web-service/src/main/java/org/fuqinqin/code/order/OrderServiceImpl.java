package org.fuqinqin.code.order;

import org.fuqinqin.code.mapper.OrderMapper;
import org.fuqinqin.code.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private RedisClient<Long, OrderEntity> redisClient;

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

    public OrderEntity findById(Long orderId) {
        if(orderId == null){
            return null;
        }

        OrderEntity entity = orderMapper.findById(orderId);

        redisClient.set(orderId, entity);

        return entity;
    }

    public List<OrderEntity> findAll() {
        OrderEntity orderEntity = redisClient.get(10L);
        System.out.println("===============================================================================");
        System.out.println(orderEntity);
        System.out.println("===============================================================================");
        return orderMapper.findAll();
    }
}
