package com.luda.inventory.model;

import com.luda.materiel.model.MaterielModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购单明细
 * Created by Administrator on 2017/10/21.
 */
public class PurchaseOrderItem implements Serializable {
    private int itemId;
    /**
     * 采购单id
     */
    private int purchaseOrderId;
    /**
     * 库存id
     */
    private int mardId;
    /**
     * 商品id
     */
    private int materielId;
    /**
     * 商品
     */
    private MaterielModel materiel;
    /**
     * 采购价
     */
    private BigDecimal purchasePrice = BigDecimal.ZERO;
    /**
     * 采购数量
     */
    private int purchaseQuantity;
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
     * 备注
     */
    private String remark;

    public int getMardId() {
        return mardId;
    }

    public void setMardId(int mardId) {
        this.mardId = mardId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public int getMaterielId() {
        return materielId;
    }

    public void setMaterielId(int materielId) {
        this.materielId = materielId;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public MaterielModel getMateriel() {
        return materiel;
    }

    public void setMateriel(MaterielModel materiel) {
        this.materiel = materiel;
    }

    /**
     * 计算明细小计
     * 明细小计 = 采购价 * 采购数量
     * @return
     */
    public BigDecimal getTotalAmount(){
        return purchasePrice.multiply(BigDecimal.valueOf(purchaseQuantity));
    }

    @Override
    public String toString() {
        return "PurchaseOrderItem{" +
                "itemId=" + itemId +
                ", purchaseOrderId=" + purchaseOrderId +
                ", mardId=" + mardId +
                ", materielId=" + materielId +
                ", materiel=" + materiel +
                ", purchasePrice=" + purchasePrice +
                ", purchaseQuantity=" + purchaseQuantity +
                ", sphere=" + sphere +
                ", cylinder=" + cylinder +
                ", axial=" + axial +
                ", remark='" + remark + '\'' +
                '}';
    }
}
