package com.luda.inventory.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.inventory.dao.InventoryDao;
import com.luda.inventory.exception.InventoryException;
import com.luda.inventory.model.*;
import com.luda.inventory.service.InventoryService;
import com.luda.materiel.model.MaterielModel;
import com.luda.materiel.service.MaterielService;
import com.luda.util.CodeBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    // 调拨单编号缓存
    private static String TRANSFER_ORDER_CURRENT_CODE = null;

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private MaterielService materielService;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        inventoryDao.savePurchaseOrder(purchaseOrder);

        // 保存采购单明细
        initPurchaseOrderItem(purchaseOrder);
        inventoryDao.insertPurchaseOrderItemBatch(purchaseOrder.getPurchaseOrderItemList());

        // 更新商品库存,增加库存
        for(PurchaseOrderItem item : purchaseOrder.getPurchaseOrderItemList()){
            // 采购单则增加库存，采购退货单则扣减库存
            Mard mard = getMard(item.getMaterielId(), purchaseOrder.getStoreId(),
                    item.getSphere(), item.getCylinder(), item.getAxial());
            if(Constants.ORDER_TYPE_PURCHASE.equals(purchaseOrder.getOrderType())){
                // 采购
                if(mard == null){
                    saveMard(item.getMaterielId(), purchaseOrder.getStoreId(), item.getPurchaseQuantity(),
                            item.getSphere(), item.getCylinder(), item.getAxial());
                }else {
                    updateMardById(mard.getId(), item.getPurchaseQuantity());
                }
            }else {
                // 退货
                if(mard == null){
                    resultHandle.setMsg("商品库存不存在");
                    return resultHandle;
                }
                updateMardById(mard.getId(), -item.getPurchaseQuantity());
            }
        }
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

        return resultHandle;
    }

    @Override
    @Transactional
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

            // 更新库存，采购单增加库存，采购退货单扣减库存
            // 采购单则增加库存，采购退货单则扣减库存
            if(Constants.ORDER_TYPE_PURCHASE.equals(purchaseOrder.getOrderType())){
                // 采购
                Mard mard = getMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(),
                        purchaseOrderItem.getSphere(), purchaseOrderItem.getCylinder(), purchaseOrderItem.getAxial());
                if(mard == null){
                    saveMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(), purchaseOrderItem.getPurchaseQuantity(),
                            purchaseOrderItem.getSphere(), purchaseOrderItem.getCylinder(), purchaseOrderItem.getAxial());
                }else {
                    updateMardById(mard.getId(), purchaseOrderItem.getPurchaseQuantity());
                }
            }else {
                // 退货
                updateMardById(purchaseOrderItem.getMardId(), -purchaseOrderItem.getPurchaseQuantity());
            }
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
    @Transactional
    public ResultHandle<PurchaseOrderItem> removePurchaseOrderItem(int itemId) {
        ResultHandle<PurchaseOrderItem> resultHandle = new ResultHandle<>();

        PurchaseOrderItem purchaseOrderItem = inventoryDao.getPurchaseOrderItemById(itemId);
        if(purchaseOrderItem == null){
            resultHandle.setMsg("采购明细不存在");
            return resultHandle;
        }

        // 删除明细
        inventoryDao.removePurchaseOrderItem(itemId);

        PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderItem.getPurchaseOrderId());
        Mard mard = getMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(),
                purchaseOrderItem.getSphere(), purchaseOrderItem.getCylinder(), purchaseOrderItem.getAxial());

        // 更新采购单总数量和总金额
        updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
        // 更新库存，删除采购单明细则扣减库存，删除采购退货单则增加库存
        if(Constants.ORDER_TYPE_PURCHASE.equals(purchaseOrder.getOrderType())){
            updateMardById(mard.getId(), -purchaseOrderItem.getPurchaseQuantity());
        }else {
            updateMardById(mard.getId(), purchaseOrderItem.getPurchaseQuantity());
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
        inventoryDao.updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
    }

    /**
     * 更新库存数量
     * @param materielId 商品
     * @param storeId 门店
     * @param increment 库存增量(>0:新增库存 <0:扣减库存)
     * @param sphere 球镜
     * @param cylinder 柱镜
     * @param axial 轴向
     **/
    private int saveMard(int materielId, int storeId, int increment, double sphere, double cylinder, double axial){
        log.info("new mard>> materielId:" + materielId + " storeId:" + storeId + " increment:" + increment);
        Mard mard = new Mard();
        mard.setMaterielId(materielId);
        mard.setStoreId(storeId);
        mard.setCurrentInventory(increment);
        mard.setSphere(sphere);
        mard.setCylinder(cylinder);
        mard.setAxial(axial);
        MaterielModel materielModel = materielService.getById(materielId);
        mard.setTypeId(materielModel.getTypeId());
        return inventoryDao.saveMard(mard);
    }

    @Override
    public ResultHandle<PurchaseOrderItem> updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        ResultHandle<PurchaseOrderItem> resultHandle = new ResultHandle<>();
//        String errorMsg = checkPurchaseOrderItem(purchaseOrderItem);
//        if(StringUtils.isNotEmpty(errorMsg)){
//            resultHandle.setMsg(errorMsg);
//            return resultHandle;
//        }
//        // 当前库里的明细
//        PurchaseOrderItem sourceItem = inventoryDao.getPurchaseOrderItemById(purchaseOrderItem.getItemId());
//        if(sourceItem == null){
//            resultHandle.setMsg("采购明细不存在");
//            return resultHandle;
//        }
//        //更新明细
//        int result = inventoryDao.updatePurchaseOrderItem(purchaseOrderItem);
//        if(result > 0){
//            // 更新采购单总金额和总数量
//            PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderItem.getPurchaseOrderId());
//            updatePurchaseOrderTotalQuantityAndTotalAmount(purchaseOrder);
//            // 更新库存
//            // 判断商品是否变换
//            //若商品变换了，则先扣减原明细商品的库存，再增加新明细商品的库存
//            //若商品未变换，则增量更新商品库存
//            if(purchaseOrderItem.getMaterielId() != sourceItem.getMaterielId()){
//                updateMard(sourceItem.getMaterielId(), purchaseOrder.getStoreId(), -sourceItem.getPurchaseQuantity());
//                updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(), purchaseOrderItem.getPurchaseQuantity());
//            }else {
//                updateMard(purchaseOrderItem.getMaterielId(), purchaseOrder.getStoreId(),
//                        purchaseOrderItem.getPurchaseQuantity() - sourceItem.getPurchaseQuantity());
//            }
//        }
        return resultHandle;
    }

    @Override
    public List<MardVo> fetchMardVoList(CommonQueryBean queryBean) {
        return inventoryDao.fetchMardVoList(queryBean);
    }

    @Override
    public Mard lockMard(int materielId, int storeId, double sphere, double cylinder, double axial) {
        return inventoryDao.lockMard(materielId, storeId, sphere, cylinder, axial);
    }

    @Override
    public Mard lockMardById(int mardId){
        return inventoryDao.lockMardById(mardId);
    }

    @Override
    public int updateMardById(int mardId, int increment) {
        Mard mard = lockMardById(mardId);
        if(mard == null){
            throw new InventoryException("商品库存不存在");
        }
        // 扣减库存时，判断商品库存数量是否足够
        log.info("Thread:" +Thread.currentThread().getName()+" update mard>> mardId:" + mardId + " increment:" + increment + " current:" + mard.getCurrentInventory());
        if(increment < 0){
            if(mard.getCurrentInventory() + increment < 0){
                throw new InventoryException("商品库存不足");
            }
        }
        return inventoryDao.updateMard(mard.getId(), increment);
    }

    private Mard getMard(int materielId, int storeId, double sphere, double cylinder, double axial){
        return inventoryDao.getMard(materielId, storeId, sphere, cylinder, axial);
    }

    @Override
    public Mard getMardById(int mardId){
        return inventoryDao.getMardById(mardId);
    }

    @Override
    public int removeMard(int materielId) {
        return inventoryDao.removeMard(materielId);
    }

    @Override
    public List<PurchaseOrderVo> fetchPurchaseOrderVoList(CommonQueryBean queryBean) {
        return inventoryDao.fetchPurchaseOrderVoList(queryBean);
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(int id) {
        PurchaseOrder purchaseOrder = inventoryDao.getPurchaseOrderById(id);
        List<PurchaseOrderItem> items = inventoryDao.fetchPurchaseOrderItemList(purchaseOrder.getPurchaseOrderId());
        purchaseOrder.setPurchaseOrderItemList(items);
        return purchaseOrder;
    }

    @Override
    public PurchaseOrder getPurchaseOrderWithoutItemsById(int id) {
        return inventoryDao.getPurchaseOrderById(id);
    }

    @Override
    @Transactional
    public ResultHandle removePurchaseOrder(int id) {
        ResultHandle resultHandle = new ResultHandle();
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        // 删除采购单
        int result = inventoryDao.removePurchaseOrder(id);
        // 更新商品库存
        if(result > 0){
            // 采购单扣减库存，采购退货单增加库存
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(purchaseOrder.getPurchaseOrderItemList())){
                for(PurchaseOrderItem item : purchaseOrder.getPurchaseOrderItemList()){
                    Mard mard = getMard(item.getMaterielId(), purchaseOrder.getStoreId(),
                            item.getSphere(), item.getCylinder(), item.getAxial());
                    if(mard == null){
                        resultHandle.setMsg("商品库存不存在");
                        return resultHandle;
                    }
                    if(Constants.ORDER_TYPE_PURCHASE.equals(purchaseOrder.getOrderType())){
                        updateMardById(mard.getId(), -item.getPurchaseQuantity());
                    }else {
                        updateMardById(mard.getId(), item.getPurchaseQuantity());
                    }
                }
            }
        }
        return resultHandle;
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
     * 获取调拨单编号
     * 商品编号格式：1710200001，171020为当前日期，0001为当前计数
     * 从缓存计数器中获取商品编号最新值，若为当天的，则计数部分+1，
     * 若不是当天的，生成当天日期前缀，并从1开始计数，不足4位补0
     **/
    private synchronized String getTransferOrderCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(TRANSFER_ORDER_CURRENT_CODE)){
            TRANSFER_ORDER_CURRENT_CODE = inventoryDao.getTransferOrderMaxCode();
        }

        String nextCode = CodeBuilder.buildTransferOrderCode(TRANSFER_ORDER_CURRENT_CODE);

        // 刷新缓存
        TRANSFER_ORDER_CURRENT_CODE = nextCode;

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
    @Transactional
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
        for(InventoryVerificationItem item : inventoryVerification.getInvtVerifItemList()){
            if(Constants.INVNT_VERIF_TYPE_WIN.equals(item.getType())){
                updateMardById(item.getMardId(), item.getQuantity());
            }else {
                updateMardById(item.getMardId(), -item.getQuantity());
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
    public List<InventoryVerificationVo> fetchInvntVerifVoList(CommonQueryBean queryBean) {
        return inventoryDao.fetchInvntVerifVoList(queryBean);
    }

    @Override
    @Transactional
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
                updateMardById(item.getMardId(), item.isWin() ? (-item.getQuantity()) : item.getQuantity());
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
    @Transactional
    public ResultHandle<InventoryVerificationItem> saveInvntVerificationItem(InventoryVerificationItem item) {
        ResultHandle<InventoryVerificationItem> resultHandle = new ResultHandle<>();

        InventoryVerification inventoryVerification = getInvntVerificationById(item.getInventoryVerificationId());
        if(inventoryVerification == null){
            resultHandle.setMsg("盘点单不存在");
            return resultHandle;
        }

        int result = inventoryDao.saveInvntVerificationItem(item);

        if(result > 0){
            // 盘盈了增加库存，盘亏了扣减库存
            updateMardById(item.getMardId(), item.isWin() ? item.getQuantity() : (-item.getQuantity()));
        }

        resultHandle.setReturnContent(item);
        return resultHandle;
    }

    @Override
    @Transactional
    public ResultHandle<InventoryVerificationItem> removeInvntVerificationItem(int id) {
        ResultHandle<InventoryVerificationItem> resultHandle = new ResultHandle<>();
        InventoryVerificationItem item = inventoryDao.getInvntVerificationItemById(id);
        if(item == null){
            resultHandle.setMsg("盘点明细不存在");
            return resultHandle;
        }
        int result = inventoryDao.removeInvntVerificationItem(id);
        if(result > 0){
            // 更新库存
            updateMardById(item.getMardId(), item.isWin() ? (-item.getQuantity()) : item.getQuantity());
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

    @Override
    @Transactional
    public ResultHandle<TransferOrder> saveTransferOrder(TransferOrder transferOrder) {
        ResultHandle<TransferOrder> resultHandle = new ResultHandle<>();
        // 验证数据
        String errorMsg = checkTransferOrder(transferOrder, true);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }
        // 初始化数据
        initTransferOrder(transferOrder, true);
        // 保存调拨单
        inventoryDao.saveTransferOrder(transferOrder);
        // 保存调拨明细
        for(TransferOrderItem item : transferOrder.getTransferOrderItems()){
            item.setTransferOrderId(transferOrder.getId());
            inventoryDao.saveTransferOrderItem(item);
        }
        // 更新库存
        for(TransferOrderItem item : transferOrder.getTransferOrderItems()){
            // 调出门店扣减商品库存
            updateMardById(item.getMardId(), -item.getQuantity());
            // 调入门店增加商品库存
            Mard inMard = lockMard(item.getMaterielId(), transferOrder.getInStoreId(),
                    item.getSphere(), item.getCylinder(), item.getAxial());
            if(inMard == null){
                saveMard(item.getMaterielId(), transferOrder.getInStoreId(), item.getQuantity(),
                        item.getSphere(), item.getCylinder(), item.getAxial());
            }else {
                updateMardById(inMard.getId(), item.getQuantity());
            }
        }
        return resultHandle;
    }

    /**
     * 初始化调拨单数据
     * @param transferOrder
     * @param isNew
     */
    private void initTransferOrder(TransferOrder transferOrder, boolean isNew) {
        if(transferOrder.getTransferDate() == null){
            transferOrder.setTransferDate(new Date());
        }
        // 新增调拨单设置编号
        if(isNew){
            transferOrder.setCode(getTransferOrderCode());
        }
    }

    /**
     * 验证调拨单信息
     * @param transferOrder
     * @param isNew 是否为新增
     * @return
     */
    private String checkTransferOrder(TransferOrder transferOrder, boolean isNew) {
        // 调出门店
        if(transferOrder.getOutStoreId() <= 0){
            return "请选择调出门店";
        }
        // 调入门店
        if(transferOrder.getInStoreId() <= 0){
            return "请选择调入门店";
        }
        // 调出门店与调入门店不同
        if(transferOrder.getOutStoreId() == transferOrder.getInStoreId()){
            return "调出门店与调入门店不可相同";
        }
        // 新增时明细验证
        if(isNew){
            if(CollectionUtils.isEmpty(transferOrder.getTransferOrderItems())){
                return "请添加调拨明细";
            }
            // 验证库存数量
            for(TransferOrderItem item : transferOrder.getTransferOrderItems()){
                Mard outMard = inventoryDao.lockMard(item.getMaterielId(), transferOrder.getOutStoreId(),
                        item.getSphere(), item.getCylinder(), item.getAxial());
                if(outMard == null){
                    return "调拨商品库存不存在";
                }
                if(outMard.getCurrentInventory() < item.getQuantity()){
                    return "调拨商品库存不足";
                }
            }
        }
        return null;
    }

    @Override
    public List<TransferOrderVo> fetchTransferOrders(CommonQueryBean queryBean) {
        return inventoryDao.fetchTransferOrders(queryBean);
    }

    @Override
    public TransferOrder getTransferOrderWithItemsById(int id) {
        TransferOrder transferOrder = inventoryDao.getTransferOrderById(id);
        transferOrder.setTransferOrderItems(inventoryDao.getTransferOrderItems(transferOrder.getId()));
        return transferOrder;
    }

    @Override
    @Transactional
    public ResultHandle<TransferOrderItem> removeTransferOrderItem(int itemId) {
        ResultHandle<TransferOrderItem> resultHandle = new ResultHandle<>();
        TransferOrderItem item = inventoryDao.getTransferOrderItemById(itemId);
        if(item == null){
            resultHandle.setMsg("调拨明细不存在");
            return resultHandle;
        }
        int result = inventoryDao.removeTransferOrderItem(itemId);
        if(result > 0){
            TransferOrder transferOrder = inventoryDao.getTransferOrderById(item.getTransferOrderId());
            // 更新库存,将调拨的库存原路返换
            // 调入门店扣减库存
            Mard inMard = getMard(item.getMaterielId(), transferOrder.getInStoreId(),
                    item.getSphere(), item.getCylinder(), item.getAxial());
            updateMardById(inMard.getId(), -item.getQuantity());
            // 调出门店增加库存
            updateMardById(item.getMardId(), item.getQuantity());
        }
        return resultHandle;
    }

    @Override
    @Transactional
    public ResultHandle<TransferOrderItem> saveTransferOrderItem(TransferOrderItem transferOrderItem) {
        ResultHandle<TransferOrderItem> resultHandle = new ResultHandle<>();

        TransferOrder transferOrder = inventoryDao.getTransferOrderById(transferOrderItem.getTransferOrderId());
        String errorMsg = checkTransferOrderItem(transferOrderItem, transferOrder);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 保存明细
        int result = inventoryDao.saveTransferOrderItem(transferOrderItem);
        if(result > 0){
            resultHandle.setReturnContent(transferOrderItem);
            // 调出门店扣减库存
            updateMardById(transferOrderItem.getMardId(), -transferOrderItem.getQuantity());
            // 调入门店增加商品库存
            Mard inMard = lockMard(transferOrderItem.getMaterielId(), transferOrder.getInStoreId(),
                    transferOrderItem.getSphere(), transferOrderItem.getCylinder(), transferOrderItem.getAxial());
            if(inMard == null){
                saveMard(transferOrderItem.getMaterielId(), transferOrder.getInStoreId(), transferOrderItem.getQuantity(),
                        transferOrderItem.getSphere(), transferOrderItem.getCylinder(), transferOrderItem.getAxial());
            }else {
                updateMardById(inMard.getId(), transferOrderItem.getQuantity());
            }
        }else {
            resultHandle.setMsg("明细添加失败");
        }
        return resultHandle;
    }

    /**
     * 验证调拨明细
     * @param transferOrderItem
     * @return
     */
    private String checkTransferOrderItem(TransferOrderItem transferOrderItem, TransferOrder transferOrder) {
        if(transferOrderItem.getTransferOrderId() <= 0){
            return "调拨单号为空";
        }
        if(transferOrderItem.getMaterielId() <= 0){
            return "请选择商品";
        }
        if(transferOrderItem.getQuantity() <= 0){
            return "请输入调拨数量";
        }
        if(transferOrder == null){
            return "调拨单不存在";
        }
        // 验证库存数量
        Mard outMard = inventoryDao.lockMardById(transferOrderItem.getMardId());
        if(outMard == null){
            return "调拨商品库存不存在";
        }
        if(outMard.getCurrentInventory() < transferOrderItem.getQuantity()){
            return "调拨商品库存不足";
        }
        return null;
    }

    @Override
    public ResultHandle updateTransferOrder(TransferOrder transferOrder) {
        ResultHandle<TransferOrder> resultHandle = new ResultHandle<>();
        initTransferOrder(transferOrder, false);
        int result = inventoryDao.updateTransferOrder(transferOrder);
        if(result <= 0){
            resultHandle.setMsg("调拨单更新失败");
        }
        return resultHandle;
    }

    @Override
    @Transactional
    public ResultHandle<TransferOrder> removeTransferOrder(int id) {
        ResultHandle<TransferOrder> resultHandle = new ResultHandle<>();
        TransferOrder transferOrder = getTransferOrderWithItemsById(id);
        if(transferOrder == null){
            resultHandle.setMsg("调拨单不存在");
            return resultHandle;
        }

        // 删除调拨单
        int result = inventoryDao.removeTransferOrder(id);
        if(result > 0){
            // 返还商品库存
            for(TransferOrderItem item : transferOrder.getTransferOrderItems()){
                // 调入门店扣减库存
                Mard inMard = getMard(item.getMaterielId(), transferOrder.getInStoreId(),
                        item.getSphere(), item.getCylinder(), item.getAxial());
                updateMardById(inMard.getId(), -item.getQuantity());
                // 调出门店增加库存
                updateMardById(item.getMardId(), item.getQuantity());
            }
        }else {
            resultHandle.setMsg("调拨单删除失败");
        }

        return resultHandle;
    }


    @Override
    public int getMardTotalCount(MardQueryBean queryBean) {
        return inventoryDao.getMardTotalCount(queryBean);
    }

    @Override
    public List<MardVo> fetchMardVoListPage(MardQueryBean queryBean) {
        return inventoryDao.fetchMardVoListPage(queryBean);
    }

    @Override
    public int getPurchaseOrderItemCount(int purchaseOrderId) {
        return inventoryDao.getPurchaseOrderItemCount(purchaseOrderId);
    }

    @Override
    public List<PurchaseOrderItem> fetchPurchaseOrderItemListPage(CommonQueryBean queryBean) {
        return inventoryDao.fetchPurchaseOrderItemListPage(queryBean);
    }
}
