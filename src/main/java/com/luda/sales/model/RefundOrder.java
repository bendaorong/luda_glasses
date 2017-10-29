package com.luda.sales.model;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 退货单
 * Created by Administrator on 2017/10/29.
 */
public class RefundOrder implements Serializable {
    /**
     * id
     */
    private int id;
    /**
     * 编号
     */
    private String code;
    /**
     * 销售单id
     */
    private int salesOrderId;
    /**
     * 退货日期
     */
    private Date refundDate;
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
     * 退货数量
     */
    private int totalQuantity;
    /**
     * 退货金额
     */
    private BigDecimal totalAmount;
    /**
     * 备注
     */
    private String remark;
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

    /**
     * 退款单明细
     */
    private List<RefundOrderItem> refundOrderItems;

    public List<RefundOrderItem> getRefundOrderItems() {
        return refundOrderItems;
    }

    public void setRefundOrderItems(List<RefundOrderItem> refundOrderItems) {
        this.refundOrderItems = refundOrderItems;
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

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
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

    public int getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    /**
     * 获取退货商品总数量
     * @return
     */
    public int getItemTotalQuantity() {
        int quantity = 0;
        if(CollectionUtils.isNotEmpty(refundOrderItems)){
            for(RefundOrderItem item : refundOrderItems){
                quantity += item.getQuantity();
            }
        }
        return quantity;
    }

    /**
     * 获取退货商品总金额
     * @return
     */
    public BigDecimal getItemTotalAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        if(CollectionUtils.isNotEmpty(refundOrderItems)){
            for(RefundOrderItem item : refundOrderItems){
                amount = amount.add(item.getSellPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return amount;
    }
}
