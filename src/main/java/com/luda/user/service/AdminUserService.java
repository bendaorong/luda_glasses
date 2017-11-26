package com.luda.user.service;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.user.model.AdminRoleModel;
import com.luda.user.model.AdminUserDetailModel;
import com.luda.user.model.AdminUserModel;

import java.util.List;

public interface AdminUserService {
    /**
     * 根据id查询用户
     * @param adminUserId 用户Id
     */
    AdminUserModel getWithDetailById(int adminUserId);

    /**
     * 根据手机号查询用户
     * @param mobileNumber
     * @return
     */
    AdminUserModel getByMobileNumber(String mobileNumber);

    /**
     * 用户登录
     * @param loginModel 登录信息
     */
    ResultHandle<AdminUserModel> login(AdminUserModel loginModel);

    /**
     * 查询用户
     * @return
     */
    List<AdminUserModel> fetchAdminUserListWithDetail(CommonQueryBean queryBean);

    /**
     * 查询用户详情
     */
    AdminUserDetailModel getAdminUserDetail(int adminUserId);

    /**
     * 修改用户信息
     * @param adminUserModel
     * @return
     */
    ResultHandle<AdminUserModel> updateAdminUser(AdminUserModel adminUserModel);

    /**
     * 创建用户
     * @param adminUserModel
     * @return
     */
    ResultHandle<AdminUserModel> saveAdminUser(AdminUserModel adminUserModel);

    /**
     * 删除用户
     * @param adminUserId 用户Id
     * @return
     */
    ResultHandle<AdminUserModel> removeAdminUser(int adminUserId);

    /**
     * 重设用户密码
     * @param adminUserId
     * @return
     */
    ResultHandle<String> resetPwd(int adminUserId);

    /**
     * 获取角色列表
     */
    List<AdminRoleModel> getAdminRoleList();
}