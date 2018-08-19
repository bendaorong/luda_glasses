package com.luda.customer.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 验光记录
 * Created by Administrator on 2017/11/5.
 */
public class OptometryRecord implements Serializable {
    private int id;
    /**
     * 客户
     */
    private int customerId;
    /**
     * 验光师
     */
    private String optometrist;
    /**
     * 验光日期
     */
    private Date optometryDate;
    /**
     * 右球镜
     */
    private double rightSphere;
    /**
     * 左球镜
     */
    private double leftSphere;
    /**
     * 右柱镜
     */
    private double rightCylinder;
    /**
     * 左柱镜
     */
    private double leftCylinder;
    /**
     * 右轴向
     */
    private double rightAxial;
    /**
     * 左轴向
     */
    private double leftAxial;
    /**
     * 右裸视
     */
    private double rightUncorrectedVisualAcuity;
    /**
     * 左裸视
     */
    private double leftUncorrectedVisualAcuity;

    /**
     * 右矫正
     */
    private double rightCorrectedVisualAcuity;
    /**
     * 左矫正
     */
    private double leftCorrectedVisualAcuity;
    /**
     * 备注
     */
    private int remark;
    /**
     * 创建用户
     */
    private int createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新用户
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOptometryDate() {
        return optometryDate;
    }

    public void setOptometryDate(Date optometryDate) {
        this.optometryDate = optometryDate;
    }

    public double getRightSphere() {
        return rightSphere;
    }

    public void setRightSphere(double rightSphere) {
        this.rightSphere = rightSphere;
    }

    public double getLeftSphere() {
        return leftSphere;
    }

    public void setLeftSphere(double leftSphere) {
        this.leftSphere = leftSphere;
    }

    public double getRightCylinder() {
        return rightCylinder;
    }

    public void setRightCylinder(double rightCylinder) {
        this.rightCylinder = rightCylinder;
    }

    public double getLeftCylinder() {
        return leftCylinder;
    }

    public void setLeftCylinder(double leftCylinder) {
        this.leftCylinder = leftCylinder;
    }

    public double getRightAxial() {
        return rightAxial;
    }

    public void setRightAxial(double rightAxial) {
        this.rightAxial = rightAxial;
    }

    public double getLeftAxial() {
        return leftAxial;
    }

    public void setLeftAxial(double leftAxial) {
        this.leftAxial = leftAxial;
    }

    public double getRightUncorrectedVisualAcuity() {
        return rightUncorrectedVisualAcuity;
    }

    public void setRightUncorrectedVisualAcuity(double rightUncorrectedVisualAcuity) {
        this.rightUncorrectedVisualAcuity = rightUncorrectedVisualAcuity;
    }

    public double getLeftUncorrectedVisualAcuity() {
        return leftUncorrectedVisualAcuity;
    }

    public void setLeftUncorrectedVisualAcuity(double leftUncorrectedVisualAcuity) {
        this.leftUncorrectedVisualAcuity = leftUncorrectedVisualAcuity;
    }

    public double getRightCorrectedVisualAcuity() {
        return rightCorrectedVisualAcuity;
    }

    public void setRightCorrectedVisualAcuity(double rightCorrectedVisualAcuity) {
        this.rightCorrectedVisualAcuity = rightCorrectedVisualAcuity;
    }

    public double getLeftCorrectedVisualAcuity() {
        return leftCorrectedVisualAcuity;
    }

    public void setLeftCorrectedVisualAcuity(double leftCorrectedVisualAcuity) {
        this.leftCorrectedVisualAcuity = leftCorrectedVisualAcuity;
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

    public String getOptometrist() {
        return optometrist;
    }

    public void setOptometrist(String optometrist) {
        this.optometrist = optometrist;
    }

    @Override
    public String toString() {
        return "OptometryRecord{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", optometrist='" + optometrist + '\'' +
                ", optometryDate=" + optometryDate +
                ", rightSphere=" + rightSphere +
                ", leftSphere=" + leftSphere +
                ", rightCylinder=" + rightCylinder +
                ", leftCylinder=" + leftCylinder +
                ", rightAxial=" + rightAxial +
                ", leftAxial=" + leftAxial +
                ", rightUncorrectedVisualAcuity=" + rightUncorrectedVisualAcuity +
                ", leftUncorrectedVisualAcuity=" + leftUncorrectedVisualAcuity +
                ", rightCorrectedVisualAcuity=" + rightCorrectedVisualAcuity +
                ", leftCorrectedVisualAcuity=" + leftCorrectedVisualAcuity +
                ", remark=" + remark +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                '}';
    }
}
