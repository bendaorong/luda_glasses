package com.luda.sales.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.sales.model.*;
import com.luda.sales.service.SalesService;
import com.luda.user.model.AdminUserModel;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/28.
 */
@Controller
@RequestMapping("/rest/sales")
public class SalesController extends BaseController{
    private static Logger log = Logger.getLogger(SalesController.class);

    @Autowired
    private SalesService salesService;

    /**
     * 查询销售单列表
     * @param orderType 订单类型 01:销售单 02:退货单
     */
    @RequestMapping("/fetchSalesOrderVoList/{orderType}")
    @ResponseBody
    public String fetchSalesOrderVoList(@PathVariable String orderType, HttpSession httpSession){
        String result = "";
        try {
            SalesOrderQueryBean queryBean = new SalesOrderQueryBean();
            queryBean.setOrderType(orderType);
            if(!isSuperManage(httpSession)){
                queryBean.setStoreId(getStoreId(httpSession));
            }

            List<SalesOrderVo> list = salesService.fetchSalesOrderVoList(queryBean);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("fetchSalesOrderVoList error", e);
        }
        return result;
    }

    /**
     * 查询客户销售记录
     * @param customerId 客户id
     */
    @RequestMapping("/fetchCustomerSalesOrderVoList/{customerId}")
    @ResponseBody
    public String fetchCustomerSalesOrderVoList(@PathVariable int customerId){
        String result = "";
        try {
            List<SalesOrderVo> list = salesService.fetchSalesOrderVoByCustomerId(customerId);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("fetchSalesOrderVoList error", e);
        }
        return result;
    }

    /**
     * 新增销售单/新增退货单
     * 通过orderType区分
     * @param data
     * @return
     */
    @RequestMapping("/saveSalesOrder")
    @ResponseBody
    public String saveSalesOrder(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            JSONObject jsonData = JSONObject.fromObject(data);
            pretreatment(jsonData);


            Map classMap = new HashMap();
            classMap.put("salesOrderItems", SalesOrderItem.class);
            SalesOrder salesOrder = CommonUtils.convertJsonToBean_(jsonData, SalesOrder.class, classMap);

            AdminUserModel loginUser = getLoginUser(httpSession);
            salesOrder.setCreateUserId(loginUser.getAdminUserId());
            salesOrder.setUpdateUserId(loginUser.getAdminUserId());

            ResultHandle<SalesOrder> resultHandle = salesService.saveSalesOrder(salesOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save sales order error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 创建销售单对页面数据预先处理，日期类型的字段如果为空则删除掉
     * @param jsonData
     */
    private void pretreatment(JSONObject jsonData) {
        // 销售日期
        Object saleDate = jsonData.get("saleDate");
        if(saleDate == null || StringUtils.isEmpty(saleDate.toString())){
            jsonData.remove("saleDate");
        }

        // 提货日期
        Object pickupDate = jsonData.get("pickupDate");
        if(pickupDate == null || StringUtils.isEmpty(pickupDate.toString())){
            jsonData.remove("pickupDate");
        }
    }

    /**
     * 查询销售单及销售明细
     * @param id 销售单id
     * @return
     */
    @RequestMapping("/getSalesOrderWithItemsById/{id}")
    @ResponseBody
    public String getSalesOrderWithItemsById(@PathVariable int id){
        String result = "";
        try {
            SalesOrder salesOrder = salesService.getSalesOrderWithItemsById(id);
            result = getSuccessResult(CommonUtils.convertBeanToJson(salesOrder, null).toString());
        }catch (Exception e){
            log.error("getSalesOrderWithItemsById error, id:" + id);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 更新销售单
     * (更新时只更新销售单信息，明细信息单独更新)
     * @param data
     * @return
     */
    @RequestMapping("/updateSalesOrder")
    @ResponseBody
    public String updateSalesOrder(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            JSONObject jsonData = JSONObject.fromObject(data);
            Map classMap = new HashMap();
            classMap.put("salesOrderItems", SalesOrderItem.class);
            SalesOrder salesOrder = CommonUtils.convertJsonToBean_(jsonData, SalesOrder.class, classMap);

            AdminUserModel loginUser = getLoginUser(httpSession);
            salesOrder.setUpdateUserId(loginUser.getAdminUserId());

            ResultHandle<SalesOrder> resultHandle = salesService.updateSalesOrder(salesOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("updateSalesOrder error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除采购明细
     * @return
     */
    @RequestMapping("/removeSalesOrderItem/{itemId}")
    @ResponseBody
    public String removeSalesOrderItem(@PathVariable int itemId){
        String result = "";
        try {
            ResultHandle<SalesOrderItem> resultHandle = salesService.removeSalesOrderItem(itemId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removeSalesOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 添加销售明细
     */
    @RequestMapping("/saveSalesOrderItem")
    @ResponseBody
    public String saveSalesOrderItem(@RequestBody SalesOrderItem salesOrderItem){
        String result = "";
        try {
            ResultHandle<SalesOrderItem> resultHandle = salesService.saveSalesOrderItem(salesOrderItem);
            if(resultHandle.isSuccess()){
                result = getSuccessResult(resultHandle.getReturnContent());
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("saveSalesOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除销售单
     * @param id 销售单id
     */
    @RequestMapping("/removeSalesOrder/{id}")
    @ResponseBody
    public String removeSalesOrder(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<SalesOrder> resultHandle = salesService.removeSalesOrder(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removeSalesOrder error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 销售单退货
     * @param id
     * @return
     */
    @RequestMapping("/orderRefund/{id}")
    @ResponseBody
    public String orderRefund(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<SalesOrder> resultHandle = salesService.orderRefund(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("orderRefund error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
