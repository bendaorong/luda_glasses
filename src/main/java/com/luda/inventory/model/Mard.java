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
     * 门店Id
     */
    private int storeId;
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
        return "Mard{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", materielId=" + materielId +
                ", sphere=" + sphere +
                ", cylinder=" + cylinder +
                ", axial=" + axial +
                ", currentInventory=" + currentInventory +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
