package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * 销售统计报表数据-按商品类型
 * Created by Administrator on 2018/2/4.
 */
public class SaleStatisticsChartByMaterielType {
    private int typeId;
    private String typeName;
    /**
     * * 金额
     */
    private BigDecimal amount;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
