package com.luda.test.sales;

import com.luda.comm.po.ResultHandle;
import com.luda.sales.model.SalesOrder;
import com.luda.sales.model.SalesOrderItem;
import com.luda.sales.model.SalesOrderVo;
import com.luda.sales.service.SalesService;
import com.luda.test.SpringSimpleJunit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */
public class SaleServiceTest extends SpringSimpleJunit{
    @Autowired
    private SalesService salesService;

    @Test
    public void testSaveSalesOrder(){
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setStoreId(2);
        salesOrder.setBusinessManId(1);
        salesOrder.setCustomerId(1);
        salesOrder.setPickUpDate(new Date());
        salesOrder.setTotalQuantity(1);
        salesOrder.setTotalAmount(new BigDecimal(10));
        salesOrder.setRemark("验光师：张贤胜");

        List<SalesOrderItem> items = new ArrayList<>();
        salesOrder.setSalesOrderItems(items);

        SalesOrderItem item = new SalesOrderItem();
        item.setMaterielId(6);
        item.setQuantity(1);
        item.setSellPrice(new BigDecimal(10));
        item.setRemark("九五折");

        items.add(item);

        ResultHandle<SalesOrder> resultHandle = salesService.saveSalesOrder(salesOrder);
        print(resultHandle.toString());
    }

    @Test
    public void testFetchSalesOrderVoList(){
        List<SalesOrderVo> list = salesService.fetchSalesOrderVoList();
        print(list.toString());
    }

    @Test
    public void testGetSalesOrderWithItemsById(){
        SalesOrder salesOrder = salesService.getSalesOrderWithItemsById(1);
        print(salesOrder.toString());
    }

    @Test
    public void testUpdateSalesOrder(){
        SalesOrder salesOrder = salesService.getSalesOrderWithItemsById(1);
        salesOrder.setRemark("111");
        ResultHandle<SalesOrder> resultHandle = salesService.updateSalesOrder(salesOrder);
        print(resultHandle.toString());
    }
}
