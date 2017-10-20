package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * 商品颜色
 * Created by Administrator on 2017/10/15.
 */
public class GoodsColor implements Serializable{
    /**
     * 颜色id
     */
    private int colorId;
    /**
     * 颜色名称
     */
    private String colorName;

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "GoodsColor{" +
                "colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                '}';
    }
}
