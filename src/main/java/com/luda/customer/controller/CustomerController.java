package com.luda.customer.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.service.CustomerService;
import com.luda.store.model.StoreModel;
import com.luda.customer.service.CustomerService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
@Controller
@RequestMapping("/rest/customer")
public class CustomerController extends BaseController {
    private static final Logger log = Logger.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * 门店列表
     * @param httpServletResponse
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchCustomerList(HttpServletResponse httpServletResponse){
        String result;
        try {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            List<CustomerModel> customerList = customerService.fetchCustomerList();
            JSONArray jsonArray =  CommonUtils.convertBeanCollectionToJsonArray(customerList, "yyyy-MM-dd HH:mm:ss");
            result = jsonArray.toString();
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("fetchCustomerList error", e);
        }
        return result;
}

    /**
     * 新增门店
     */
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @ResponseBody
    public String addCustomer(@RequestBody CustomerModel customerModel, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<CustomerModel> resultHandle = customerService.saveCustomer(customerModel);
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
    @RequestMapping(value = "/getById/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@PathVariable int customerId, HttpServletResponse httpServletResponse){
        String result;
        try {
            CustomerModel customerModel = customerService.getById(customerId);
            result = CommonUtils.convertBeanToJson(customerModel, "yyyy-MM-dd HH:mm:ss").toString();
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("get by id error, customerid:"+ customerId, e);
        }
        return result;
    }

    /**
     * 修改门店信息
     */
    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    @ResponseBody
    public String updateCustomer(@RequestBody CustomerModel customerModel, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<CustomerModel> resultHandle = customerService.updateCustomer(customerModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("update store error, storeId:" + customerModel.getId(), e);
        }
        return result;
    }

    /**
     * 删除门店
     */
    @RequestMapping(value = "/removeCustomer/{customerId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeCustomer(@PathVariable int customerId, HttpServletResponse httpServletResponse){
        String result = "";
        try {
            ResultHandle<CustomerModel> resultHandle = customerService.removeCustomer(customerId);
            if(!resultHandle.isSuccess()){
                result = returnErrorResult(httpServletResponse, resultHandle.getMsg());
            }
        }catch (Exception e){
            result = returnErrorResult(httpServletResponse, "系统异常");
            log.error("remove store error, storeId:" + customerId, e);
        }
        return result;
    }
}