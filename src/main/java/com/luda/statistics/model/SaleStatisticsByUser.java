package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/11/19.
 */
public class SaleStatisticsByUser {
    /**
     * 用户
     */
    private int adminUserId;
    /**
     * 用户名
     */
    private String adminName;
    /**
     * 数量小计
     */
    private int subtotalQuantity;
    /**
     * 金额小计
     */
    private BigDecimal subtotalAmount;

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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
        return "SaleStatisticsByUser{" +
                "adminUserId=" + adminUserId +
                ", adminName='" + adminName + '\'' +
                ", subtotalQuantity=" + subtotalQuantity +
                ", subtotalAmount=" + subtotalAmount +
                '}';
    }
}
