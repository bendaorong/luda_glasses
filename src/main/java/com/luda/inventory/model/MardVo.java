package com.luda.inventory.model;

import com.luda.materiel.model.MaterielModel;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/21.
 */
public class MardVo extends Mard{
    /**
     * 门店
     */
    private String storeName;
    /**
     * 商品
     */
    private MaterielModel materiel;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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
                "storeName='" + storeName + '\'' +
                ", materiel=" + materiel +
                '}';
    }
}
