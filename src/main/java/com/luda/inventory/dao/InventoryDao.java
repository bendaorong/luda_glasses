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

    Mard lockMard(@Param("materielId") int materielId, @Param("storeId")  int storeId);

    void updateMard(@Param("id") int id, @Param("currentInventory") int currentInventory);

    int saveMard(Mard mard);

    List<MardVo> fetchMardVoList();

    List<PurchaseOrderVo> fetchPurchaseOrderVoList();

    PurchaseOrder getPurchaseOrderById(int id);

    List<PurchaseOrderItem> fetchPurchaseOrderItemList(int purchaseOrderId);
}
