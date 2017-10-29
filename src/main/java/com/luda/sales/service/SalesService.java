package com.luda.sales.service;

import com.luda.comm.po.ResultHandle;
import com.luda.sales.model.SalesOrder;
import com.luda.sales.model.SalesOrderItem;
import com.luda.sales.model.SalesOrderVo;

import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */
public interface SalesService {
    /**
     * 新增销售单
     */
    ResultHandle<SalesOrder> saveSalesOrder(SalesOrder salesOrder);

    /**
     * 查询销售单列表
     */
    List<SalesOrderVo> fetchSalesOrderVoList();

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

    ResultHandle<SalesOrderItem> saveSalesOrderItem(SalesOrderItem salesOrderItem);
}
