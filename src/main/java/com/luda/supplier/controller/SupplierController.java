package com.luda.supplier.controller;

import com.luda.common.controller.BaseController;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @RequestMapping("/fetchSupplierList")
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
}
