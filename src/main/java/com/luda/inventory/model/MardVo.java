package com.luda.inventory.model;

import com.luda.materiel.model.MaterielModel;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/21.
 */
public class MardVo {
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
    /**
     * 商品
     */
    private MaterielModel materiel;

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

    public MaterielModel getMateriel() {
        return materiel;
    }

    public void setMateriel(MaterielModel materiel) {
        this.materiel = materiel;
    }

    @Override
    public String toString() {
        return "MardVo{" +
                "id=" + id +
                ", materielId=" + materielId +
                ", storeId=" + storeId +
                ", currentInventory=" + currentInventory +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", materiel=" + materiel +
                '}';
    }
}
