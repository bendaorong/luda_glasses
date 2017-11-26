package com.luda.test.user.service;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.test.SpringSimpleJunit;
import com.luda.user.model.AdminRoleModel;
import com.luda.user.model.AdminUserDetailModel;
import com.luda.user.model.AdminUserModel;
import com.luda.user.service.AdminUserService;
import com.luda.util.CommonUtils;
import com.luda.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/10/3.
 */
public class AdminUserServiceTest extends SpringSimpleJunit{
    @Autowired
    private AdminUserService adminUserService;

    @Test
    public void testGetByMobileNumber(){
        String mobileNumber = "13402176163";
        AdminUserModel adminUserModel = adminUserService.getByMobileNumber(mobileNumber);
        print(adminUserModel.toString());
    }

    @Test
    public void testFetchAdminUserListWithDetail(){
        List<AdminUserModel> userList = adminUserService.fetchAdminUserListWithDetail(new CommonQueryBean());
        print(CommonUtils.convertBeanCollectionToJsonArray(userList, "yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    public void testGetAdminUserDetail(){
        AdminUserDetailModel detailModel = adminUserService.getAdminUserDetail(1);
        print(detailModel.toString());
    }

    @Test
    public void testGetWithDetailById(){
        AdminUserModel adminUserModel = adminUserService.getWithDetailById(1);
        print(adminUserModel.toString());
    }

    @Test
    public void testUpdateAdminUser(){
        AdminUserModel adminUserModel = adminUserService.getWithDetailById(1);
        adminUserModel.setActiveFlag(1);
        adminUserModel.getAdminUserDetailModel().setAddress("苏州市吴中区幸福路2号");
        adminUserService.updateAdminUser(adminUserModel);
    }

    @Test
    public void testSaveAdminUser(){
        AdminUserModel adminUserModel = new AdminUserModel();
        adminUserModel.setAdminName("liubei");
        adminUserModel.setStoreId(1);
        adminUserModel.setMobileNumber("13477777777");
        adminUserModel.setActiveFlag(1);

        AdminUserDetailModel detailModel = new AdminUserDetailModel();
        detailModel.setGender("M");
        detailModel.setRealname("刘备");
        detailModel.setEmail("liubei@163.com");
        detailModel.setIdNo("3333333333");
        detailModel.setNativePlace("河东");
        detailModel.setAddress("未知");
        adminUserModel.setAdminUserDetailModel(detailModel);

        ResultHandle<AdminUserModel> resultHandle = adminUserService.saveAdminUser(adminUserModel);
        print("msg:"+resultHandle.getMsg());
    }

    @Test
    public void testRemoveAdminUser(){
        int adminUserId = 2;
        ResultHandle resultHandle = adminUserService.removeAdminUser(adminUserId);
        print(resultHandle.getMsg());
    }

    @Test
    public void testResetPwd(){
        int adminUserId = 2;
        ResultHandle<String> resultHandle = adminUserService.resetPwd(adminUserId);
        print("新密码:"+resultHandle.getReturnContent());
    }

    @Test
    public void testGetAdminRoleList(){
        List<AdminRoleModel> list = adminUserService.getAdminRoleList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list, "yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    public void testJsonTranfer(){
        AdminUserModel adminUserModel = adminUserService.getWithDetailById(1);
        String json = JsonUtil.toJson(adminUserModel);
        print(json);
        AdminUserModel model = JsonUtil.fromJson(json, AdminUserModel.class);
        print(model.toString());
    }
}
