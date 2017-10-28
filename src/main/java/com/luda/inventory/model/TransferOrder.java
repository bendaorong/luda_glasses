package com.luda.inventory.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 库存调拨单
 * Created by Administrator on 2017/10/26.
 */
public class TransferOrder implements Serializable{
    private int id;
    /**
     * 编号
     */
    private String code;
    /**
     * 调拨日期
     */
    private Date transferDate;
    /**
     * 调出门店
     */
    private int outStoreId;
    /**
     * 调入门店
     */
    private int inStoreId;
    /**
     * 业务员
     */
    private int businessmanId;
    /**
     * 备注
     */
    private String remark;
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
     * 调拨明细
     */
    private List<TransferOrderItem> transferOrderItems;

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

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public int getOutStoreId() {
        return outStoreId;
    }

    public void setOutStoreId(int outStoreId) {
        this.outStoreId = outStoreId;
    }

    public int getInStoreId() {
        return inStoreId;
    }

    public void setInStoreId(int inStoreId) {
        this.inStoreId = inStoreId;
    }

    public int getBusinessmanId() {
        return businessmanId;
    }

    public void setBusinessmanId(int businessmanId) {
        this.businessmanId = businessmanId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
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

    public List<TransferOrderItem> getTransferOrderItems() {
        return transferOrderItems;
    }

    public void setTransferOrderItems(List<TransferOrderItem> transferOrderItems) {
        this.transferOrderItems = transferOrderItems;
    }

    @Override
    public String toString() {
        return "TransferOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", transferDate=" + transferDate +
                ", outStoreId=" + outStoreId +
                ", inStoreId=" + inStoreId +
                ", businessmanId=" + businessmanId +
                ", remark='" + remark + '\'' +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", transferOrderItems=" + transferOrderItems +
                '}';
    }
}
