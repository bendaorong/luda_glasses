package com.luda.inventory.model;

/**
 * Created by Administrator on 2018/11/20.
 */
public class MardQueryBean {
    /**
     * 门店
     */
    private Integer storeId;
    /**
     * 品牌
     */
    private Integer typeId;
    /**
     * 商品
     */
    private Integer materielId;
    /**
     * 球镜
     */
    private Double sphere;
    /**
     * 柱镜
     */
    private Double cylinder;
    /**
     * 度数
     */
    private Double axial;

    private int pageNo;
    private int startIndex;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Integer materielId) {
        this.materielId = materielId;
    }

    public Double getSphere() {
        return sphere;
    }

    public void setSphere(Double sphere) {
        this.sphere = sphere;
    }

    public Double getCylinder() {
        return cylinder;
    }

    public void setCylinder(Double cylinder) {
        this.cylinder = cylinder;
    }

    public Double getAxial() {
        return axial;
    }

    public void setAxial(Double axial) {
        this.axial = axial;
    }
}
