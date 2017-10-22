package com.luda.inventory.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.inventory.model.MardVo;
import com.luda.inventory.model.PurchaseOrder;
import com.luda.inventory.model.PurchaseOrderItem;
import com.luda.inventory.model.PurchaseOrderVo;
import com.luda.inventory.service.InventoryService;
import com.luda.util.CommonUtils;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    /**
     * 查询采购单详情
     */
    @RequestMapping("/purchaseOrder/getById/{id}")
    @ResponseBody
    public String getPurchaseOrderById(@PathVariable int id){
        String result = "";
        try {
            PurchaseOrder purchaseOrder = inventoryService.getPurchaseOrderById(id);
            String data = CommonUtils.convertBeanToJson(purchaseOrder, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("getPurchaseOrderById error, id:" + id, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/purchaseOrder/savePurchaseOrder")
    @ResponseBody
    public String savePurchaseOrder(@RequestBody String data){
        String result = "";
        try {
            // 采购单
            JSONObject purchaseOrderJson = JSONObject.fromObject(data);
            // 采购单明细
            JSONArray itemArr = purchaseOrderJson.getJSONArray("purchaseOrderItemList");

            purchaseOrderJson.remove("purchaseOrderItemList");
            PurchaseOrder purchaseOrder = CommonUtils.convertJsonToBean(purchaseOrderJson, PurchaseOrder.class);

            List<PurchaseOrderItem> itemList = new ArrayList<>();
            for(int i=0; i<itemArr.size(); i++){
                JSONObject itemJson = itemArr.getJSONObject(i);
                PurchaseOrderItem item = CommonUtils.convertJsonToBean(itemJson, PurchaseOrderItem.class);
                itemList.add(item);
            }
            purchaseOrder.setPurchaseOrderItemList(itemList);

            // 保存采购单和采购明细
            ResultHandle resultHandle = inventoryService.savePurchaseOrder(purchaseOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save purchase order error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
