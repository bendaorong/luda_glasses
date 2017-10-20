package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * 商品类型
 * Created by Administrator on 2017/10/15.
 */
public class GoodsType implements Serializable{
    /**
     * 类型id
     */
    private int typeId;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 类型种类
     */
    private int kindId;

    // 以下为非数据库字段
    /**
     * 种类名称
     */
    private String kindName;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

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
        return "GoodsType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", kindId=" + kindId +
                ", kindName='" + kindName + '\'' +
                '}';
    }
}
