package com.luda.statistics.dao;

import com.luda.statistics.model.SaleStatisticsByMateriel;
import com.luda.statistics.model.SaleStatisticsByStore;
import com.luda.statistics.model.SaleStatisticsByUser;
import com.luda.statistics.model.StatisticsCondition;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
public interface SaleStatisticsDao {
    List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition);

    List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition);

    List<SaleStatisticsByStore> saleStatisticsByStore(StatisticsCondition statisticsCondition);
}
