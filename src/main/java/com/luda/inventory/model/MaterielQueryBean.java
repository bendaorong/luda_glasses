package com.luda.inventory.model;

/**
 * Created by Administrator on 2018/11/15.
 */
public class MaterielQueryBean {
    private String name;
    private Integer typeId;
    private Integer startIndex;

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
