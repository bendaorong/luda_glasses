package com.luda.user.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/5.
 */
public class AdminRoleModel implements Serializable{
    private static final long serialVersionUID = 4007459507505583294L;
    /**
     * id
     */
    private int roleId;
    /**
     * 角色编号
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "AdminRoleModel{" +
                "roleId=" + roleId +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
