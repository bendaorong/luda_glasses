package com.luda.test.statistics;

import com.luda.statistics.model.StatisticsCondition;
import com.luda.statistics.service.SaleStatisticsService;
import com.luda.test.SpringSimpleJunit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
public class SaleStatisticsServiceTest extends SpringSimpleJunit{
    @Autowired
    private SaleStatisticsService saleStatisticsService;

    @Test
    public void testSaleStatisticsByMateriel(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        //condition.setTypeId(4);
        List list = saleStatisticsService.saleStatisticsByMateriel(condition);
        System.out.println(list);
    }

    @Test
    public void saleStatisticsByAdminUser(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        List list = saleStatisticsService.saleStatisticsByAdminUser(condition);
        System.out.println(list);
    }
}
