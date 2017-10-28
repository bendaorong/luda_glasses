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

    /**
     * 更新库存盘点单
     * @param inventoryVerification
     * @return
     */
    ResultHandle<InventoryVerification> updateInvntVerification(InventoryVerification inventoryVerification);

    /**
     * 保存库存盘点明细
     * @param item
     * @return
     */
    ResultHandle<InventoryVerificationItem> saveInvntVerificationItem(InventoryVerificationItem item);

    /**
     * 删除库存盘点明细
     * @param id
     * @return
     */
    ResultHandle<InventoryVerificationItem> removeInvntVerificationItem(int id);

    /**
     * 创建调拨单
     * @param transferOrder 调拨单
     */
    ResultHandle<TransferOrder> saveTransferOrder(TransferOrder transferOrder);

    /**
     * 查询调拨单
     */
    List<TransferOrderVo> fetchTransferOrders();

    /**
     * 根据id查询调拨单(包含调拨单明细)
     */
    TransferOrder getTransferOrderWithItemsById(int id);

    /**
     * 获取门店商品库存
     * @param materielId 商品id
     * @param storeId 门店id
     * @return
     */
    Mard getMard(int materielId, int storeId);

    /**
     * 删除调拨单明细
     * @param itemId
     * @return
     */
    ResultHandle<TransferOrderItem> removeTransferOrderItem(int itemId);

    /**
     * 保存调拨单明细
     * @param transferOrderItem
     * @return
     */
    ResultHandle<TransferOrderItem> saveTransferOrderItem(TransferOrderItem transferOrderItem);

    /**
     * 更新调拨单
     * @param transferOrder
     * @return
     */
    ResultHandle updateTransferOrder(TransferOrder transferOrder);

    /**
     * 删除调拨单
     * @param id
     * @return
     */
    ResultHandle<TransferOrder> removeTransferOrder(int id);
}
