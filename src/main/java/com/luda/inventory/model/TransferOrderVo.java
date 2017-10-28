package com.luda.inventory.model;

/**
 * Created by Administrator on 2017/10/26.
 */
public class TransferOrderVo extends TransferOrder{
    /**
     * 调出门店
     */
    private String outStoreName;
    /**
     * 调入门店
     */
    private String inStoreName;
    /**
     * 业务员
     */
    private String businessmanName;

    public String getOutStoreName() {
        return outStoreName;
    }

    public void setOutStoreName(String outStoreName) {
        this.outStoreName = outStoreName;
    }

    public String getInStoreName() {
        return inStoreName;
    }

    public void setInStoreName(String inStoreName) {
        this.inStoreName = inStoreName;
    }

    public String getBusinessmanName() {
        return businessmanName;
    }

    public void setBusinessmanName(String businessmanName) {
        this.businessmanName = businessmanName;
    }

    @Override
    public String toString() {
        return super.toString() + " TransferOrderVo{" +
                "outStoreName='" + outStoreName + '\'' +
                ", inStoreName='" + inStoreName + '\'' +
                ", businessmanName='" + businessmanName + '\'' +
                '}';
    }
}
