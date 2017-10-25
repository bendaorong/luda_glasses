package com.luda.inventory.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
public class InventoryVerification implements Serializable {
    private int id;
    /**
     * 编号
     */
    private String code;
    /**
     * 盘点日期
     */
    private Date verifDate;
    /**
     * 门店
     */
    private int storeId;
    /**
     * 业务员Id
     */
    private int businessmanId;
    /**
     * 备注
     */
    private int remark;
    /**
     * 创建人
     */
    private int createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 盘点明细
     */
    private List<InventoryVerificationItem> invtVerifItemList;

    public Date getVerifDate() {
        return verifDate;
    }

    public void setVerifDate(Date verifDate) {
        this.verifDate = verifDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getBusinessmanId() {
        return businessmanId;
    }

    public void setBusinessmanId(int businessmanId) {
        this.businessmanId = businessmanId;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<InventoryVerificationItem> getInvtVerifItemList() {
        return invtVerifItemList;
    }

    public void setInvtVerifItemList(List<InventoryVerificationItem> invtVerifItemList) {
        this.invtVerifItemList = invtVerifItemList;
    }

    @Override
    public String toString() {
        return "InventoryVerification{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", storeId=" + storeId +
                ", businessmanId=" + businessmanId +
                ", remark=" + remark +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", invtVerifItemList=" + invtVerifItemList +
                '}';
    }
}
