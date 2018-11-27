package com.luda.inventory.dao;

import com.luda.inventory.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */
public interface InventoryDao {
    /**
     * 获取采购单最大编号
     * @return
     */
    String getPurchaseOrderMaxCode();

    /**
     * 保存采购单
     * @param purchaseOrder
     * @return
     */
    int savePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 批量保存采购单明细
     * @param purchaseOrderItemList
     * @return
     */
    int insertPurchaseOrderItemBatch(List<PurchaseOrderItem> purchaseOrderItemList);

    Mard lockMard(@Param("materielId") int materielId, @Param("storeId")  int storeId,
                  @Param("sphere") double sphere, @Param("cylinder") double cylinder, @Param("axial") double axial);

    Mard lockMardById(int id);

    /**
     * 更新商品库存
     * @param id
     * @param increment 库存增量值
     * @return
     */
    int updateMard(@Param("id") int id, @Param("increment") int increment);

    int saveMard(Mard mard);

    List<MardVo> fetchMardVoList(CommonQueryBean queryBean);

    List<PurchaseOrderVo> fetchPurchaseOrderVoList(CommonQueryBean queryBean);

    PurchaseOrder getPurchaseOrderById(int id);

    List<PurchaseOrderItem> fetchPurchaseOrderItemList(int purchaseOrderId);

    int removePurchaseOrder(int purchaseOrderId);

    int updatePurchaseOrder(PurchaseOrder purchaseOrder);

    int updatePurchaseOrderItemBatch(List<PurchaseOrderItem> updateItems);

    /**
     * 获取盘点单最大编号
     * @return
     */
    String getInvtVerifMaxCode();

    int saveInvtVerif(InventoryVerification inventoryVerification);

    int insertInvtVerifItemBatch(List<InventoryVerificationItem> invtVerifItemList);

    List<InventoryVerificationVo> fetchInvntVerifVoList(CommonQueryBean queryBean);

    int savePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

    PurchaseOrderItem getPurchaseOrderItemById(int itemId);

    int removePurchaseOrderItem(int itemId);

    int updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);

    int removeInvntVerification(int id);

    InventoryVerification getInvntVerificationById(int id);

    List<InventoryVerificationItem> fetchInvntVerificationItemList(int inventoryVerificationId);

    int updateInvntVerification(InventoryVerification inventoryVerification);

    int saveInvntVerificationItem(InventoryVerificationItem item);

    int removeInvntVerificationItem(int id);

    InventoryVerificationItem getInvntVerificationItemById(int id);

    int saveTransferOrder(TransferOrder transferOrder);

    int saveTransferOrderItem(TransferOrderItem item);

    String getTransferOrderMaxCode();

    List<TransferOrderVo> fetchTransferOrders(CommonQueryBean queryBean);

    TransferOrder getTransferOrderById(int id);

    List<TransferOrderItem> getTransferOrderItems(int transferOrderId);

    Mard getMard(@Param("materielId") int materielId, @Param("storeId") int storeId,
                 @Param("sphere") double sphere, @Param("cylinder") double cylinder, @Param("axial") double axial);

    Mard getMardById(int id);

    int removeMard(int materielId);

    TransferOrderItem getTransferOrderItemById(int itemId);

    int removeTransferOrderItem(int itemId);

    int updateTransferOrder(TransferOrder transferOrder);

    int removeTransferOrder(int id);

    int updatePurchaseOrderTotalQuantityAndTotalAmount(PurchaseOrder purchaseOrder);

    int getMardTotalCount(MardQueryBean queryBean);

    List<MardVo> fetchMardVoListPage(MardQueryBean queryBean);

    int getPurchaseOrderItemCount(int purchaseOrderId);

    List<PurchaseOrderItem> fetchPurchaseOrderItemListPage(CommonQueryBean queryBean);

    int getTotalInventorys(MardQueryBean queryBean);
}
