package com.luda.statistics.service.impl;

import com.luda.statistics.dao.StatisticsDao;
import com.luda.statistics.model.*;
import com.luda.statistics.service.StatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService{
    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return statisticsDao.saleStatisticsByMateriel(statisticsCondition);
    }

    @Override
    public List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return statisticsDao.saleStatisticsByAdminUser(statisticsCondition);
    }

    @Override
    public List<SaleStatisticsByStore> saleStatisticsByStore(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return statisticsDao.saleStatisticsByStore(statisticsCondition);
    }

    @Override
    public List<PurchaseStatisticsByMateriel> purchaseStatisticsByMateriel(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return statisticsDao.purchaseStatisticsByMateriel(statisticsCondition);
    }

    @Override
    public List<PurchaseStatisticsBySupplier> purchaseStatisticsBySupplier(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return statisticsDao.purchaseStatisticsBySupplier(statisticsCondition);
    }
}
