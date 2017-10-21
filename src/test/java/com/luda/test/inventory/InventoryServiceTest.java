package com.luda.test.inventory;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.*;
import com.luda.inventory.service.InventoryService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */
public class InventoryServiceTest extends SpringSimpleJunit{
    @Autowired
    private InventoryService inventoryService;

    @Test
    public void testSavePurchaseOrder(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStoreId(1);
        purchaseOrder.setSupplierId(1);
        purchaseOrder.setBusinessmanId(1);
        purchaseOrder.setRemark("test");
        purchaseOrder.setCreateUserId(1);
        purchaseOrder.setUpdateUserId(1);

        List<PurchaseOrderItem> itemList = new ArrayList<>();
        PurchaseOrderItem item1 = new PurchaseOrderItem();
        item1.setMaterielId(1);
        item1.setPurchasePrice(new BigDecimal(95));
        item1.setPurchaseQuantity(20);
        item1.setRemark("打九五折");
        itemList.add(item1);

        PurchaseOrderItem item2 = new PurchaseOrderItem();
        item2.setMaterielId(4);
        item2.setPurchasePrice(new BigDecimal(70));
        item2.setPurchaseQuantity(50);
        item2.setRemark("打七折");
        itemList.add(item2);

        purchaseOrder.setPurchaseOrderItemList(itemList);
        purchaseOrder.setTotalQuantity(purchaseOrder.getTotalItemQuantity());
        purchaseOrder.setTotalAmount(purchaseOrder.getTotalItemAmount());

        ResultHandle<PurchaseOrder> resultHandle = inventoryService.savePurchaseOrder(purchaseOrder);
        print(resultHandle.toString());
    }

    @Test
    public void testFetchMardList(){
        List<MardVo> list = inventoryService.fetchMardVoList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
    }

    @Test
    public void testFetchPurchaseOrderList(){
        List<PurchaseOrderVo> list = inventoryService.fetchPurchaseOrderVoList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
    }
}
