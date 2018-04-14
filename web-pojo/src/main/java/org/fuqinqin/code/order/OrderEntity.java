package org.fuqinqin.code.order;

import java.io.Serializable;

public class OrderEntity implements Serializable{

    private Long orderId;
    private String orderNo;
    private Long goodsId;
    private String goodsName;
    private Long userId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderEntity{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
