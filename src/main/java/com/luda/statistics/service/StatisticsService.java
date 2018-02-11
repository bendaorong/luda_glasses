package com.luda.statistics.service;

import com.luda.statistics.model.*;

import java.util.List;

/**
 * 报表服务
 * Created by Administrator on 2017/11/18.
 */
public interface StatisticsService {
    /**
     * 销售报表-按商品统计
     */
    List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition);

    /**
     * 销售报表-按用户统计
     */
    List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition);

    /**
     * 销售报表-按门店统计
     */
    List<SaleStatisticsByStore> saleStatisticsByStore(StatisticsCondition statisticsCondition);

    /**
     * 采购报表-按商品统计
     * @param statisticsCondition
     * @return
     */
    List<PurchaseStatisticsByMateriel> purchaseStatisticsByMateriel(StatisticsCondition statisticsCondition);

    /**
     * 采购报表-按供应商统计
     */
    List<PurchaseStatisticsBySupplier> purchaseStatisticsBySupplier(StatisticsCondition statisticsCondition);

    List<SaleStatisticsChartByMaterielType> saleStatisticsByMaterielType(StatisticsCondition statisticsCondition);

    List<SaleStatisticsChartByStore> saleStatisticsChartByStore(StatisticsCondition statisticsCondition);
}
