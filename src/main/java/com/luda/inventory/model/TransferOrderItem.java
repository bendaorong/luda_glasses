package com.luda.inventory.model;

import com.luda.materiel.model.MaterielModel;

import java.io.Serializable;

/**
 * 库存调拨明细
 * Created by Administrator on 2017/10/26.
 */
public class TransferOrderItem implements Serializable{
    private int itemId;
    /**
     * 调拨单id
     */
    private int transferOrderId;
    /**
     * 库存id
     */
    private int mardId;
    /**
     * 商品id
     */
    private int materielId;
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
     * 调拨数量
     */
    private int quantity;
    /**
     * 备注
     */
    private String remark;

    /**
     * 商品
     */
    private MaterielModel materiel;

    public int getMardId() {
        return mardId;
    }

    public void setMardId(int mardId) {
        this.mardId = mardId;
    }

    public MaterielModel getMateriel() {
        return materiel;
    }

    public void setMateriel(MaterielModel materiel) {
        this.materiel = materiel;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getTransferOrderId() {
        return transferOrderId;
    }

    public void setTransferOrderId(int transferOrderId) {
        this.transferOrderId = transferOrderId;
    }

    public int getMaterielId() {
        return materielId;
    }

    public void setMaterielId(int materielId) {
        this.materielId = materielId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "TransferOrderItem{" +
                "itemId=" + itemId +
                ", transferOrderId=" + transferOrderId +
                ", mardId=" + mardId +
                ", materielId=" + materielId +
                ", sphere=" + sphere +
                ", cylinder=" + cylinder +
                ", axial=" + axial +
                ", quantity=" + quantity +
                ", remark='" + remark + '\'' +
                ", materiel=" + materiel +
                '}';
    }
}
