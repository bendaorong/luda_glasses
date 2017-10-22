package com.luda.inventory.model;

import com.luda.materiel.model.MaterielModel;

import java.math.BigDecimal;

/**
 * 采购单明细
 * Created by Administrator on 2017/10/21.
 */
public class PurchaseOrderItem {
    private int itemId;
    /**
     * 采购单id
     */
    private int purchaseOrderId;
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
     * 备注
     */
    private String remark;

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
                ", materielId=" + materielId +
                ", purchasePrice=" + purchasePrice +
                ", purchaseQuantity=" + purchaseQuantity +
                ", remark='" + remark + '\'' +
                '}';
    }
}
