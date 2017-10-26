package com.luda.inventory.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.inventory.dao.InventoryDao;
import com.luda.inventory.exception.InventoryException;
import com.luda.inventory.model.*;
import com.luda.inventory.service.InventoryService;
import com.luda.util.CodeBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/10/21.
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService{
    private static final Logger log = Logger.getLogger(InventoryServiceImpl.class);

    // 采购单编号缓存
    private static String PURCHASE_ORDER_CURRENT_CODE = null;
    // 库存盘点单编号缓存
    private static String INVENTORY_VERIFICATION_CURRENT_CODE = null;

    @Autowired
    private InventoryDao inventoryDao;

    @Override
    @Transactional
    public ResultHandle<PurchaseOrder> savePurchaseOrder(PurchaseOrder purchaseOrder) {
        ResultHandle<PurchaseOrder> resultHandle = new ResultHandle<>();
        // 验证采购单
        String errorMsg = checkPurchaseOrder(purchaseOrder, true);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化采购单
        initPurchaseOrder(purchaseOrder);

        // 保存采购单
        int result = inventoryDao.savePurchaseOrder(purchaseOrder);
        if(result <= 0){
            throw new InventoryException("采购单保存失败");
        }

        // 保存采购单明细
        initPurchaseOrderItem(purchaseOrder);
        result = inventoryDao.insertPurchaseOrderItemBatch(purchaseOrder.getPurchaseOrderItemList());
        if(result <= 0){
            throw new InventoryException("采购单明细保存失败");
        }

        // 更新商品库存
        syncUpdateMard(purchaseOrder);

        return resultHandle;
    }

    @Override
    public ResultHandle updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        ResultHandle<PurchaseOrder> resultHandle = new ResultHandle<>();
        // 验证采购单
        String errorMsg = checkPurchaseOrder(purchaseOrder, false);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 更新采购单
        inventoryDao.updatePurchaseOrder(purchaseOrder);

        // 初始化采购单明细
        //initPurchaseOrderItem(purchaseOrder);

//        // 分离待新增明细和待更新明细
//        List<PurchaseOrderItem> newItems = new ArrayList<>();
//        List<PurchaseOrderItem> updateItems = new ArrayList<>();
//        for(PurchaseOrderItem item : purchaseOrder.getPurchaseOrderItemList()){
//            if(item.getItemId() == 0){
//                newItems.add(item);
//            }else{
//                updateItems.add(item);
//            }
//        }
//
//        // 更新之前的采购单明细
//        List<PurchaseOrderItem> sourceItems = inventoryDao.fetchPurchaseOrderItemList(purchaseOrder.getPurchaseOrderId());
//        // 处理库存变更
//        Map<Integer, Integer> inventoryMap = dealWithInventory(sourceItems, newItems, updateItems);
//
//        // 保存新增明细
//        if(!CollectionUtils.isEmpty(newItems)){
//            result = inventoryDao.insertPurchaseOrderItemBatch(newItems);
//            if(result <= 0){
//                throw new InventoryException("采购单明细保存失败");
//            }
//        }
//        // 更新明细
//        result = inventoryDao.updatePurchaseOrderItemBatch(updateItems);
//        if(result <= 0){
//            throw new InventoryException("采购单明细更新失败");
//        }
//
//        // 更新商品库存
//        syncUpdateMard(inventoryMap, purchaseOrder.getStoreId());

        return resultHandle;
    }

    @Override
    public ResultHandle<PurchaseOrderItem> savePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        ResultHandle<PurchaseOrderItem> resultHandle = new ResultHandle<>();
        String errorMsg = checkPurchaseOrderItem(purchaseOrderItem);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }
        // 保存明细
        int result = inventoryDao.savePurchaseOrderItem(purchaseOrderItem);
        if(result > 0){
            resultHandle.setReturnContent(purchaseOrderItem);
            // 更新采购单总数量和总金额
            PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderItem.getPurchaseOrderId());
            updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
            // 更新库存
            updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(), purchaseOrderItem.getPurchaseQuantity());
        }
        return resultHandle;
    }

    // 验证采购明细
    private String checkPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        if(purchaseOrderItem.getMaterielId() <= 0){
            return "请选择商品";
        }
        if(purchaseOrderItem.getPurchaseQuantity() <= 0){
            return "采购数量必须大于0";
        }
        if(purchaseOrderItem.getPurchasePrice() == null){
            return "请填写采购价格";
        }
        if(purchaseOrderItem.getPurchasePrice().compareTo(BigDecimal.ZERO) <= 0){
            return "采购价必须大于0";
        }
        return null;
    }

    @Override
    public ResultHandle<PurchaseOrderItem> removePurchaseOrderItem(int itemId) {
        ResultHandle<PurchaseOrderItem> resultHandle = new ResultHandle<>();
        PurchaseOrderItem purchaseOrderItem = inventoryDao.getPurchaseOrderItemById(itemId);
        if(purchaseOrderItem == null){
            resultHandle.setMsg("采购明细不存在");
            return resultHandle;
        }
        int result = inventoryDao.removePurchaseOrderItem(itemId);
        if(result > 0){
            // 更新采购单总数量和总金额
            PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderItem.getPurchaseOrderId());
            updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
            // 更新库存
            updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(), -purchaseOrderItem.getPurchaseQuantity());
        }
        return resultHandle;
    }

    /**
     * 更新采购单总数量和总金额
     * @param purchaseOrder 采购单
     */
    private void updatePurchaseOrderTotalQuantityAndTotalAmount(PurchaseOrder purchaseOrder){
        purchaseOrder.setTotalQuantity(purchaseOrder.getTotalItemQuantity());
        purchaseOrder.setTotalAmount(purchaseOrder.getTotalItemAmount());
        inventoryDao.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 更新库存数量
     * @param materielId 商品
     * @param storeId 门店
     * @param increment 库存增量(>0:新增库存 <0:扣减库存)
     **/
    private void updateMard(int materielId, int storeId, int increment){
        Mard mard = inventoryDao.lockMard(materielId, storeId);
        if(mard == null){
            mard = new Mard(materielId, storeId, increment);
            log.info("new mard>> materielId:" + materielId + " storeId:" + storeId + " increment:" + increment);
            inventoryDao.saveMard(mard);
        }else {
            log.info("update mard>> materielId:" + materielId + " storeId:" + storeId + " increment:" + increment + " current:" + mard.getCurrentInventory());
            inventoryDao.updateMard(mard.getId(), increment);
        }
    }

    @Override
    public ResultHandle<PurchaseOrderItem> updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        ResultHandle<PurchaseOrderItem> resultHandle = new ResultHandle<>();
        String errorMsg = checkPurchaseOrderItem(purchaseOrderItem);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }
        // 当前库里的明细
        PurchaseOrderItem sourceItem = inventoryDao.getPurchaseOrderItemById(purchaseOrderItem.getItemId());
        if(sourceItem == null){
            resultHandle.setMsg("采购明细不存在");
            return resultHandle;
        }
        //更新明细
        int result = inventoryDao.updatePurchaseOrderItem(purchaseOrderItem);
        if(result > 0){
            // 更新采购单总金额和总数量
            PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderItem.getPurchaseOrderId());
            updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
            // 更新库存
            // 判断商品是否变换
            //若商品变换了，则先扣减原明细商品的库存，再增加新明细商品的库存
            //若商品未变换，则增量更新商品库存
            if(purchaseOrderItem.getMaterielId() != sourceItem.getMaterielId()){
                updateMard(sourceItem.getMaterielId(), purchaseOrder.getStoreId(), -sourceItem.getPurchaseQuantity());
                updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(), purchaseOrderItem.getPurchaseQuantity());
            }else {
                updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(),
                        purchaseOrderItem.getPurchaseQuantity() - sourceItem.getPurchaseQuantity());
            }
        }
        return resultHandle;
    }

    /**
     * 更新采购单时处理商品库存变化
     * @param sourceItems 更新前的当前采购单明细
     * @param newItems 本次增的采购单明细
     * @param updateItems 原有采购单明细
     */
    private Map<Integer, Integer> dealWithInventory(List<PurchaseOrderItem> sourceItems, List<PurchaseOrderItem> newItems, List<PurchaseOrderItem> updateItems) {
        Map<Integer, Integer> invtMap = gatherInventoryFromItems(sourceItems);
        if(updateItems.size() > 0){
            for(PurchaseOrderItem updateItem : updateItems){
                // 增量库存 = 更新后的采购数量 - 原有采购数量
                invtMap.put(updateItem.getMaterielId(), updateItem.getPurchaseQuantity() - invtMap.get(updateItem.getMaterielId()));
            }
        }
        if(newItems.size() > 0){
            for(PurchaseOrderItem newItem : newItems){
                Integer materielId = Integer.valueOf(newItem.getMaterielId());
                if(invtMap.get(materielId) != null){
                    invtMap.put(materielId, invtMap.get(materielId) + newItem.getPurchaseQuantity());
                }else {
                    invtMap.put(materielId, newItem.getPurchaseQuantity());
                }
            }
        }
        return invtMap;
    }

    @Override
    public List<MardVo> fetchMardVoList() {
        return inventoryDao.fetchMardVoList();
    }

    @Override
    public List<PurchaseOrderVo> fetchPurchaseOrderVoList() {
        return inventoryDao.fetchPurchaseOrderVoList();
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(int id) {
        PurchaseOrder purchaseOrder = inventoryDao.getPurchaseOrderById(id);
        List<PurchaseOrderItem> items = inventoryDao.fetchPurchaseOrderItemList(purchaseOrder.getPurchaseOrderId());
        purchaseOrder.setPurchaseOrderItemList(items);
        return purchaseOrder;
    }

    @Override
    public ResultHandle removePurchaseOrder(int id) {
        ResultHandle resultHandle = new ResultHandle();
        // 删除采购单
        int result = inventoryDao.removePurchaseOrder(id);
        // 扣减商品库存
        if(result > 0){
            // 收集商品库存
            PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
            Map<Integer, Integer> invtMap = gatherInventoryFromItems(purchaseOrder.getPurchaseOrderItemList());

            // 扣减库存
            Set<Map.Entry<Integer, Integer>> invtSet = invtMap.entrySet();
            Iterator<Map.Entry<Integer, Integer>> iterator = invtSet.iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, Integer> entry = iterator.next();
                // 锁库存
                Mard mard = inventoryDao.lockMard(entry.getKey(), purchaseOrder.getStoreId());
                if(mard != null){
                    inventoryDao.updateMard(mard.getId(), -entry.getValue());
                }
            }
        }
        return resultHandle;
    }

    /**
     * 从采购单明细中收集商品库存信息
     * @param itemList
     * @return
     */
    private Map<Integer, Integer> gatherInventoryFromItems(List<PurchaseOrderItem> itemList){
        Map<Integer, Integer> invtMap = new HashMap<>();
        for(PurchaseOrderItem item : itemList){
            Integer materielId = Integer.valueOf(item.getMaterielId());
            if(invtMap.get(materielId) != null){
                invtMap.put(materielId, invtMap.get(materielId) + item.getPurchaseQuantity());
            }else {
                invtMap.put(materielId, item.getPurchaseQuantity());
            }
        }
        return invtMap;
    }

    /**
     * 更新商品库存
     * 采购单明细中的商品允许重复，所以先汇总商品库存，再更新到库里
     **/
    private void syncUpdateMard(PurchaseOrder purchaseOrder) {
        // 商品库存
        Map<Integer, Integer> invtMap = gatherInventoryFromItems(purchaseOrder.getPurchaseOrderItemList());
        syncUpdateMard(invtMap, purchaseOrder.getStoreId());
    }

    private void syncUpdateMard(Map<Integer, Integer> invtMap, int storeId){
        //先用select for update所商品库存记录
        Set<Map.Entry<Integer, Integer>> invtSet = invtMap.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = invtSet.iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Integer> entry = iterator.next();
            // 锁库存
            Mard mard = inventoryDao.lockMard(entry.getKey(), storeId);
            if(mard == null){
                mard = new Mard(entry.getKey(), storeId, entry.getValue());
                inventoryDao.saveMard(mard);
            }else {
                inventoryDao.updateMard(mard.getId(), entry.getValue());
            }
        }
    }

    //初始化采购单明细
    private void initPurchaseOrderItem(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderItem> itemList = purchaseOrder.getPurchaseOrderItemList();
        for(PurchaseOrderItem item : itemList){
            item.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        }
    }

    //初始化采购单
    private void initPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setCode(getPurchaseOrderCode());
        if(purchaseOrder.getPurchaseDate() == null){
            purchaseOrder.setPurchaseDate(new Date());
        }
    }

    /**
     * 获取采购单编号
     * 商品编号格式：1710200001，171020为当前日期，0001为当前计数
     * 从缓存计数器中获取商品编号最新值，若为当天的，则计数部分+1，
     * 若不是当天的，生成当天日期前缀，并从1开始计数，不足4位补0
     **/
    private synchronized String getPurchaseOrderCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(PURCHASE_ORDER_CURRENT_CODE)){
            PURCHASE_ORDER_CURRENT_CODE = inventoryDao.getPurchaseOrderMaxCode();
        }

        String nextCode = CodeBuilder.buildPurchaseOrderCode(PURCHASE_ORDER_CURRENT_CODE);

        // 刷新缓存
        PURCHASE_ORDER_CURRENT_CODE = nextCode;

        return nextCode;
    }

    /**
     * 获取盘点单编号
     * 商品编号格式：1710200001，171020为当前日期，0001为当前计数
     * 从缓存计数器中获取商品编号最新值，若为当天的，则计数部分+1，
     * 若不是当天的，生成当天日期前缀，并从1开始计数，不足4位补0
     **/
    private synchronized String getInvtVerifCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(INVENTORY_VERIFICATION_CURRENT_CODE)){
            INVENTORY_VERIFICATION_CURRENT_CODE = inventoryDao.getInvtVerifMaxCode();
        }

        String nextCode = CodeBuilder.buildInvtVerifCode(INVENTORY_VERIFICATION_CURRENT_CODE);

        // 刷新缓存
        INVENTORY_VERIFICATION_CURRENT_CODE = nextCode;

        return nextCode;
    }

    /**
     * 验证采购单
     * @param isNew 新增采购单
     **/

    private String checkPurchaseOrder(PurchaseOrder purchaseOrder, boolean isNew) {
        if(purchaseOrder.getStoreId() <= 0){
            return "请选择门店";
        }
        if(purchaseOrder.getSupplierId() <= 0){
            return "请选择供应商";
        }
        if(purchaseOrder.getTotalQuantity() <= 0){
            return "总数量必须大于0";
        }
        if(purchaseOrder.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0){
            return "总金额必须大于0";
        }
        if(isNew){
            if(CollectionUtils.isEmpty(purchaseOrder.getPurchaseOrderItemList())){
                return "请录入采购单明细";
            }
            // 总数量=明细数量总计
            if(purchaseOrder.getTotalQuantity() != purchaseOrder.getTotalItemQuantity()){
                return "采购单总数量与明细总数量不一致";
            }
            // 总金额=明细金额总计
            if(purchaseOrder.getTotalAmount().compareTo(purchaseOrder.getTotalItemAmount()) != 0){
                return "采购单总金额与明细总金额不一致";
            }
        }
        return null;
    }

    @Override
    public ResultHandle<InventoryVerification> saveInventoryVerification(InventoryVerification inventoryVerification) {
        ResultHandle<InventoryVerification> resultHandle = new ResultHandle<>();
        String errorMsg = checkInventoryVerifation(inventoryVerification, true);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化盘点单信息
        initInvtVerifInfo(inventoryVerification);

        // 保存盘点单
        int result = inventoryDao.saveInvtVerif(inventoryVerification);
        if(result <= 0){
            throw new InventoryException("盘点单保存失败");
        }

        // 保存盘点单明细
        initInvtVerifItem(inventoryVerification);
        result = inventoryDao.insertInvtVerifItemBatch(inventoryVerification.getInvtVerifItemList());
        if(result <= 0){
            throw new InventoryException("盘点单明细保存失败");
        }

        // 更新商品库存
        Map<Integer, Integer> invtMap = new HashMap<>();
        for(InventoryVerificationItem item : inventoryVerification.getInvtVerifItemList()){
            Integer materielId = Integer.valueOf(item.getMaterielId());

            int quantity = 0;
            if(Constants.INVNT_VERIF_TYPE_WIN.equals(item.getType())){
                quantity = item.getQuantity();
            }else {
                quantity = -item.getQuantity();
            }
            if(invtMap.get(materielId) != null){
                invtMap.put(materielId, invtMap.get(materielId) + quantity);
            }else {
                invtMap.put(materielId, quantity);
            }
        }

        //先用select for update所商品库存记录
        Set<Map.Entry<Integer, Integer>> invtSet = invtMap.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = invtSet.iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Integer> entry = iterator.next();
            // 锁库存
            Mard mard = inventoryDao.lockMard(entry.getKey(), inventoryVerification.getStoreId());
            if(mard == null){
                mard = new Mard(entry.getKey(), inventoryVerification.getStoreId(), entry.getValue());
                inventoryDao.saveMard(mard);
            }else {
                inventoryDao.updateMard(mard.getId(), entry.getValue());
            }
        }

        return resultHandle;
    }

    private void initInvtVerifItem(InventoryVerification inventoryVerification) {
        for(InventoryVerificationItem item : inventoryVerification.getInvtVerifItemList()){
            item.setInventoryVerificationId(inventoryVerification.getId());
        }
    }

    /**
     * 初始化盘点订单信息
     * @param inventoryVerification
     */
    private void initInvtVerifInfo(InventoryVerification inventoryVerification) {
        inventoryVerification.setCode(getInvtVerifCode());
        if(inventoryVerification.getVerifDate() == null){
            inventoryVerification.setVerifDate(new Date());
        }
    }

    /**
     * 验证盘点信息
     * @param inventoryVerification
     * @return
     */
    private String checkInventoryVerifation(InventoryVerification inventoryVerification, boolean isNew) {
        if(inventoryVerification.getStoreId() == 0){
            return "请选择门店";
        }
        if(inventoryVerification.getBusinessmanId() == 0){
            return "请选择业务员";
        }
        if(isNew){
            if(CollectionUtils.isEmpty(inventoryVerification.getInvtVerifItemList())){
                return "请添加盘点明细";
            }
        }
        return null;
    }


    @Override
    public List<InventoryVerificationVo> fetchInvntVerifVoList() {
        return inventoryDao.fetchInvntVerifVoList();
    }

    @Override
    public ResultHandle<InventoryVerification> removeInvntVerification(int id) {
        ResultHandle<InventoryVerification> resultHandle = new ResultHandle<>();
        InventoryVerification inventoryVerification = getInvntVerificationById(id);
        if(inventoryVerification == null){
            resultHandle.setMsg("盘点单不存在");
            return resultHandle;
        }

        int result = inventoryDao.removeInvntVerification(id);
        if(result > 0){
            List<InventoryVerificationItem> items = inventoryVerification.getInvtVerifItemList();
            for(InventoryVerificationItem item : items){
                // 若是盘盈，则扣减库存，若是盘亏，则增加库存(与保存盘点单是相反)
                updateMard(item.getMaterielId(), inventoryVerification.getStoreId(),
                        item.isWin() ? (-item.getQuantity()) : item.getQuantity());
            }
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<InventoryVerification> updateInvntVerification(InventoryVerification inventoryVerification) {
        ResultHandle<InventoryVerification> resultHandle = new ResultHandle<>();
        String errorMsg = checkInventoryVerifation(inventoryVerification, false);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        if(inventoryVerification.getVerifDate() == null){
            inventoryVerification.setVerifDate(new Date());
        }

        inventoryDao.updateInvntVerification(inventoryVerification);

        return resultHandle;
    }

    @Override
    public ResultHandle<InventoryVerificationItem> saveInvntVerificationItem(InventoryVerificationItem item) {
        ResultHandle<InventoryVerificationItem> resultHandle = new ResultHandle<>();

        InventoryVerification inventoryVerification = getInvntVerificationById(item.getInventoryVerificationId());
        if(inventoryVerification == null){
            resultHandle.setMsg("盘点单不存在");
            return resultHandle;
        }

        int result = inventoryDao.saveInvntVerificationItem(item);

        if(result > 0){
            // 更新库存
            // 盘盈了增加库存，盘亏了扣减库存
            updateMard(item.getMaterielId(), inventoryVerification.getStoreId(),
                    item.isWin() ? item.getQuantity() : (-item.getQuantity()));
        }

        resultHandle.setReturnContent(item);
        return resultHandle;
    }

    @Override
    public ResultHandle<InventoryVerificationItem> removeInvntVerificationItem(int id) {
        ResultHandle<InventoryVerificationItem> resultHandle = new ResultHandle<>();
        InventoryVerificationItem item = inventoryDao.getInvntVerificationItemById(id);
        if(item == null){
            resultHandle.setMsg("盘点明细不存在");
            return resultHandle;
        }
        int result = inventoryDao.removeInvntVerificationItem(id);
        if(result > 0){
            InventoryVerification inventoryVerification = getInvntVerificationById(item.getInventoryVerificationId());
            // 更新库存
            updateMard(item.getMaterielId(), inventoryVerification.getStoreId(),
                    item.isWin() ? (-item.getQuantity()) : item.getQuantity());
        }
        return resultHandle;
    }

    @Override
    public InventoryVerification getInvntVerificationById(int id) {
        InventoryVerification inventoryVerification = inventoryDao.getInvntVerificationById(id);
        List<InventoryVerificationItem> itemList = inventoryDao.fetchInvntVerificationItemList(inventoryVerification.getId());
        inventoryVerification.setInvtVerifItemList(itemList);
        return inventoryVerification;
    }
}
