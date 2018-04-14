package org.fuqinqin.code.controller;

import org.fuqinqin.code.order.IOrderService;
import org.fuqinqin.code.order.OrderEntity;
import org.fuqinqin.code.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
@Scope("prototype")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisClient<String, String> redisClient;


    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public OrderEntity findById(@PathVariable("orderId") Long orderId){
        OrderEntity orderEntity = orderService.findById(orderId);
        logger.info("orderEntity : "+orderEntity);

//        redisClient.set("hello_1111", "world");

        System.out.println(redisClient.getExpire("hello_1111"));
        System.out.println(redisClient.getExpire("hello"));

        return orderEntity;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderEntity> fiindAll(){
        List<OrderEntity> list = orderService.findAll();
        logger.info("list : "+list);
        return list;
    }

}
