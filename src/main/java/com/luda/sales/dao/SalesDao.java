package com.luda.sales.dao;

import com.luda.sales.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */
public interface SalesDao {
    String getMaxSalesOrderCode();

    int saveSalesOrder(SalesOrder salesOrder);

    int saveSalesOrderItem(SalesOrderItem item);

    List<SalesOrderVo> fetchSalesOrderVoList(SalesOrderQueryBean queryBean);

    SalesOrder getSalesOrderById(int id);

    List<SalesOrderItem> fetchSalesOrderItems(int salesOrderId);

    int updateSalesOrder(SalesOrder salesOrder);

    SalesOrderItem getSalesOrderItemById(int itemId);

    int removeSalesOrderItem(int itemId);

    void updateSalesOrderTotalQuantityAndTotalAmount(SalesOrder salesOrder);

    int removeSalesOrder(int id);

    String getMaxRefundOrderCode();

    int saveRefundOrder(RefundOrder refundOrder);

    int saveRefundOrderItem(RefundOrderItem item);

    int updateRelatedOrderId(@Param("id") int id, @Param("relatedOrderId") int relatedOrderId);
}
