package com.luda.statistics.model;

import java.math.BigDecimal;

/**
 * 采购报表-按商品统计
 * Created by Administrator on 2017/11/19.
 */
public class PurchaseStatisticsByMateriel {
    /**
     * 商品
     */
    private int materielId;
    /**
     * 商品名称
     */
    private String materielName;
    /**
     * 商品类型
     */
    private String typeName;
    /**
     * 球镜
     */
    private double sphere;
    /**
     * 柱镜
     */
    private double cylinder;
    /**
     * 轴向
     */
    private double axial;
    /**
     * 数量小计
     */
    private int subtotalQuantity;
    /**
     * 金额小计
     */
    private BigDecimal subtotalAmount;

    public int getMaterielId() {
        return materielId;
    }

    public void setMaterielId(int materielId) {
        this.materielId = materielId;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public double getSphere() {
        return sphere;
    }

    public void setSphere(double sphere) {
        this.sphere = sphere;
    }

    public double getCylinder() {
        return cylinder;
    }

    public void setCylinder(double cylinder) {
        this.cylinder = cylinder;
    }

    public double getAxial() {
        return axial;
    }

    public void setAxial(double axial) {
        this.axial = axial;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SaleStatisticsByMateriel{" +
                "materielId=" + materielId +
                ", materielName='" + materielName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", sphere=" + sphere +
                ", cylinder=" + cylinder +
                ", axial=" + axial +
                ", subtotalQuantity=" + subtotalQuantity +
                ", subtotalAmount=" + subtotalAmount +
                '}';
    }
}
