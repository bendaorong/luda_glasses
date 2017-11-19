package com.luda.statistics.service.impl;

import com.luda.statistics.dao.SaleStatisticsDao;
import com.luda.statistics.model.SaleStatisticsByMateriel;
import com.luda.statistics.model.SaleStatisticsByUser;
import com.luda.statistics.model.StatisticsCondition;
import com.luda.statistics.service.SaleStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
@Service("saleStatisticsService")
public class SaleStatisticsServiceImpl implements SaleStatisticsService{
    @Autowired
    private SaleStatisticsDao saleStatisticsDao;

    @Override
    public List<SaleStatisticsByMateriel> saleStatisticsByMateriel(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return saleStatisticsDao.saleStatisticsByMateriel(statisticsCondition);
    }

    @Override
    public List<SaleStatisticsByUser> saleStatisticsByAdminUser(StatisticsCondition statisticsCondition) {
        if(StringUtils.isEmpty(statisticsCondition.getBeginDate())){
            statisticsCondition.setBeginDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(statisticsCondition.getEndDate())){
            statisticsCondition.setEndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        }
        return saleStatisticsDao.saleStatisticsByAdminUser(statisticsCondition);
    }
}
