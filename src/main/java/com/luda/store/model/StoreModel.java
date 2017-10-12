package com.luda.store.model;

import java.io.Serializable;

/**
 * 门店
 * Created by Administrator on 2017/10/5.
 */
public class StoreModel implements Serializable{
    private static final long serialVersionUID = -5910365953205881344L;
    /**
     * 门店id
     */
    private int storeId;
    /**
     * 门店编号
     */
    private String storeCode;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 传真
     */
    private String faxPhone;
    /**
     * 门店地址
     */
    private String storeAddress;
    /**
     * qq号码
     */
    private String qqNumber;
    /**
     * 是否总店
     */
    private String headOfficeFlag;
    /**
     * 备注
     */
    private String remark;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFaxPhone() {
        return faxPhone;
    }

    public void setFaxPhone(String faxPhone) {
        this.faxPhone = faxPhone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getHeadOfficeFlag() {
        return headOfficeFlag;
    }

    public void setHeadOfficeFlag(String headOfficeFlag) {
        this.headOfficeFlag = headOfficeFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StoreModel{" +
                "storeId=" + storeId +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", faxPhone='" + faxPhone + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", headOfficeFlag='" + headOfficeFlag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
