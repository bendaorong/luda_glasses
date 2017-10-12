package com.luda.common.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
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
}
