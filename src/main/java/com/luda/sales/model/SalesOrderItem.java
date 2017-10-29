package com.luda.sales.model;

import com.luda.materiel.model.MaterielModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售明细
 * Created by Administrator on 2017/10/28.
 */
public class SalesOrderItem implements Serializable {
    private int itemId;
    /**
     * 销售单id
     */
    private int salesOrderId;
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

    public int getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
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
        return "SalesListItem{" +
                "itemId=" + itemId +
                ", salesOrderId=" + salesOrderId +
                ", materielId=" + materielId +
                ", quantity=" + quantity +
                ", sellPrice=" + sellPrice +
                ", remark='" + remark + '\'' +
                ", materiel=" + materiel +
                '}';
    }
}
