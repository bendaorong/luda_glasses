package com.luda.user.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.luda.comm.po.DateJsonDeserializer;
import com.luda.comm.po.DateJsonSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详情
 * Created by Administrator on 2017/10/5.
 */
public class AdminUserDetailModel implements Serializable{
    private static final long serialVersionUID = 2268961291099388271L;
    /**
     * 用户id
     */
    private int adminUserId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证
     */
    private String idNo;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 地址
     */
    private String address;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 学历
     */
    private String education;
    /**
     * 婚姻状况
     */
    private String maritalStatus;
    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 开户行
     */
    private String bank;
    /**
     * 微信
     */
    private String wechatNumber;
    /**
     * QQ
     */
    private String qqNumber;
    /**
     * 职位
     */
    private String position;
    /**
     * 入职时间
     */
//    @JsonSerialize(using = DateJsonSerializer.class)
//    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date onboardDate;
    /**
     * 离职时间
     */
//    @JsonSerialize(using = DateJsonSerializer.class)
//    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date dimissionDate;

    /**
     * 创建时间
     */
//    @JsonSerialize(using = DateJsonSerializer.class)
//    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date createDatetime;

    /**
     * 更新时间
     */
//    @JsonSerialize(using = DateJsonSerializer.class)
//    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date updateDatetime;

    /**
     * 邮政编码
     */
    private String postcode;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(Date onboardDate) {
        this.onboardDate = onboardDate;
    }

    public Date getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(Date dimissionDate) {
        this.dimissionDate = dimissionDate;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    @Override
    public String toString() {
        return "AdminUserDetailModel{" +
                "adminUserId=" + adminUserId +
                ", realname='" + realname + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", idNo='" + idNo + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", education='" + education + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", bank='" + bank + '\'' +
                ", wechatNumber='" + wechatNumber + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", position='" + position + '\'' +
                ", onboardDate=" + onboardDate +
                ", dimissionDate=" + dimissionDate +
                ", createDatetime=" + createDatetime +
                ", updateDatetime=" + updateDatetime +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
