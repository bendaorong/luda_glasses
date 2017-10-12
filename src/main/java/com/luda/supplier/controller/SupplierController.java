package com.luda.supplier.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 供应商管理
 * Created by Administrator on 2017/10/10.
 */
@Controller
@RequestMapping("/rest/supplier")
public class SupplierController extends BaseController{
    private Logger log = Logger.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    /**
     * 查询供应商列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchSupplierList(){
        String result;
        try {
            List<SupplierModel> supplierList = supplierService.fetchSupplierList();
            JSONArray arr = CommonUtils.convertBeanCollectionToJsonArray(supplierList, "yyyy-MM-dd HH:mm:ss");
            result = getSuccessResult(arr);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("fetch supplier list error", e);
        }
        return result;
    }

    /**
     * 创建供应商
     */
    @RequestMapping("/addSupplier")
    @ResponseBody
    public String addSupplier(@RequestBody SupplierModel supplierModel){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.saveSupplier(supplierModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("add supplier error", e);
        }
        return result;
    }

    /**
     * 根据供应商id查询供应商详情
     */
    @RequestMapping("/getSupplierById/{supplierId}")
    @ResponseBody
    public String getSupplierById(@PathVariable int supplierId){
        String result = "";
        try {
            SupplierModel supplierModel = supplierService.getSupplierById(supplierId);
            String data = CommonUtils.convertBeanToJson(supplierModel, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get supplier by id error, supplierId:" + supplierId, e);
        }
        return result;
    }

    /**
     * 更新供应商信息
     */
    @RequestMapping("/updateSupplier")
    @ResponseBody
    public String updateSupplier(@RequestBody SupplierModel supplierModel){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.updateSupplier(supplierModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update supplier error, supplierId:" + supplierModel.getSupplierId(), e);
        }
        return result;
    }

}
