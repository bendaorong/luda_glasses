package com.luda.inventory.model;

import java.util.Date;

/**
 * 商品库存
 * 记录某一个门店的某一个商品的现时库存量
 * 该库存量随着采购入库，销售出库，库存盘点，库存调拨等操作而实时更新
 * Created by Administrator on 2017/10/21.
 */
public class Mard {
    private int id;
    /**
     * 商品id
     */
    private int materielId;
    /**
     * 门店Id
     */
    private int storeId;
    /**
     * 当前库存量
     */
    private int currentInventory;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Mard(){}

    public Mard(int materielId, int storeId, int currentInventory){
        this.materielId = materielId;
        this.storeId = storeId;
        this.currentInventory = currentInventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterielId() {
        return materielId;
    }

    public void setMaterielId(int materielId) {
        this.materielId = materielId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(int currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Mard{" +
                "id=" + id +
                ", materielId=" + materielId +
                ", storeId=" + storeId +
                ", currentInventory=" + currentInventory +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
