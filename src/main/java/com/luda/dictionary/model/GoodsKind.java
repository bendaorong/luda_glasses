package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * 商品种类
 * Created by Administrator on 2017/10/15.
 */
public class GoodsKind implements Serializable{
    /**
     * 种类id
     */
    private int kindId;
    /**
     * 种类名称
     */
    private String kindName;

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    @Override
    public String toString() {
        return "GoodsKind{" +
                "kindId=" + kindId +
                ", kindName='" + kindName + '\'' +
                '}';
    }
}
