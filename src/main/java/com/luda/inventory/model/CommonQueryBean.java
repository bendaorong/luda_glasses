package com.luda.inventory.model;

/**
 * Created by Administrator on 2017/11/24.
 */
public class CommonQueryBean {
    /**
     * 订单号
     */
    private Integer orderId;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 门店
     */
    private Integer storeId;
    /**
     * 业务员
     */
    private Integer businessManId;
    /**
     * 商品id
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
    /**
     * startIndex
     */
    private int startIndex;

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

    public Integer getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Integer materielId) {
        this.materielId = materielId;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(Integer businessManId) {
        this.businessManId = businessManId;
    }

    public Double getAxial() {
        return axial;
    }

    public void setAxial(Double axial) {
        this.axial = axial;
    }

    @Override
    public String toString() {
        return "CommonQueryBean{" +
                "orderId=" + orderId +
                ", orderType='" + orderType + '\'' +
                ", storeId=" + storeId +
                ", businessManId=" + businessManId +
                ", materielId=" + materielId +
                ", sphere=" + sphere +
                ", cylinder=" + cylinder +
                ", axial=" + axial +
                ", startIndex=" + startIndex +
                '}';
    }
}
