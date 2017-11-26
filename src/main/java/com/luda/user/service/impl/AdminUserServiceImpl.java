package com.luda.user.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.user.dao.AdminUserDao;
import com.luda.user.model.AdminRoleModel;
import com.luda.user.model.AdminUserDetailModel;
import com.luda.user.model.AdminUserModel;
import com.luda.user.service.AdminUserService;
import com.luda.util.CommonUtils;
import com.luda.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
    // 缓存当前最大用户编号
    private static String CURRENT__CODE = null;

    @Autowired
    private AdminUserDao adminUserDao;

    public AdminUserModel getByMobileNumber(String mobileNumber) {
        return adminUserDao.getByMobileNumber(mobileNumber);
    }

    public AdminUserModel getByAdminName(String adminName){
        return adminUserDao.getByAdminName(adminName);
    }

    @Override
    public ResultHandle<AdminUserModel> login(AdminUserModel loginModel) {
        ResultHandle<AdminUserModel> resultHandle = new ResultHandle<>();

        // 验证登录信息
        if(StringUtils.isEmpty(loginModel.getMobileNumber())
                && StringUtils.isEmpty(loginModel.getAdminName())){
            resultHandle.setMsg("请输入用户名或手机号");
            return resultHandle;
        }
        if(StringUtils.isEmpty(loginModel.getLoginPassword())){
            resultHandle.setMsg("请输入密码");
            return resultHandle;
        }

        AdminUserModel adminUserModel = null;
        // 手机号登录
        if(StringUtils.isNotEmpty(loginModel.getMobileNumber())){
            adminUserModel = getByMobileNumber(loginModel.getMobileNumber());
            if(adminUserModel == null){
                resultHandle.setMsg("手机号不存在");
                return resultHandle;
            }
        }

        // 用户名登录
        if(StringUtils.isNotEmpty(loginModel.getAdminName())){
            adminUserModel = getByAdminName(loginModel.getAdminName());
            if(adminUserModel == null){
                resultHandle.setMsg("用户名不存在");
                return resultHandle;
            }
        }

        // 验证密码
        if(!adminUserModel.getLoginPassword().equals(MD5.getMD5(loginModel.getLoginPassword()))){
            resultHandle.setMsg("密码不正确");
            return resultHandle;
        }

        resultHandle.setReturnContent(adminUserModel);
        return resultHandle;
    }

    @Override
    public List<AdminUserModel> fetchAdminUserListWithDetail(CommonQueryBean queryBean) {
        return adminUserDao.fetchAdminUserListWithDetail(queryBean);
    }

    @Override
    public AdminUserModel getWithDetailById(int adminUserId) {
        return adminUserDao.getWithDetailById(adminUserId);
    }

    @Override
    public AdminUserDetailModel getAdminUserDetail(int adminUserId) {
        return adminUserDao.getAdminUserDetail(adminUserId);
    }

    @Override
    public ResultHandle<AdminUserModel> updateAdminUser(AdminUserModel adminUserModel) {
        ResultHandle<AdminUserModel> resultHandle = new ResultHandle<>();

        String errorMsg = checkUpdateAdminUser(adminUserModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        adminUserDao.updateAdminUser(adminUserModel);
        adminUserDao.updateAdminUserDetail(adminUserModel.getAdminUserDetailModel());

        resultHandle.setReturnContent(adminUserModel);
        return resultHandle;
    }

    /**
     * 验证更新用户信息
     * @param adminUserModel
     * @return
     */
    private String checkUpdateAdminUser(AdminUserModel adminUserModel) {
        // 验证手机号
        if(StringUtils.isEmpty(adminUserModel.getMobileNumber())){
            return "请填写手机号码";
        }
        if(!CommonUtils.isMobileNumber(adminUserModel.getMobileNumber())){
            return "手机号码不合法";
        }
        AdminUserModel checkModel = getByMobileNumber(adminUserModel.getMobileNumber());
        if(checkModel != null && checkModel.getAdminUserId() != adminUserModel.getAdminUserId()){
            return "该手机号码已被占用";
        }
        // 姓名
        if(StringUtils.isEmpty(adminUserModel.getAdminUserDetailModel().getRealname())){
            return "请填写姓名";
        }
        // 验证门店
        if(adminUserModel.getStoreId() <= 0){
            return "请选择门店";
        }
        // 角色
        if(adminUserModel.getRoleId() <= 0){
            return "请选择角色";
        }
        // 邮箱
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getEmail())){
            if(!CommonUtils.isEmail(adminUserModel.getAdminUserDetailModel().getEmail())){
                return "邮箱不合法";
            }
        }
        // 邮政编码
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getPostcode())){
            if(!CommonUtils.isPostcode(adminUserModel.getAdminUserDetailModel().getPostcode())){
                return "邮政编码不合法";
            }
        }
        // 身份证
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getIdNo())){
            if(!CommonUtils.isIdNumber(adminUserModel.getAdminUserDetailModel().getIdNo())){
                return "身份证不合法";
            }
        }
        return null;
    }

    @Override
    public ResultHandle<AdminUserModel> saveAdminUser(AdminUserModel adminUserModel) {
        ResultHandle<AdminUserModel> resultHandle = new ResultHandle<>();

        // 验证用户信息
        String errorMsg = checkAddAdminUser(adminUserModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化用户信息
        initAddAdminUser(adminUserModel);
        // 保存用户信息
        adminUserDao.saveAdminUser(adminUserModel);
        // 保存用户详情信息
        adminUserModel.getAdminUserDetailModel().setAdminUserId(adminUserModel.getAdminUserId());
        adminUserDao.saveAdminUserDetail(adminUserModel.getAdminUserDetailModel());

        resultHandle.setReturnContent(adminUserModel);
        return resultHandle;
    }

    @Override
    public ResultHandle<AdminUserModel> removeAdminUser(int adminUserId) {
        ResultHandle resultHandle = new ResultHandle();
        int result = adminUserDao.removeAdminUser(adminUserId);
        return resultHandle;
    }

    @Override
    public ResultHandle<String> resetPwd(int adminUserId) {
        ResultHandle<String> resultHandle = new ResultHandle<>();

        AdminUserModel adminUserModel = getWithDetailById(adminUserId);
        if(adminUserModel == null){
            resultHandle.setMsg("该用户不存在");
            return resultHandle;
        }

        // 密码重设为手机号
        int result = adminUserDao.updatePwd(adminUserId, MD5.getMD5(adminUserModel.getMobileNumber()));

        if(result <= 0){
            resultHandle.setMsg("重设密码失败");
        }

        // 返回新密码(手机号)
        resultHandle.setReturnContent(adminUserModel.getMobileNumber());

        return resultHandle;
    }

    @Override
    public List<AdminRoleModel> getAdminRoleList() {
        return adminUserDao.getAdminRoleList();
    }

    /**
     * 初始化创建用户信息
     * @param adminUserModel
     */
    private void initAddAdminUser(AdminUserModel adminUserModel) {
        //用户编号
        adminUserModel.setAdminCode(getNextAdminCode());
        //用户密码(初始密码为手机号码)
        adminUserModel.setLoginPassword(MD5.getMD5(adminUserModel.getMobileNumber()));

        AdminUserDetailModel detailModel = adminUserModel.getAdminUserDetailModel();
        // 性别
        if(StringUtils.isEmpty(detailModel.getGender())){
            detailModel.setGender(Constants.GENDER_MALE);
        }
    }

    /**
     * 获取用户编号
     * @return
     */
    private synchronized String getNextAdminCode() {
        String currentAdminCode = adminUserDao.getMaxAdminCode();
        // 默认从0001开始计数
        if(StringUtils.isEmpty(currentAdminCode)){
            return "0001";
        }

        // 新编号=当前编号+1
        int adminCode = Integer.valueOf(currentAdminCode);
        return String.format("%04d", adminCode + 1);
    }

    /**
     * 验证创建用户信息
     * @param adminUserModel
     * @return
     */
    private String checkAddAdminUser(AdminUserModel adminUserModel) {
        // 验证用户名
        if(StringUtils.isEmpty(adminUserModel.getAdminName())){
            return "请填写用户名";
        }
        AdminUserModel checkModel = getByAdminName(adminUserModel.getAdminName());
        if(checkModel != null){
            return "该用户名已被占用";
        }
        // 验证手机号
        if(StringUtils.isEmpty(adminUserModel.getMobileNumber())){
            return "请填写手机号码";
        }
        if(!CommonUtils.isMobileNumber(adminUserModel.getMobileNumber())){
            return "手机号码不合法";
        }
        checkModel = getByMobileNumber(adminUserModel.getMobileNumber());
        if(checkModel != null){
            return "该手机号码已被占用";
        }
        // 验证门店
        if(adminUserModel.getStoreId() <= 0){
            return "请选择门店";
        }
        // 验证角色
        if(adminUserModel.getRoleId() <= 0){
            return "请选择角色";
        }
        // 工号
        if(StringUtils.isEmpty(adminUserModel.getStaffid())){
            return "请填写工号";
        }
        checkModel = getByStaffid(adminUserModel.getStaffid());
        if(checkModel != null){
            return "该工号已被占用";
        }
        // 姓名
        if(StringUtils.isEmpty(adminUserModel.getAdminUserDetailModel().getRealname())){
            return "请填写姓名";
        }
        // 邮箱
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getEmail())){
            if(!CommonUtils.isEmail(adminUserModel.getAdminUserDetailModel().getEmail())){
                return "邮箱不合法";
            }
        }
        // 邮政编码
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getPostcode())){
            if(!CommonUtils.isPostcode(adminUserModel.getAdminUserDetailModel().getPostcode())){
                return "邮政编码不合法";
            }
        }
        // 身份证
        if(StringUtils.isNotEmpty(adminUserModel.getAdminUserDetailModel().getIdNo())){
            if(!CommonUtils.isIdNumber(adminUserModel.getAdminUserDetailModel().getIdNo())){
                return "身份证不合法";
            }
        }
        return null;
    }

    private AdminUserModel getByStaffid(String staffid) {
        return adminUserDao.getByStaffid(staffid);
    }
}