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

//    @Test
//    public void testSavePurchaseOrder(){
//        PurchaseOrder purchaseOrder = new PurchaseOrder();
//        purchaseOrder.setStoreId(1);
//        purchaseOrder.setSupplierId(1);
//        purchaseOrder.setBusinessmanId(1);
//        purchaseOrder.setRemark("test");
//        purchaseOrder.setCreateUserId(1);
//        purchaseOrder.setUpdateUserId(1);
//
//        List<PurchaseOrderItem> itemList = new ArrayList<>();
//        PurchaseOrderItem item1 = new PurchaseOrderItem();
//        item1.setMaterielId(1);
//        item1.setPurchasePrice(new BigDecimal(95));
//        item1.setPurchaseQuantity(20);
//        item1.setRemark("打九五折");
//        itemList.add(item1);
//
//        PurchaseOrderItem item2 = new PurchaseOrderItem();
//        item2.setMaterielId(4);
//        item2.setPurchasePrice(new BigDecimal(70));
//        item2.setPurchaseQuantity(50);
//        item2.setRemark("打七折");
//        itemList.add(item2);
//
//        purchaseOrder.setPurchaseOrderItemList(itemList);
//        purchaseOrder.setTotalQuantity(purchaseOrder.getTotalItemQuantity());
//        purchaseOrder.setTotalAmount(purchaseOrder.getTotalItemAmount());
//
//        ResultHandle<PurchaseOrder> resultHandle = inventoryService.savePurchaseOrder(purchaseOrder);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testFetchPurchaseOrderList(){
//        CommonQueryBean queryBean = new CommonQueryBean();
//        queryBean.setOrderType("01");
//        List<PurchaseOrderVo> list = inventoryService.fetchPurchaseOrderVoList(queryBean);
//        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
//    }
//
//    @Test
//    public void testGetPurchaseOrderById(){
//        PurchaseOrder purchaseOrder = inventoryService.getPurchaseOrderById(5);
//        print(purchaseOrder.toString());
//    }
//
//    @Test
//    public void testSaveInvntVerif(){
//        InventoryVerification invntVerif = new InventoryVerification();
//        invntVerif.setVerifDate(new Date());
//        invntVerif.setStoreId(1);
//        invntVerif.setBusinessmanId(1);
//
//        List<InventoryVerificationItem> items = new ArrayList<>();
//
//        InventoryVerificationItem item1 = new InventoryVerificationItem();
//        item1.setMaterielId(1);
//        item1.setType(Constants.INVNT_VERIF_TYPE_WIN);
//        item1.setQuantity(5);
//        item1.setRemark("多了5个");
//        items.add(item1);
//
//        InventoryVerificationItem item2 = new InventoryVerificationItem();
//        item2.setMaterielId(4);
//        item2.setType(Constants.INVNT_VERIF_TYPE_LOSE);
//        item2.setQuantity(5);
//        item2.setRemark("少了5个");
//        items.add(item2);
//
//        invntVerif.setInvtVerifItemList(items);
//
//        inventoryService.saveInventoryVerification(invntVerif);
//    }
//
//    @Test
//    public void testSavePurchaseOrderItem(){
//        PurchaseOrderItem item = new PurchaseOrderItem();
//        item.setMaterielId(3);
//        item.setPurchaseOrderId(12);
//        item.setPurchaseQuantity(10);
//        item.setPurchasePrice(new BigDecimal(100));
//        ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.savePurchaseOrderItem(item);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testRemovePurchaseOrderItem(){
//        int itemId = 39;
//        ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.removePurchaseOrderItem(itemId);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testUpdatePurchaseOrderItem(){
//        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
//        purchaseOrderItem.setItemId(38);
//        purchaseOrderItem.setMaterielId(4);
//        purchaseOrderItem.setPurchaseOrderId(12);
//        purchaseOrderItem.setPurchaseQuantity(5);
//        purchaseOrderItem.setPurchasePrice(new BigDecimal(100));
//        purchaseOrderItem.setRemark("hhhhhh");
//        ResultHandle<PurchaseOrderItem> resultHandle = inventoryService.updatePurchaseOrderItem(purchaseOrderItem);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testGetInvntVerificationById(){
//        InventoryVerification inventoryVerification = inventoryService.getInvntVerificationById(6);
//        print(inventoryVerification.toString());
//    }
//
//    @Test
//    public void testRemoveInvntVerification(){
//        ResultHandle<InventoryVerification> resultHandle = inventoryService.removeInvntVerification(6);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testSaveTransferOrder(){
//        TransferOrder transferOrder = new TransferOrder();
//        transferOrder.setOutStoreId(1);
//        transferOrder.setInStoreId(2);
//        transferOrder.setBusinessmanId(1);
//
//        List<TransferOrderItem> itemList = new ArrayList<>();
//        transferOrder.setTransferOrderItems(itemList);
//
//        TransferOrderItem item1 = new TransferOrderItem();
//        item1.setMaterielId(60);
//        item1.setQuantity(100);
//        item1.setRemark("借你10个");
//        itemList.add(item1);
//
//        TransferOrderItem item2 = new TransferOrderItem();
//        item2.setMaterielId(4);
//        item2.setQuantity(100);
//        item2.setRemark("借你10个");
//        itemList.add(item2);
//
//        ResultHandle<TransferOrder> resultHandle = inventoryService.saveTransferOrder(transferOrder);
//        print(resultHandle.toString());
//    }
//
//    @Test
//    public void testFetchTransferOrder(){
//        List<TransferOrderVo> list = inventoryService.fetchTransferOrders();
//        print(list.toString());
//    }
//
//    @Test
//    public void testGetTransferOrderWithItemsById(){
//        TransferOrder transferOrder = inventoryService.getTransferOrderWithItemsById(1);
//        print(transferOrder.toString());
//    }
//
//    @Test
//    public void testGetMard(){
//        Mard mard = inventoryService.getMardById(1);
//        print(mard.toString());
//    }
}
