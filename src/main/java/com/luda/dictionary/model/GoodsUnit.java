package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * 商品单位
 * Created by Administrator on 2017/10/15.
 */
public class GoodsUnit implements Serializable{
    /**
     * 单位Id
     */
    private int unitId;
    /**
     * 单位名称
     */
    private String unitName;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return "GoodsUnit{" +
                "unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}
