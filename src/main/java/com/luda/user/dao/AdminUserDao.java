package com.luda.user.dao;

import com.luda.inventory.model.CommonQueryBean;
import com.luda.user.model.AdminRoleModel;
import com.luda.user.model.AdminUserDetailModel;
import com.luda.user.model.AdminUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 */
public interface AdminUserDao {
    AdminUserModel getWithDetailById(int adminUserId);

    AdminUserModel getByMobileNumber(String mobileNumber);

    AdminUserModel getByAdminName(String adminName);

    List<AdminUserModel> fetchAdminUserListWithDetail(CommonQueryBean queryBean);

    AdminUserDetailModel getAdminUserDetail(int adminUserId);

    int updateAdminUser(AdminUserModel adminUserModel);

    void updateAdminUserDetail(AdminUserDetailModel adminUserDetailModel);

    String getMaxAdminCode();

    int saveAdminUser(AdminUserModel adminUserModel);

    int saveAdminUserDetail(AdminUserDetailModel adminUserDetailModel);

    int removeAdminUser(int adminUserId);

    int updatePwd(@Param("adminUserId") int adminUserId, @Param("password") String password);

    List<AdminRoleModel> getAdminRoleList();

    AdminUserModel getByStaffid(String staffid);
}
