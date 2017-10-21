package com.luda.inventory.controller;

import com.luda.common.controller.BaseController;
import com.luda.inventory.model.MardVo;
import com.luda.inventory.model.PurchaseOrderVo;
import com.luda.inventory.service.InventoryService;
import com.luda.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 库存管理
 * Created by Administrator on 2017/10/21.
 */
@Controller
@RequestMapping("/rest/inventory")
public class InventoryController extends BaseController{
    private static final Logger log = Logger.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    /**
     * 查询商品库存
     */
    @RequestMapping("/mard/list")
    @ResponseBody
    public String fetchMardList(){
        String result = "";
        try {
            List<MardVo> mardVoList = inventoryService.fetchMardVoList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(mardVoList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch mard list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 查询采购单
     */
    @RequestMapping("/purchaseOrder/list")
    @ResponseBody
    public String fetchPurchaseOrderList(){
        String result = "";
        try {
            List<PurchaseOrderVo> purchaseOrderVoList = inventoryService.fetchPurchaseOrderVoList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(purchaseOrderVoList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch purchase order list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
