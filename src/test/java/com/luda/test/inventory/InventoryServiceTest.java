package com.luda.test.inventory;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.*;
import com.luda.inventory.service.InventoryService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void testGetPurchaseOrderById(){
        PurchaseOrder purchaseOrder = inventoryService.getPurchaseOrderById(5);
        print(purchaseOrder.toString());
    }

    @Test
    public void testSaveInvntVerif(){
        InventoryVerification invntVerif = new InventoryVerification();
        invntVerif.setVerifDate(new Date());
        invntVerif.setStoreId(1);
        invntVerif.setBusinessmanId(1);

        List<InventoryVerificationItem> items = new ArrayList<>();

        InventoryVerificationItem item1 = new InventoryVerificationItem();
        item1.setMaterielId(1);
        item1.setType(Constants.INVNT_VERIF_TYPE_WIN);
        item1.setQuantity(5);
        item1.setRemark("多了5个");
        items.add(item1);

        InventoryVerificationItem item2 = new InventoryVerificationItem();
        item2.setMaterielId(4);
        item2.setType(Constants.INVNT_VERIF_TYPE_LOSE);
        item2.setQuantity(5);
        item2.setRemark("少了5个");
        items.add(item2);

        invntVerif.setInvtVerifItemList(items);

        inventoryService.saveInventoryVerification(invntVerif);
    }
}
