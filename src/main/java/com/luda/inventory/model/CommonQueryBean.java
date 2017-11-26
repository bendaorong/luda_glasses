package com.luda.inventory.model;

/**
 * Created by Administrator on 2017/11/24.
 */
public class CommonQueryBean {
    /**
     * 订单号
     */
    private Integer orderId;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 门店
     */
    private Integer storeId;
    /**
     * 业务员
     */
    private Integer businessManId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(Integer businessManId) {
        this.businessManId = businessManId;
    }

    @Override
    public String toString() {
        return "CommonQueryBean{" +
                "orderId=" + orderId +
                ", orderType='" + orderType + '\'' +
                ", storeId=" + storeId +
                ", businessManId=" + businessManId +
                '}';
    }
}
