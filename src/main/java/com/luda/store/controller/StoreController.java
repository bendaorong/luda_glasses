package com.luda.store.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.store.model.StoreModel;
import com.luda.store.service.StoreService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 门店管理
 * Created by Administrator on 2017/10/5.
 */
@Controller
@RequestMapping("/rest/store")
public class StoreController extends BaseController {
    private static final Logger log = Logger.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    /**
     * 门店列表
     * @param httpServletResponse
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchStoreList(HttpServletResponse httpServletResponse){
        String result;
        try {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            List<StoreModel> storeList = storeService.fetchStoreList();
            JSONArray jsonArray =  CommonUtils.convertBeanCollectionToJsonArray(storeList, "yyyy-MM-dd HH:mm:ss");
            result = jsonArray.toString();
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("fetchStoreList error", e);
        }
        return result;
    }

    /**
     * 新增门店
     */
    @RequestMapping(value = "/addStore", method = RequestMethod.POST)
    @ResponseBody
    public String addStore(@RequestBody StoreModel storeModel, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<StoreModel> resultHandle = storeService.saveStore(storeModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else{
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("add store error", e);
        }
        return result;
    }

    /**
     * 根据门店id查询门店信息
     */
    @RequestMapping(value = "/getById/{storeId}", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@PathVariable int storeId, HttpServletResponse httpServletResponse){
        String result;
        try {
            StoreModel storeModel = storeService.getById(storeId);
            result = CommonUtils.convertBeanToJson(storeModel, "yyyy-MM-dd HH:mm:ss").toString();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("get by id error, storeId:"+ storeId, e);
        }
        return result;
    }

    /**
     * 修改门店信息
     */
    @RequestMapping(value = "/updateStore", method = RequestMethod.POST)
    @ResponseBody
    public String updateStore(@RequestBody StoreModel storeModel, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<StoreModel> resultHandle = storeService.updateStore(storeModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("update store error, storeId:" + storeModel.getStoreId(), e);
        }
        return result;
    }

    /**
     * 删除门店
     */
    @RequestMapping(value = "/removeStore/{storeId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeStore(@PathVariable int storeId, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<StoreModel> resultHandle = storeService.removeStore(storeId);
            if(!resultHandle.isSuccess()){
                result = returnErrorResult(httpServletResponse, resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("remove store error, storeId:" + storeId, e);
        }
        return result;
    }
}
