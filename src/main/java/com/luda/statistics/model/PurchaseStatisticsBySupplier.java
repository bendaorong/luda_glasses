package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/11/21.
 */
public class PurchaseStatisticsBySupplier {
    /**
     * 供应商id
     */
    private int supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 数量小计
     */
    private int subtotalQuantity;
    /**
     * 金额小计
     */
    private BigDecimal subtotalAmount;

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

    public int getSubtotalQuantity() {
        return subtotalQuantity;
    }

    public void setSubtotalQuantity(int subtotalQuantity) {
        this.subtotalQuantity = subtotalQuantity;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    @Override
    public String toString() {
        return "PurchaseStatisticsBySupplier{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", subtotalQuantity=" + subtotalQuantity +
                ", subtotalAmount=" + subtotalAmount +
                '}';
    }
}
