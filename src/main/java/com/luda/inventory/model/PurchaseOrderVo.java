package com.luda.inventory.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/21.
 */
public class PurchaseOrderVo extends PurchaseOrder{
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 供应商名称
     */
    private String supplierName;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBusinessmanName() {
        return businessmanName;
    }

    public void setBusinessmanName(String businessmanName) {
        this.businessmanName = businessmanName;
    }

    @Override
    public String toString() {
        return "PurchaseOrderVo{" +
                "storeName='" + storeName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", businessmanName='" + businessmanName + '\'' +
                '}';
    }
}
