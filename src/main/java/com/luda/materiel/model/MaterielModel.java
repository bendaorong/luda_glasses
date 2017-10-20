package com.luda.materiel.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * Created by Administrator on 2017/10/15.
 */
public class MaterielModel implements Serializable{
    /**
     * id
     */
    private int id;
    /**
     * 商品编号
     */
    private String code;
    /**
     * 条码
     */
    private String barcode;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品类型
     */
    private int typeId;
    /**
     * 售价
     */
    private double sellPrice;
    /**
     * 批发价
     */
    private double tradePrice;
    /**
     * 成本价
     */
    private double costPrice;
    /**
     * 单位
     */
    private String unit;
    /**
     * 品牌
     */
    private String brand;

    /**
     * 规格型号
     */
    private String specification;
    /**
     * 颜色
     */
    private String color;
    /**
     * 材质
     */
    private String texture;
    /**
     * 生产企业
     */
    private String manufacturer;
    /**
     * 库存下限
     */
    private int minInventory;
    /**
     * 库存上限
     */
    private int maxInventory;
    /**
     * 停用
     */
    private int useFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建用户
     */
    private int creatorUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新用户
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;

    // 以下为非数据库字段
    /**
     * 商品类型名称
     */
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getMinInventory() {
        return minInventory;
    }

    public void setMinInventory(int minInventory) {
        this.minInventory = minInventory;
    }

    public int getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(int maxInventory) {
        this.maxInventory = maxInventory;
    }

    public int getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MaterielModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", sellPrice=" + sellPrice +
                ", tradePrice=" + tradePrice +
                ", costPrice=" + costPrice +
                ", unit=" + unit +
                ", brand=" + brand +
                ", specification='" + specification + '\'' +
                ", color=" + color +
                ", texture='" + texture + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", minInventory=" + minInventory +
                ", maxInventory=" + maxInventory +
                ", useFlag=" + useFlag +
                ", remark='" + remark + '\'' +
                ", creatorUserId=" + creatorUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                '}';
    }
}
