package com.luda.sales.model;

/**
 * Created by Administrator on 2017/10/28.
 */
public class SalesOrderVo extends SalesOrder{
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 业务员
     */
    private String businessManName;
    /**
     * 客户
     */
    private String customerName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBusinessManName() {
        return businessManName;
    }

    public void setBusinessManName(String businessManName) {
        this.businessManName = businessManName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return super.toString() + " SalesOrderVo{" +
                "storeName='" + storeName + '\'' +
                ", businessManName='" + businessManName + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
