package com.luda.statistics.controller;

import com.luda.common.controller.BaseController;
import com.luda.statistics.model.SaleStatisticsByMateriel;
import com.luda.statistics.model.SaleStatisticsByStore;
import com.luda.statistics.model.SaleStatisticsByUser;
import com.luda.statistics.model.StatisticsCondition;
import com.luda.statistics.service.SaleStatisticsService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 销售统计报表
 * Created by Administrator on 2017/11/18.
 */
@Controller
@RequestMapping("/rest/statistics/sale")
public class SaleStatisticsController extends BaseController{
    private static Logger log = Logger.getLogger(SaleStatisticsController.class);

    @Autowired
    private SaleStatisticsService saleStatisticsService;

    /**
     * 按商品统计
     * @param data
     * @return
     */
    @RequestMapping("/byMateriel")
    @ResponseBody
    public String saleStatisticsByMateriel(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByMateriel> saleStatisticsList = saleStatisticsService.saleStatisticsByMateriel(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 按用户(业务员)统计
     */
    @RequestMapping("/byAdminUser")
    @ResponseBody
    public String saleStatisticsByAdminUser(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByUser> saleStatisticsList = saleStatisticsService.saleStatisticsByAdminUser(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 按门店统计
     */
    @RequestMapping("/byStore")
    @ResponseBody
    public String saleStatisticsByStore(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByStore> saleStatisticsList = saleStatisticsService.saleStatisticsByStore(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
