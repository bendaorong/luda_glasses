package com.luda.sales.model;


import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售单&退货单
 * Created by Administrator on 2017/10/28.
 */
public class SalesOrder implements Serializable {
    /**
     * id
     */
    private int id;
    /**
     * 销售单编号
     */
    private String code;
    /**
     * 销售日期
     */
    private Date saleDate;
    /**
     * 门店
     */
    private int storeId;
    /**
     * 业务员
     */
    private int businessManId;
    /**
     * 客户
     */
    private int customerId;
    /**
     * 取货日期
     */
    private Date pickUpDate;
    /**
     * 总数量
     */
    private int totalQuantity;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 创建用户
     */
    private int createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新用户
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;

    private List<SalesOrderItem> salesOrderItems;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<SalesOrderItem> getSalesOrderItems() {
        return salesOrderItems;
    }

    public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
        this.salesOrderItems = salesOrderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(int businessManId) {
        this.businessManId = businessManId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取销售明细总数量
     * @return
     */
    public int getItemTotalQuantity(){
        int quantity = 0;
        if(CollectionUtils.isNotEmpty(salesOrderItems)){
            for(SalesOrderItem item : salesOrderItems){
                quantity += item.getQuantity();
            }
        }
        return quantity;
    }

    /**
     * 获取销售明细总金额
     * @return
     */
    public BigDecimal getItemTotalAmount(){
        BigDecimal amount = new BigDecimal(0);
        if(CollectionUtils.isNotEmpty(salesOrderItems)){
            for(SalesOrderItem item : salesOrderItems){
                amount = amount.add(item.getSellPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return amount;
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", saleDate=" + saleDate +
                ", storeId=" + storeId +
                ", businessManId=" + businessManId +
                ", customerId=" + customerId +
                ", pickUpDate=" + pickUpDate +
                ", totalQuantity=" + totalQuantity +
                ", totalAmount=" + totalAmount +
                ", remark='" + remark + '\'' +
                ", orderType='" + orderType + '\'' +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", salesOrderItems=" + salesOrderItems +
                '}';
    }
}
