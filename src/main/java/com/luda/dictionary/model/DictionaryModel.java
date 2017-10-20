package com.luda.dictionary.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/19.
 */
public class DictionaryModel implements Serializable {
    /**
     * id
     */
    private int dictId;
    /**
     * 类型
     */
    private String dictType;
    /**
     * 名称
     */
    private String dictName;

    public DictionaryModel(){

    }
    public DictionaryModel(int dictId, String dictName, String dictType){
        this.dictId = dictId;
        this.dictName = dictName;
        this.dictType = dictType;
    }

    public int getDictId() {
        return dictId;
    }

    public void setDictId(int dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Override
    public String toString() {
        return "DictionaryModel{" +
                "dictId=" + dictId +
                ", dictType=" + dictType +
                ", dictName='" + dictName + '\'' +
                '}';
    }
}
