package com.luda.user.model;

import com.luda.comm.po.Constants;
import com.luda.store.model.StoreModel;

import java.io.Serializable;

public class AdminUserModel implements Serializable{
    private static final long serialVersionUID = 1909646931868440967L;
    /**
     * id
     */
    private int adminUserId;
    /**
     * 用户编号
     */
    private String adminCode;
    /**
     * 用户名
     */
    private String adminName;
    /**
     * 员工工号
     */
    private String staffid;
    /**
     * 手机号
     */
    private String mobileNumber;
    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     * 所属门店
     */
    private int storeId;
    /**
     * 角色Id
     */
    private int roleId;
    /**
     * 角色
     */
    private AdminRoleModel adminRoleModel;
    /**
     * 用户详情
     */
    private AdminUserDetailModel adminUserDetailModel;
    /**
     * 门店
     */
    private StoreModel storeModel;
    /**
     * 激活
     */
    private int activeFlag;

    public StoreModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(StoreModel storeModel) {
        this.storeModel = storeModel;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public AdminRoleModel getAdminRoleModel() {
        return adminRoleModel;
    }

    public void setAdminRoleModel(AdminRoleModel adminRoleModel) {
        this.adminRoleModel = adminRoleModel;
    }

    public AdminUserDetailModel getAdminUserDetailModel() {
        return adminUserDetailModel;
    }

    public void setAdminUserDetailModel(AdminUserDetailModel adminUserDetailModel) {
        this.adminUserDetailModel = adminUserDetailModel;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    /**
     * 是否为总经理
     * @return
     */
    public boolean isSuperManager(){
        return Constants.ROLE_SUPERMANAGER.equals(this.adminRoleModel.getRoleCode());
    }

    @Override
    public String toString() {
        return "AdminUserModel{" +
                "adminUserId=" + adminUserId +
                ", adminCode='" + adminCode + '\'' +
                ", adminName='" + adminName + '\'' +
                ", staffid='" + staffid + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", storeId=" + storeId +
                ", roleId=" + roleId +
                ", adminRoleModel=" + adminRoleModel +
                ", adminUserDetailModel=" + adminUserDetailModel +
                ", activeFlag=" + activeFlag +
                '}';
    }
}