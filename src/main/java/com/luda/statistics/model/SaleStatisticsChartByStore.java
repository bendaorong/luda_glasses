package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/6.
 */
public class SaleStatisticsChartByStore {
    private int storeId;
    private String storeName;
    /**
     * * 金额
     */
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
