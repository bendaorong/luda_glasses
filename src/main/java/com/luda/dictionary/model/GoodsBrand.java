package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * 商品品牌
 * Created by Administrator on 2017/10/15.
 */
public class GoodsBrand implements Serializable{
    /**
     * 品牌id
     */
    private int brandId;
    /**
     * 品牌名称
     */
    private String brandName;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "GoodsBrand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
