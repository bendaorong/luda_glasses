package com.luda.inventory.model;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购入库单
 * Created by Administrator on 2017/10/21.
 */
public class PurchaseOrder implements Serializable {
    private int purchaseOrderId;
    /**
     * 采购单编号
     */
    private String code;
    /**
     * 采购日期
     */
    private Date purchaseDate;
    /**
     * 门店id
     */
    private int storeId;
    /**
     * 供应商Id
     */
    private int supplierId;
    /**
     * 业务员Id
     */
    private int businessmanId;
    /**
     * 总数量
     */
    private int totalQuantity;
    /**
     * 总金额
     */
    private BigDecimal totalAmount = BigDecimal.ZERO;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private int createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 采购明细
     */
    private List<PurchaseOrderItem> purchaseOrderItemList;

    /**
     * 是否批量采购
     * Y/N
     */
    private String isBatch;

    public String getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(String isBatch) {
        this.isBatch = isBatch;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getBusinessmanId() {
        return businessmanId;
    }

    public void setBusinessmanId(int businessmanId) {
        this.businessmanId = businessmanId;
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

    public List<PurchaseOrderItem> getPurchaseOrderItemList() {
        return purchaseOrderItemList;
    }

    public void setPurchaseOrderItemList(List<PurchaseOrderItem> purchaseOrderItemList) {
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取采购明细总数量
     * @return
     */
    public int getTotalItemQuantity(){
        int quantity = 0;
        if(CollectionUtils.isNotEmpty(purchaseOrderItemList)){
            for(PurchaseOrderItem item : purchaseOrderItemList){
                quantity += item.getPurchaseQuantity();
            }
        }
        return quantity;
    }

    /**
     * 获取采购明细总金额
     * @return
     */
    public BigDecimal getTotalItemAmount(){
        BigDecimal totalAmount = BigDecimal.ZERO;
        if(CollectionUtils.isNotEmpty(purchaseOrderItemList)){
            for(PurchaseOrderItem item : purchaseOrderItemList){
                totalAmount = totalAmount.add(item.getTotalAmount());
            }
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "purchaseOrderId=" + purchaseOrderId +
                ", code='" + code + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", storeId=" + storeId +
                ", supplierId=" + supplierId +
                ", businessmanId=" + businessmanId +
                ", totalQuantity=" + totalQuantity +
                ", totalAmount=" + totalAmount +
                ", remark='" + remark + '\'' +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", orderType='" + orderType + '\'' +
                ", purchaseOrderItemList=" + purchaseOrderItemList +
                '}';
    }

    /**
     * 是否为批量采购单
     * @return
     */
    public boolean isBatchOrder() {
        return "Y".equals(isBatch);
    }
}
