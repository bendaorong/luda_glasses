package com.luda.customer.model;

import java.io.Serializable;

/**
 * 客户
 * Created by I075723 on 10/25/2017.
 */
public class CustomerModel implements Serializable{
    private int id;

    private String code;

    private String name;

    private String gender;

    private int age;

    private String birthday;

    private String mobileNumber;

    private String wechatno;

    private String address;

    private String region;

    private String regionName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWechatno() {
        return wechatno;
    }

    public void setWechatno(String wechatno) {
        this.wechatno = wechatno;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", wechatno='" + wechatno + '\'' +
                ", address='" + address + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
