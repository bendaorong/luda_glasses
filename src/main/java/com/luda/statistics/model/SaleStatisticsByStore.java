package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * 按门店统计销售信息
 * Created by Administrator on 2017/11/19.
 */
public class SaleStatisticsByStore {
    /**
     * 门店
     */
    private int storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 数量小计
     */
    private int subtotalQuantity;
    /**
     * 金额小计
     */
    private BigDecimal subtotalAmount;

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
        return "SaleStatisticsByStore{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", subtotalQuantity=" + subtotalQuantity +
                ", subtotalAmount=" + subtotalAmount +
                '}';
    }
}
