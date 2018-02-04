package com.luda.statistics.model;

/**
 * 销售报表过滤条件
 * Created by Administrator on 2017/11/18.
 */
public class StatisticsCondition {
    /**
     * 开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 商品类型
     */
    private Integer typeId;
    /**
     * 门店
     */
    private Integer storeId;

    /**
     * 供应商
     */
    private Integer supplierId;

    /**
     * 统计维度
     * 1-按商品类型 2-按门店
     * @return
     */
    private int dimension;

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "StatisticsCondition{" +
                "beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", typeId=" + typeId +
                ", storeId=" + storeId +
                ", supplierId=" + supplierId +
                '}';
    }
}
