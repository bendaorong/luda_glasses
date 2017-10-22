package com.luda.inventory.service;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.Mard;
import com.luda.inventory.model.MardVo;
import com.luda.inventory.model.PurchaseOrder;
import com.luda.inventory.model.PurchaseOrderVo;

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
}
