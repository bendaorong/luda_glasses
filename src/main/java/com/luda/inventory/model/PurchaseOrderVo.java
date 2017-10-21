package com.luda.inventory.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/21.
 */
public class PurchaseOrderVo {
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
     * 门店名称
     */
    private String storeName;
    /**
     * 供应商Id
     */
    private int supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 业务员Id
     */
    private int businessmanId;
    /**
     * 业务员名称
     */
    private String businessmanName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getBusinessmanId() {
        return businessmanId;
    }

    public void setBusinessmanId(int businessmanId) {
        this.businessmanId = businessmanId;
    }

    public String getBusinessmanName() {
        return businessmanName;
    }

    public void setBusinessmanName(String businessmanName) {
        this.businessmanName = businessmanName;
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

    @Override
    public String toString() {
        return "PurchaseOrderVo{" +
                "purchaseOrderId=" + purchaseOrderId +
                ", code='" + code + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", businessmanId=" + businessmanId +
                ", businessmanName='" + businessmanName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalAmount=" + totalAmount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
