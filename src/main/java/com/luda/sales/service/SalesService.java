package com.luda.sales.service;

import com.luda.comm.po.ResultHandle;
import com.luda.sales.model.*;

import javax.xml.transform.Result;
import java.util.List;

/**
 * 销售服务
 * Created by Administrator on 2017/10/28.
 */
public interface SalesService {
    /**
     * 新增销售单
     */
    ResultHandle<SalesOrder> saveSalesOrder(SalesOrder salesOrder);

    /**
     * 查询销售单列表
     * @param queryBean 查询条件
     */
    List<SalesOrderVo> fetchSalesOrderVoList(SalesOrderQueryBean queryBean);

    /**
     * 查询销售单详情
     * @param id 销售单id
     */
    SalesOrder getSalesOrderWithItemsById(int id);

    /**
     * 更新销售单
     * @param salesOrder
     */
    ResultHandle<SalesOrder> updateSalesOrder(SalesOrder salesOrder);

    /**
     * 删除销售单
     * @param id 销售单id
     */
    ResultHandle<SalesOrder> removeSalesOrder(int id);

    /**
     * 删除销售单明细
     * @param itemId
     * @return
     */
    ResultHandle<SalesOrderItem> removeSalesOrderItem(int itemId);

    /**
     * 保存销售单明细
     * @param salesOrderItem
     * @return
     */
    ResultHandle<SalesOrderItem> saveSalesOrderItem(SalesOrderItem salesOrderItem);

    /**
     * 查询客户消费记录
     * @param customerId 客户id
     *
     */
    List<SalesOrderVo> fetchSalesOrderVoByCustomerId(int customerId);

    /**
     * 销售单退货
     * @param id
     * @return
     */
    ResultHandle<SalesOrder> orderRefund(int id);
}
