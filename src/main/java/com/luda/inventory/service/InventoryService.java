package com.luda.inventory.service;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.*;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */
public interface InventoryService {
    /**
     * 保存采购单
     */
    public ResultHandle<PurchaseOrder> savePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 查询商品库存
     */
    public List<MardVo> fetchMardVoList();

    /**
     * 查询采购单
     * @return
     */
    List<PurchaseOrderVo> fetchPurchaseOrderVoList();

    /**
     * 根据id查询采购单
     * @param id
     * @return
     */
    PurchaseOrder getPurchaseOrderById(int id);

    /**
     * 删除采购单
     * @param id
     * @return
     */
    ResultHandle removePurchaseOrder(int id);

    /**
     * 更新采购单
     * @param purchaseOrder
     * @return
     */
    ResultHandle updatePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 添加采购明细
     */
    ResultHandle<PurchaseOrderItem> savePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

    /**
     * 删除采购明细
     */
    ResultHandle<PurchaseOrderItem> removePurchaseOrderItem(int itemId);

    /**
     * 更新采购明细
     */
    ResultHandle<PurchaseOrderItem> updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

    /**
     * 保存库存盘点
     */
    ResultHandle<InventoryVerification> saveInventoryVerification(InventoryVerification inventoryVerification);

    /**
     * 查询盘点单列表
     */
    List<InventoryVerificationVo> fetchInvntVerifVoList();

    /**
     * 根据id查询盘点单
     * @param id
     * @return
     */
    public InventoryVerification getInvntVerificationById(int id);

    /**
     * 删除盘点单
     * @param id
     * @return
     */
    ResultHandle<InventoryVerification> removeInvntVerification(int id);

    ResultHandle<InventoryVerification> updateInvntVerification(InventoryVerification inventoryVerification);

    ResultHandle<InventoryVerificationItem> saveInvntVerificationItem(InventoryVerificationItem item);

    ResultHandle<InventoryVerificationItem> removeInvntVerificationItem(int id);
}
