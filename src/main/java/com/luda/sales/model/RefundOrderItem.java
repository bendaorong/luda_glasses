package com.luda.sales.model;

import com.luda.materiel.model.MaterielModel;

import java.math.BigDecimal;

/**
 * 退货单明细
 * Created by Administrator on 2017/10/29.
 */
public class RefundOrderItem {
    private int itemId;
    /**
     * 退货单id
     */
    private int refundOrderId;
    /**
     * 销售单明细id
     */
    private int salesOrderItemId;
    /**
     * 商品
     */
    private int materielId;
    /**
     * 数量
     */
    private int quantity;
    /**
     * 售价
     */
    private BigDecimal sellPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 商品
     */
    private MaterielModel materiel;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(int refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public int getSalesOrderItemId() {
        return salesOrderItemId;
    }

    public void setSalesOrderItemId(int salesOrderItemId) {
        this.salesOrderItemId = salesOrderItemId;
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

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
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

    @Override
    public String toString() {
        return "RefundOrderItem{" +
                "itemId=" + itemId +
                ", refundOrderId=" + refundOrderId +
                ", salesOrderItemId=" + salesOrderItemId +
                ", materielId=" + materielId +
                ", quantity=" + quantity +
                ", sellPrice=" + sellPrice +
                ", remark='" + remark + '\'' +
                ", materiel=" + materiel +
                '}';
    }
}
