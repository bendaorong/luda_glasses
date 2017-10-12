package com.luda.supplier.model;

import java.io.Serializable;

/**
 * 供应商联系人
 * 一个供应商对应多个联系人
 * Created by Administrator on 2017/10/10.
 */
public class SupplierContactModel implements Serializable{
    private static final long serialVersionUID = 786265881791030566L;
    /**
     * 联系人id
     */
    private int contactId;
    /**
     * 供应商id
     */
    private int supplierId;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号码
     */
    private String mobileNumber;
    /**
     * 电话号码
     */
    private String telNumber;
    /**
     * 地址
     */
    private String address;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 邮政编码
     */
    private String postcode;
    /**
     * 是否为负责人(Y:是 N:否)
     */
    private String headFlag;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHeadFlag() {
        return headFlag;
    }

    public void setHeadFlag(String headFlag) {
        this.headFlag = headFlag;
    }

    @Override
    public String toString() {
        return "SupplierContactModel{" +
                "contactId=" + contactId +
                ", supplierId=" + supplierId +
                ", contactName='" + contactName + '\'' +
                ", gender='" + gender + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", postcode='" + postcode + '\'' +
                ", headFlag='" + headFlag + '\'' +
                '}';
    }
}
