package com.luda.common.controller;

import com.luda.comm.po.Constants;
import com.luda.user.model.AdminUserModel;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/5.
 */
public class BaseController {
    /**
     * 返回错误结果
     * @param httpServletResponse
     * @param errorMsg
     * @return
     */
    public String returnErrorResult(HttpServletResponse httpServletResponse, String errorMsg){
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        JSONObject result = new JSONObject();
        result.put("errorMsg", errorMsg);
        return result.toString();
    }

    /**
     * 生成失败返回信息
     * @param msg
     * @return
     */
    public String getFailResult(String msg) {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", false);
        jsonobj.put("errorMsg", msg);
        return jsonobj.toString();
    }

    /**
     * 生成成功返回信息
     * @return
     */
    public String getSuccessResult() {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", true);
        return jsonobj.toString();
    }

    /**
     * 生成成功返回信息(带返回数据的)
     * @param data 返回数据，为json或者jsonarray
     */
    public String getSuccessResult(Object data){
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("success", true);
        jsonobj.put("data", data);
        return jsonobj.toString();
    }

    /**
     * 获取当前登录用户信息
     */
    public AdminUserModel getLoginUser(HttpSession session){
        AdminUserModel adminUserModel = (AdminUserModel) session.getAttribute("sessionInfo");
        return adminUserModel;
    }

    /**
     * 判断当前登录用户是否为总经理
     * @return
     */
    public boolean isSuperManage(HttpSession session){
        return Constants.ROLE_SUPERMANAGER.equals(getLoginUser(session).getAdminRoleModel().getRoleCode());
    }

    /**
     * 获取当前登录用户所属门店
     */
    public int getStoreId(HttpSession session){
        return getLoginUser(session).getStoreId();
    }

    public int getStartIndex(int pageNo, int pageSize){
        return (pageNo - 1) * pageSize;
    }
}
