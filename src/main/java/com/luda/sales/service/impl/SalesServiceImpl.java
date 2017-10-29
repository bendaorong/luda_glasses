package com.luda.sales.service.impl;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.Mard;
import com.luda.inventory.service.InventoryService;
import com.luda.sales.dao.SalesDao;
import com.luda.sales.model.*;
import com.luda.sales.service.SalesService;
import com.luda.util.CodeBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */
@Service("salesService")
public class SalesServiceImpl implements SalesService {
    private static Logger log = Logger.getLogger(SalesServiceImpl.class);

    // 当前销售单编号
    private static String SALES_ORDER_CURRENT_CODE = null;
    // 当前退货单编号
    private static String REFUND_ORDER_CURRENT_CODE = null;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SalesDao salesDao;

    @Override
    public ResultHandle<SalesOrder> saveSalesOrder(SalesOrder salesOrder) {
        ResultHandle<SalesOrder> resultHandle = new ResultHandle<>();
        // 验证
        String errorMsg = checkSalesOrder(salesOrder, true);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }
        //初始化销售单
        initSalesOrder(salesOrder, true);

        // 保存销售单
        salesDao.saveSalesOrder(salesOrder);

        // 保存销售明细
        for(SalesOrderItem item : salesOrder.getSalesOrderItems()){
            item.setSalesOrderId(salesOrder.getId());
            salesDao.saveSalesOrderItem(item);
        }

        // 更新商品库存
        for(SalesOrderItem item : salesOrder.getSalesOrderItems()){
            // 扣减库存
            inventoryService.updateMard(item.getMaterielId(), salesOrder.getStoreId(), -item.getQuantity());
        }

        return resultHandle;
    }

    @Override
    public List<SalesOrderVo> fetchSalesOrderVoList() {
        return salesDao.fetchSalesOrderVoList();
    }

    @Override
    public SalesOrder getSalesOrderWithItemsById(int id) {
        SalesOrder salesOrder = salesDao.getSalesOrderById(id);
        salesOrder.setSalesOrderItems(salesDao.fetchSalesOrderItems(id));
        return salesOrder;
    }

    @Override
    public ResultHandle<SalesOrder> updateSalesOrder(SalesOrder salesOrder) {
        ResultHandle<SalesOrder> resultHandle = new ResultHandle<>();
        checkSalesOrder(salesOrder, false);
        initSalesOrder(salesOrder, false);
        //更新销售单
        int result = salesDao.updateSalesOrder(salesOrder);
        if(result <= 0){
            resultHandle.setMsg("销售单更新失败");
        }
        return resultHandle;
    }

    /**
     * 初始化销售单信息
     * @param salesOrder
     * @param isNew 是否为新增销售单
     */
    private void initSalesOrder(SalesOrder salesOrder, boolean isNew) {
        if(salesOrder.getSaleDate() == null){
            salesOrder.setSaleDate(new Date());
        }
        if(isNew){
            salesOrder.setCode(getSalesOrderCode());
        }
    }

    /**
     * 获取销售单编号
     * @return
     */
    private String getSalesOrderCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(SALES_ORDER_CURRENT_CODE)){
            SALES_ORDER_CURRENT_CODE = salesDao.getMaxSalesOrderCode();
        }

        String nextCode = CodeBuilder.buildSalesOrderCode(SALES_ORDER_CURRENT_CODE);

        // 刷新缓存
        SALES_ORDER_CURRENT_CODE = nextCode;

        return nextCode;
    }

    /**
     * 获取退货单编号
     * @return
     */
    private String getRefundOrderCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(REFUND_ORDER_CURRENT_CODE)){
            REFUND_ORDER_CURRENT_CODE = salesDao.getMaxRefundOrderCode();
        }

        String nextCode = CodeBuilder.buildRefundOrderCode(REFUND_ORDER_CURRENT_CODE);

        // 刷新缓存
        REFUND_ORDER_CURRENT_CODE = nextCode;

        return nextCode;
    }

    /**
     * 验证销售单
     * @param salesOrder 销售单数据
     * @param isNew 是否为新增
     * @return 错误信息
     */
    private String checkSalesOrder(SalesOrder salesOrder, boolean isNew) {
        if(salesOrder.getStoreId() <= 0){
            return "请选择门店";
        }
        if(salesOrder.getCustomerId() <= 0){
            return "请选择客户";
        }
        if(isNew){
            if(CollectionUtils.isEmpty(salesOrder.getSalesOrderItems())){
                return "请添加销售明细";
            }
            // 验证库存
            for(SalesOrderItem item : salesOrder.getSalesOrderItems()){
                Mard mard = inventoryService.lockMard(item.getMaterielId(), salesOrder.getStoreId());
                if(mard == null){
                    return "商品库存不存在";
                }
                if(mard.getCurrentInventory() < item.getQuantity()){
                    return "商品库存不足";
                }
            }
            // 验证销售总金额和总数量
            if(salesOrder.getTotalQuantity() != salesOrder.getItemTotalQuantity()){
                return "销售总数量与明细总数量不一致";
            }
            if(salesOrder.getTotalAmount().compareTo(salesOrder.getItemTotalAmount()) != 0){
                return "销售总金额与明细总金额不一致";
            }
        }
        return null;
    }

    // TODO
    @Override
    public ResultHandle<SalesOrder> removeSalesOrder(int id) {
        ResultHandle<SalesOrder> resultHandle = new ResultHandle<>();

        SalesOrder salesOrder = getSalesOrderWithItemsById(id);
        if(salesOrder == null){
            resultHandle.setMsg("销售单不存在");
            return resultHandle;
        }

        // 删除销售单
        salesDao.removeSalesOrder(id);
        // 增加销售商品库存量
        if(CollectionUtils.isNotEmpty(salesOrder.getSalesOrderItems())){
            for(SalesOrderItem item : salesOrder.getSalesOrderItems()){
                inventoryService.updateMard(item.getMaterielId(), salesOrder.getStoreId(), item.getQuantity());
            }
        }

        return resultHandle;
    }

    @Override
    public ResultHandle<SalesOrderItem> removeSalesOrderItem(int itemId) {
        ResultHandle<SalesOrderItem> resultHandle = new ResultHandle<>();
        SalesOrderItem item = salesDao.getSalesOrderItemById(itemId);
        if(item == null){
            resultHandle.setMsg("销售单明细不存在");
            return resultHandle;
        }
        // 删除明细
        int result = salesDao.removeSalesOrderItem(itemId);
        if(result > 0){
            // 更新销售单金额和数量
            SalesOrder salesOrder = getSalesOrderWithItemsById(item.getSalesOrderId());
            updateSalesOrderTotalQuantityAndTotalAmount(salesOrder);
            // 更新库存,增加销售商品对应库存
            inventoryService.updateMard(item.getMaterielId(), salesOrder.getStoreId(), item.getQuantity());
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SalesOrderItem> saveSalesOrderItem(SalesOrderItem salesOrderItem) {
        ResultHandle<SalesOrderItem> resultHandle = new ResultHandle<>();

        SalesOrder salesOrder = salesDao.getSalesOrderById(salesOrderItem.getSalesOrderId());
        String errorMsg = checkSalesOrderItem(salesOrderItem, salesOrder);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 保存明细
        int result = salesDao.saveSalesOrderItem(salesOrderItem);
        if(result > 0){
            // 更新销售单数量和应收金额
            salesOrder = getSalesOrderWithItemsById(salesOrderItem.getSalesOrderId());
            updateSalesOrderTotalQuantityAndTotalAmount(salesOrder);

            // 更新库存,扣减库存
            inventoryService.updateMard(salesOrderItem.getMaterielId(), salesOrder.getStoreId(), -salesOrderItem.getQuantity());

            resultHandle.setReturnContent(salesOrderItem);
        }else {
            resultHandle.setMsg("明细添加失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<RefundOrder> saveRefundOrder(RefundOrder refundOrder) {
        ResultHandle<RefundOrder> resultHandle = new ResultHandle<>();
        // 验证
        String checkMsg = checkRefundOrder(refundOrder, true);
        if(StringUtils.isNotEmpty(checkMsg)){
            resultHandle.setMsg(checkMsg);
            return resultHandle;
        }
        // 初始化
        initRefundOrder(refundOrder, true);

        // 保存退货单
        salesDao.saveRefundOrder(refundOrder);

        // 保存销售明细
        for(RefundOrderItem item : refundOrder.getRefundOrderItems()){
            item.setRefundOrderId(refundOrder.getId());
            salesDao.saveRefundOrderItem(item);
        }

        // 更新商品库存
        for(RefundOrderItem item : refundOrder.getRefundOrderItems()){
            // 返还库存
            inventoryService.updateMard(item.getMaterielId(), refundOrder.getStoreId(), item.getQuantity());
        }

        return resultHandle;
    }

    /**
     * 初始化退货单信息
     * @param refundOrder
     * @param isNew 是否新增
     */
    private void initRefundOrder(RefundOrder refundOrder, boolean isNew) {
        if(refundOrder.getRefundDate() == null){
            refundOrder.setRefundDate(new Date());
        }
        if(isNew){
            refundOrder.setCode(getRefundOrderCode());
        }
    }

    /**
     * 验证退货单
     * @param refundOrder
     * @param isNew
     * @return
     */
    private String checkRefundOrder(RefundOrder refundOrder, boolean isNew) {
        if(refundOrder.getStoreId() <= 0){
            return "请选择门店";
        }
        if(refundOrder.getCustomerId() <= 0){
            return "请选择客户";
        }
        if(isNew){
            if(CollectionUtils.isEmpty(refundOrder.getRefundOrderItems())){
                return "请选择退货商品";
            }
            // 验证退货总金额和总数量
            if(refundOrder.getTotalQuantity() != refundOrder.getItemTotalQuantity()){
                return "退货单总数量与明细总数量不一致";
            }
            if(refundOrder.getTotalAmount().compareTo(refundOrder.getItemTotalAmount()) != 0){
                return "退货单总金额与明细总金额不一致";
            }
        }
        return null;
    }

    /**
     * 验证销售明细
     * @param salesOrderItem
     * @param salesOrder
     * @return
     */
    private String checkSalesOrderItem(SalesOrderItem salesOrderItem, SalesOrder salesOrder) {
        if(salesOrderItem.getMaterielId() <= 0){
            return "请选择商品";
        }
        if(salesOrderItem.getQuantity() <= 0){
            return "请填写数量";
        }
        if(salesOrderItem.getSellPrice() == null || salesOrderItem.getSellPrice().compareTo(BigDecimal.ZERO) <= 0){
            return "单价不正确";
        }
        // 验证商品库存
        Mard mard = inventoryService.lockMard(salesOrderItem.getMaterielId(), salesOrder.getStoreId());
        if(mard == null){
            return "商品库存不存在";
        }
        if(mard.getCurrentInventory() < salesOrderItem.getQuantity()){
            return "商品库存不足";
        }
        return null;
    }

    /**
     * 更新销售单总数量和应收金额
     * @param salesOrder
     */
    private void updateSalesOrderTotalQuantityAndTotalAmount(SalesOrder salesOrder) {
        salesOrder.setTotalQuantity(salesOrder.getItemTotalQuantity());
        salesOrder.setTotalAmount(salesOrder.getItemTotalAmount());
        salesDao.updateSalesOrderTotalQuantityAndTotalAmount(salesOrder);
    }
}