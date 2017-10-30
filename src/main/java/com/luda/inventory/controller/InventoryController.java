package com.luda.inventory.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.inventory.model.*;
import com.luda.inventory.service.InventoryService;
import com.luda.user.model.AdminUserModel;
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

import javax.servlet.http.HttpSession;
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
     * 查询业务员门店商品库存
     */
    @RequestMapping("/mard/list")
    @ResponseBody
    public String fetchMardList(HttpSession httpSession){
        String result = "";
        try {
            int storeId = getLoginUser(httpSession).getStoreId();
            List<MardVo> mardVoList = inventoryService.fetchMardVoList(storeId);
            String data = CommonUtils.convertBeanCollectionToJsonArray(mardVoList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch mard list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 查询商品库存
     */
    @RequestMapping("/mard/getMard/{materielId}/{storeId}")
    @ResponseBody
    public String getMard(@PathVariable int materielId, @PathVariable int storeId){
        String result = "";
        try {
            Mard mard = inventoryService.getMard(materielId, storeId);
            if(mard == null){
                result = getSuccessResult(0);
            }else{
                result = getSuccessResult(mard.getCurrentInventory());
            }
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

    /**
     * 保存采购单
     * @param data
     * @return
     */
    @RequestMapping("/purchaseOrder/savePurchaseOrder")
    @ResponseBody
    public String savePurchaseOrder(@RequestBody String data, HttpSession session){
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

            // 创建人&更新人
            AdminUserModel adminUserModel = getLoginUser(session);
            purchaseOrder.setCreateUserId(adminUserModel.getAdminUserId());
            purchaseOrder.setUpdateUserId(adminUserModel.getAdminUserId());

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

    @RequestMapping("/purchaseOrder/updatePurchaseOrder")
    @ResponseBody
    public String updatePurchaseOrder(@RequestBody String data, HttpSession session){
        String result = "";
        try {
            // 采购单
            JSONObject purchaseOrderJson = JSONObject.fromObject(data);
            // 采购单明细
            //JSONArray itemArr = purchaseOrderJson.getJSONArray("purchaseOrderItemList");

            purchaseOrderJson.remove("purchaseOrderItemList");
            PurchaseOrder purchaseOrder = CommonUtils.convertJsonToBean(purchaseOrderJson, PurchaseOrder.class);

//            List<PurchaseOrderItem> itemList = new ArrayList<>();
//            for(int i=0; i<itemArr.size(); i++){
//                JSONObject itemJson = itemArr.getJSONObject(i);
//                PurchaseOrderItem item = CommonUtils.convertJsonToBean(itemJson, PurchaseOrderItem.class);
//                itemList.add(item);
//            }
//            purchaseOrder.setPurchaseOrderItemList(itemList);

            // 更新人
            AdminUserModel adminUserModel = getLoginUser(session);
            purchaseOrder.setUpdateUserId(adminUserModel.getAdminUserId());

            // 保存采购单和采购明细
            ResultHandle resultHandle = inventoryService.updatePurchaseOrder(purchaseOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update purchase order error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除采购单
     * @param id
     * @return
     */
    @RequestMapping("/purchaseOrder/removePurchaseOrder/{id}")
    @ResponseBody
    public String removePurchaseOrder(@PathVariable int id){
        String result = "";
        try {
            ResultHandle resultHandle = inventoryService.removePurchaseOrder(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove purchase order error, id:"+id, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 新增采购明细
     * @return
     */
    @RequestMapping("/purchaseOrder/savePurchaseOrderItem")
    @ResponseBody
    public String savePurchaseOrderItem(@RequestBody PurchaseOrderItem purchaseOrderItem){
        String result = "";
        try {
            ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.savePurchaseOrderItem(purchaseOrderItem);
            if(resultHandle.isSuccess()){
                result = getSuccessResult(CommonUtils.convertBeanToJson(resultHandle.getReturnContent(), null));
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("savePurchaseOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 更新采购明细
     * @return
     */
    @RequestMapping("/purchaseOrder/updatePurchaseOrderItem")
    @ResponseBody
    public String updatePurchaseOrderItem(@RequestBody PurchaseOrderItem purchaseOrderItem){
        String result = "";
        try {
            ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.updatePurchaseOrderItem(purchaseOrderItem);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("updatePurchaseOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除采购明细
     * @return
     */
    @RequestMapping("/purchaseOrder/removePurchaseOrderItem/{itemId}")
    @ResponseBody
    public String removePurchaseOrderItem(@PathVariable int itemId){
        String result = "";
        try {
            ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.removePurchaseOrderItem(itemId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removePurchaseOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 查询盘点单列表
     */
    @RequestMapping("/inventoryVerification/list")
    @ResponseBody
    public String fetchInventoryVerificationList(){
        String result = "";
        try {
            List<InventoryVerificationVo> list = inventoryService.fetchInvntVerifVoList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(list, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch InventoryVerification order list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存库存盘点单
     */
    @RequestMapping("/inventoryVerification/saveInventoryVerification")
    @ResponseBody
    public String saveInventoryVerification(@RequestBody String data, HttpSession session){
        String result = "";
        try {
            // 盘点单
            JSONObject inventoryVerificationJson = JSONObject.fromObject(data);
            // 盘点明细
            JSONArray itemArr = inventoryVerificationJson.getJSONArray("invtVerifItemList");

            inventoryVerificationJson.remove("invtVerifItemList");
            InventoryVerification inventoryVerification = CommonUtils.convertJsonToBean(inventoryVerificationJson, InventoryVerification.class);

            List<InventoryVerificationItem> itemList = new ArrayList<>();
            for(int i=0; i<itemArr.size(); i++){
                JSONObject itemJson = itemArr.getJSONObject(i);
                InventoryVerificationItem item = CommonUtils.convertJsonToBean(itemJson, InventoryVerificationItem.class);
                itemList.add(item);
            }
            inventoryVerification.setInvtVerifItemList(itemList);

            // 创建人&更新人
            AdminUserModel adminUserModel = getLoginUser(session);
            inventoryVerification.setCreateUserId(adminUserModel.getAdminUserId());
            inventoryVerification.setUpdateUserId(adminUserModel.getAdminUserId());

            // 保存盘点单和盘点明细
            ResultHandle resultHandle = inventoryService.saveInventoryVerification(inventoryVerification);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("saveInventoryVerification error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除库存盘点单
     */
    @RequestMapping("/inventoryVerification/removeInvntVerification/{id}")
    @ResponseBody
    public String removeInvntVerification(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<InventoryVerification> resultHandle = inventoryService.removeInvntVerification(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removeInvntVerification error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据id查询库存盘点单
     * @return
     */
    @RequestMapping("/inventoryVerification/getInvntVerificationById/{id}")
    @ResponseBody
    public String getInvntVerificationById(@PathVariable int id){
        String result = "";
        try {
            InventoryVerification inventoryVerification = inventoryService.getInvntVerificationById(id);
            String data = CommonUtils.convertBeanToJson(inventoryVerification, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("getInvntVerificationById error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/inventoryVerification/updateInvntVerification")
    @ResponseBody
    public String updateInvntVerification(@RequestBody String data, HttpSession session){
        String result = "";
        try {
            // 盘点单
            JSONObject inventoryVerificationJson = JSONObject.fromObject(data);
            inventoryVerificationJson.remove("invtVerifItemList");
            InventoryVerification inventoryVerification = CommonUtils.convertJsonToBean(inventoryVerificationJson, InventoryVerification.class);

            // 更新用户
            AdminUserModel adminUserModel = getLoginUser(session);
            inventoryVerification.setUpdateUserId(adminUserModel.getAdminUserId());

            ResultHandle<InventoryVerification> resultHandle = inventoryService.updateInvntVerification(inventoryVerification);

            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("updateInvntVerification error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 添加盘点明细
     */
    @RequestMapping("/inventoryVerification/saveInvntVerificationItem")
    @ResponseBody
    public String saveInvntVerificationItem(@RequestBody InventoryVerificationItem item){
        String result = "";
        try {
            ResultHandle<InventoryVerificationItem> resultHandle = inventoryService.saveInvntVerificationItem(item);
            if(resultHandle.isSuccess()){
                String data = CommonUtils.convertBeanToJson(resultHandle.getReturnContent(), null).toString();
                result = getSuccessResult(data);
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        } catch (Exception e){
            log.error("saveInvntVerificationItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除盘点明细
     */
    @RequestMapping("/inventoryVerification/removeInvntVerificationItem/{id}")
    @ResponseBody
    public String removeInvntVerificationItem(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<InventoryVerificationItem> resultHandle = inventoryService.removeInvntVerificationItem(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removeInvntVerificationItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 查询调拨单列表
     */
    @RequestMapping("/transfer/fetchTransferOrders")
    @ResponseBody
    public String fetchTransferOrders(){
        String result = "";
        try {
            List<TransferOrderVo> orderList = inventoryService.fetchTransferOrders();
            String data = CommonUtils.convertBeanCollectionToJsonArray(orderList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetchTransferOrders error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 查询调拨单
     */
    @RequestMapping("/transfer/getTransferOrderById/{id}")
    @ResponseBody
    public String getTransferOrderById(@PathVariable int id){
        String result = "";
        try {
            TransferOrder transferOrder = inventoryService.getTransferOrderWithItemsById(id);
            String data = CommonUtils.convertBeanToJson(transferOrder, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("getTransferOrderById error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存调拨单
     * @param data
     * @return
     */
    @RequestMapping("/transfer/saveTransferOrder")
    @ResponseBody
    public String saveTransferOrder(@RequestBody String data, HttpSession session){
        String result = "";
        try {
            JSONObject transferOrderJson = JSONObject.fromObject(data);
            JSONArray itemArr = transferOrderJson.getJSONArray("transferOrderItems");
            transferOrderJson.remove("transferOrderItems");
            TransferOrder transferOrder = CommonUtils.convertJsonToBean(transferOrderJson, TransferOrder.class);

            List<TransferOrderItem> itemList = new ArrayList<>();
            for(int i=0; i<itemArr.size(); i++){
                JSONObject itemJson = itemArr.getJSONObject(i);
                TransferOrderItem item = CommonUtils.convertJsonToBean(itemJson, TransferOrderItem.class);
                itemList.add(item);
            }
            transferOrder.setTransferOrderItems(itemList);

            // 创建人&更新人
            AdminUserModel adminUserModel = getLoginUser(session);
            transferOrder.setCreateUserId(adminUserModel.getAdminUserId());
            transferOrder.setUpdateUserId(adminUserModel.getAdminUserId());

            // 保存采购单和采购明细
            ResultHandle resultHandle = inventoryService.saveTransferOrder(transferOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save transfer order error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/transfer/removeTransferOrderItem/{itemId}")
    @ResponseBody
    public String removeTransferOrderItem(@PathVariable int itemId){
        String result = "";
        try {
            ResultHandle<TransferOrderItem> resultHandle = inventoryService.removeTransferOrderItem(itemId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("removeTransferOrderItem error, itemId:" + itemId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/transfer/saveTransferOrderItem")
    @ResponseBody
    public String saveTransferOrderItem(@RequestBody TransferOrderItem transferOrderItem){
        String result = "";
        try {
            ResultHandle<TransferOrderItem> resultHandle = inventoryService.saveTransferOrderItem(transferOrderItem);
            if(resultHandle.isSuccess()){
                result = getSuccessResult(resultHandle.getReturnContent());
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("saveTransferOrderItem error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 更新调拨单
     * @param data
     * @param session
     * @return
     */
    @RequestMapping("/transfer/updateTransferOrder")
    @ResponseBody
    public String updateTransferOrder(@RequestBody String data, HttpSession session){
        String result = "";
        try {
            // 调拨单
            JSONObject transferOrderJson = JSONObject.fromObject(data);
            transferOrderJson.remove("transferOrderItems");
            TransferOrder transferOrder = CommonUtils.convertJsonToBean(transferOrderJson, TransferOrder.class);

            // 更新人
            AdminUserModel adminUserModel = getLoginUser(session);
            transferOrder.setUpdateUserId(adminUserModel.getAdminUserId());

            // 更新调拨单
            ResultHandle resultHandle = inventoryService.updateTransferOrder(transferOrder);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update transfer order error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除调拨单
     * @return
     */
    @RequestMapping("/transfer/removeTransferOrder/{id}")
    @ResponseBody
    public String removeTransferOrder(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<TransferOrder> resultHandle = inventoryService.removeTransferOrder(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove transfer order error, id:" + id, e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
