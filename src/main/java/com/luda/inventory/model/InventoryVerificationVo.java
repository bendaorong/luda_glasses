package com.luda.inventory.model;

/**
 * Created by Administrator on 2017/10/24.
 */
public class InventoryVerificationVo extends InventoryVerification{
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 业务员名称
     */
    private String businessmanName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBusinessmanName() {
        return businessmanName;
    }

    public void setBusinessmanName(String businessmanName) {
        this.businessmanName = businessmanName;
    }
}
