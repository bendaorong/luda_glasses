package com.luda.statistics.dao;

import com.luda.statistics.model.*;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
public interface StatisticsDao {
    List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition);

    List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition);

    List<SaleStatisticsByStore> saleStatisticsByStore(StatisticsCondition statisticsCondition);

    List<PurchaseStatisticsByMateriel> purchaseStatisticsByMateriel(StatisticsCondition statisticsCondition);

    List<PurchaseStatisticsBySupplier> purchaseStatisticsBySupplier(StatisticsCondition statisticsCondition);
}
