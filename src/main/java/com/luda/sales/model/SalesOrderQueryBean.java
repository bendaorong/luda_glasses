package com.luda.sales.model;

/**
 * 销售单查询辅助类
 * Created by Administrator on 2017/11/24.
 */
public class SalesOrderQueryBean {
    /**
     * 销售单号
     */
    private Integer orderId;
    /**
     * 门店
     */
    private Integer storeId;
    /**
     * 业务员
     */
    private Integer businessManId;
    /**
     * 客户
     */
    private Integer customerId;
    /**
     * 订单类型
     */
    private String orderType;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "SalesOrderQueryBean{" +
                "orderId=" + orderId +
                ", storeId=" + storeId +
                ", businessManId=" + businessManId +
                ", customerId=" + customerId +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
