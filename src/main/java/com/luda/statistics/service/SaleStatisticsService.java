package com.luda.statistics.service;

import com.luda.statistics.model.SaleStatisticsByMateriel;
import com.luda.statistics.model.SaleStatisticsByStore;
import com.luda.statistics.model.SaleStatisticsByUser;
import com.luda.statistics.model.StatisticsCondition;

import java.util.List;

/**
 * 销售报表服务
 * Created by Administrator on 2017/11/18.
 */
public interface SaleStatisticsService {
    /**
     * 按商品统计
     */
    List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition);

    /**
     * 按用户统计
     */
    List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition);

    /**
     * 按门店统计
     */
    List<SaleStatisticsByStore> saleStatisticsByStore(StatisticsCondition statisticsCondition);
}
