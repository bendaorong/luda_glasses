package com.luda.supplier.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
     * 查询可用供应商列表
     */
    @RequestMapping("/listUseable")
    @ResponseBody
    public String fetchUseableSupplierList(){
        String result;
        try {
            List<SupplierModel> supplierList = supplierService.fetchUseableSupplierList();
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

    /**
     * 启用供应商
     * @return
     */
    @RequestMapping(value = "/enableSupplier/{supplierId}", method = RequestMethod.POST)
    @ResponseBody
    public String enableSupplier(@PathVariable int supplierId){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.enableSupplier(supplierId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        } catch (Exception e){
            result = getFailResult("系统异常");
            log.error("enable supplier error, supplierId:" + supplierId, e);
        }
        return result;
    }

    /**
     * 启用供应商
     * @return
     */
    @RequestMapping(value = "/disableSupplier/{supplierId}", method = RequestMethod.POST)
    @ResponseBody
    public String disableSupplier(@PathVariable int supplierId){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.disableSupplier(supplierId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        } catch (Exception e){
            result = getFailResult("系统异常");
            log.error("enable supplier error, supplierId:" + supplierId, e);
        }
        return result;
    }

    /**
     * 删除供应商
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/removeSupplier/{supplierId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeSupplier(@PathVariable int supplierId){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.removeSupplier(supplierId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("remove supplier error, supplierId:" + supplierId, e);
        }
        return result;
    }

    /**
     * 查询供应商联系人
     */
    @RequestMapping("/contactList/{supplierId}")
    @ResponseBody
    public String contactList(@PathVariable int supplierId){
        String result = "";
        try {
            List<SupplierContactModel> supplierContactList = supplierService.getSupplierContactBySupplierId(supplierId);
            JSONArray arr = CommonUtils.convertBeanCollectionToJsonArray(supplierContactList, "yyyy-MM-dd HH:mm:ss");
            result = getSuccessResult(arr);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("fetch supplier contact list error, supplierId:" + supplierId, e);
        }
        return result;
    }

    /**
     * 联系人详情
     */
    @RequestMapping(value = "/getContactById/{contactId}")
    @ResponseBody
    public String getContactById(@PathVariable int contactId){
        String result = "";
        try {
            SupplierContactModel supplierContactModel = supplierService.getSupplierContactById(contactId);
            String data = CommonUtils.convertBeanToJson(supplierContactModel,null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get contact by id, contactId:" + contactId, e);
        }
        return result;
    }

    /**
     * 更新供应商联系人
     * @param supplierContactModel
     * @return
     */
    @RequestMapping(value = "/updateContact", method = RequestMethod.POST)
    @ResponseBody
    public String updateContact(@RequestBody SupplierContactModel supplierContactModel){
        String result = "";
        try {
            ResultHandle<SupplierContactModel> resultHandle = supplierService.updateSupplierContact(supplierContactModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update supplier contact error, contactId:" + supplierContactModel.getContactId(), e);
        }
        return result;
    }

    /**
     * 保存供应商联系人
     */
    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    @ResponseBody
    public String saveContact(@RequestBody SupplierContactModel supplierContactModel){
        String result = "";
        try {
            ResultHandle resultHandle = supplierService.saveSupplierContact(supplierContactModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult(supplierContactModel);
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("save contact error, supplierId:" + supplierContactModel.getSupplierId(), e);
        }
        return result;
    }

    /**
     * 删除联系人
     */
    @RequestMapping(value = "/removeContact/{contactId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeContact(@PathVariable int contactId){
        String result = "";
        try {
            ResultHandle<SupplierModel> resultHandle = supplierService.removeContact(contactId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("remove supplier contact error, contactId:" + contactId, e);
        }
        return result;
    }
}
