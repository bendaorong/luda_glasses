package com.luda.inventory.model;

/**
 * 库存盘点明细
 * Created by Administrator on 2017/10/24.
 */
public class InventoryVerificationItem {
    private int id;
    /**
     * 盘点Id
     */
    private int inventoryVerificationId;
    /**
     * 商品
     */
    private int materielId;
    /**
     * (盘盈盘亏)数量
     */
    private int quantity;
    /**
     * 类型 01:盘盈 02:盘亏
     */
    private String type;
    /**
     * 备注
     */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventoryVerificationId() {
        return inventoryVerificationId;
    }

    public void setInventoryVerificationId(int inventoryVerificationId) {
        this.inventoryVerificationId = inventoryVerificationId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InventoryVerificationItem{" +
                "id=" + id +
                ", inventoryVerificationId=" + inventoryVerificationId +
                ", materielId=" + materielId +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
