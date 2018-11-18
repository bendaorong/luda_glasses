package com.luda.user.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.user.exception.AdminUserException;
import com.luda.user.model.AdminRoleModel;
import com.luda.user.model.AdminUserDetailModel;
import com.luda.user.model.AdminUserModel;
import com.luda.user.model.UpdatePasswordModel;
import com.luda.user.service.AdminUserService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/3.
 */
@Controller
@RequestMapping("/rest/adminUser")
public class AdminUserController extends BaseController{
    private Logger log = Logger.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 分页查询员工信息
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchAdminUserList(HttpServletResponse httpServletResponse){
        String result;
        try {
            List<AdminUserModel> adminUserList = adminUserService.fetchAdminUserListWithDetail(new CommonQueryBean());
            JSONArray jsonArray = CommonUtils.convertBeanCollectionToJsonArray(adminUserList, "yyyy-MM-dd");
            result =  jsonArray.toString();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } catch (AdminUserException e) {
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("fetchAdminUserList error", e);
        }
        return result;
    }

    /**
     * 查询门店业务员
     */
    @RequestMapping("/listByStore")
    @ResponseBody
    public String fetchAdminUserByStore(HttpServletResponse httpServletResponse, HttpSession httpSession){
        String result;
        try {
            CommonQueryBean queryBean = new CommonQueryBean();
            if(!isSuperManage(httpSession)){
                queryBean.setStoreId(getStoreId(httpSession));
            }
            List<AdminUserModel> adminUserList = adminUserService.fetchAdminUserListWithDetail(queryBean);
            JSONArray jsonArray = CommonUtils.convertBeanCollectionToJsonArray(adminUserList, "yyyy-MM-dd");
            result =  jsonArray.toString();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } catch (AdminUserException e) {
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("fetchAdminUserList error", e);
        }
        return result;
    }

    /**
     * 新增用户
     */
    @RequestMapping(value = "/addAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public String addAdminUser(@RequestBody String data, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            JSONObject adminUserObj = CommonUtils.jsonFilterNull(JSONObject.fromObject(data));
            Map classMap = new HashMap();
            classMap.put("adminUserDetailModel", AdminUserDetailModel.class);
            AdminUserModel adminUserModel =
                    CommonUtils.convertJsonToBean_(adminUserObj, AdminUserModel.class, classMap);

            ResultHandle<AdminUserModel> resultHandle = adminUserService.saveAdminUser(adminUserModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
                log.info("add admin user fail, result:" + resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("add admin user error", e);
        }
        return result;
    }

    /**
     * 根据用户Id查询用户
     */
    @RequestMapping(value = "/{adminUserId}", method = RequestMethod.GET)
    @ResponseBody
    public String getWithDetailById(@PathVariable int adminUserId, HttpServletResponse httpServletResponse) {
        String result;
        try {
            AdminUserModel adminUserModel = adminUserService.getWithDetailById(adminUserId);
            result  = CommonUtils.convertBeanToJson(adminUserModel, "yyyy-MM-dd HH:mm:ss").toString();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } catch (AdminUserException e) {
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("getWithDetailById error, adminUserId:" + adminUserId, e);
        }
        return result;
    }

    @RequestMapping(value = "/updateAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateAdminUser(@RequestBody String data, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            JSONObject adminUserObj = CommonUtils.jsonFilterNull(JSONObject.fromObject(data));
            Map classMap = new HashMap();
            classMap.put("adminUserDetailModel", AdminUserDetailModel.class);
            AdminUserModel adminUserModel =
                    CommonUtils.convertJsonToBean_(adminUserObj, AdminUserModel.class, classMap);

            ResultHandle<AdminUserModel> resultHandle = adminUserService.updateAdminUser(adminUserModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("updateAdminUser error", e);
        }
        return result;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/removeAdminUser/{adminUserId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeAdminUser(@PathVariable int adminUserId, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<AdminUserModel> resultHandle = adminUserService.removeAdminUser(adminUserId);
            if(resultHandle.isSuccess()){
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }else {
                result = returnErrorResult(httpServletResponse, resultHandle.getMsg());
                log.info("remove admin user fail, adminUserId:" + adminUserId + " msg:" + resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("remove admin user error, adminUserId:"+adminUserId);
        }
        return result;
    }

    /**
     * 重设密码
     */
    @RequestMapping(value = "/resetPwd/{adminUserId}", method = RequestMethod.POST)
    @ResponseBody
    public String resetPwd(@PathVariable int adminUserId){
        String result = "";
        try {
            ResultHandle<String> resultHandle = adminUserService.resetPwd(adminUserId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("reset pwd error, adminUserId:" + adminUserId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 修改密码
     * @param updatePasswordModel
     * @return
     */
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    @ResponseBody
    public String modifyPwd(@RequestBody UpdatePasswordModel updatePasswordModel, HttpSession httpSession){
        String result = "";
        AdminUserModel adminUser = getLoginUser(httpSession);
        try {
            updatePasswordModel.setAdminUserId(adminUser.getAdminUserId());
            ResultHandle<String> resultHandle = adminUserService.updatePwd(updatePasswordModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update pwd error, adminUserId:" + adminUser.getAdminUserId(), e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping(value = "/roleList", method = RequestMethod.GET)
    @ResponseBody
    public String roleList(){
        String result = "";
        try {
            List<AdminRoleModel> roleList = adminUserService.getAdminRoleList();
            JSONArray roleArr = CommonUtils.convertBeanCollectionToJsonArray(roleList, "yyyy-MM-dd HH:mm:ss");
            result = roleArr.toString();
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get role list error", e);
        }
        return result;
    }
}
