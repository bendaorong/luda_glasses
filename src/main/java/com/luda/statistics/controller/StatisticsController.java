package com.luda.statistics.controller;

import com.luda.common.controller.BaseController;
import com.luda.statistics.model.*;
import com.luda.statistics.service.StatisticsService;
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
 * 报表
 * Created by Administrator on 2017/11/18.
 */
@Controller
@RequestMapping("/rest/statistics")
public class StatisticsController extends BaseController{
    private static Logger log = Logger.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 销售报表-按商品统计
     * @param data
     * @return
     */
    @RequestMapping("/sale/byMateriel")
    @ResponseBody
    public String saleStatisticsByMateriel(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByMateriel> saleStatisticsList = statisticsService.saleStatisticsByMateriel(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 销售报表-按用户(业务员)统计
     */
    @RequestMapping("/sale/byAdminUser")
    @ResponseBody
    public String saleStatisticsByAdminUser(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByUser> saleStatisticsList = statisticsService.saleStatisticsByAdminUser(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 销售报表-按门店统计
     */
    @RequestMapping("/sale/byStore")
    @ResponseBody
    public String saleStatisticsByStore(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<SaleStatisticsByStore> saleStatisticsList = statisticsService.saleStatisticsByStore(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(saleStatisticsList, null).toString());
        }catch (Exception e){
            log.error("sale statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 采购报表-按商品统计
     */
    @RequestMapping("/purchase/byMateriel")
    @ResponseBody
    public String purchaseStatisticsByMateriel(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<PurchaseStatisticsByMateriel> purchaseStatisticsList = statisticsService.purchaseStatisticsByMateriel(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(purchaseStatisticsList, null).toString());
        }catch (Exception e){
            log.error("purchase statistics by materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 采购报表-按供应商统计
     */
    @RequestMapping("/purchase/bySupplier")
    @ResponseBody
    public String purchaseStatisticsBySupplier(@RequestBody String data){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            List<PurchaseStatisticsBySupplier> purchaseStatisticsList = statisticsService.purchaseStatisticsBySupplier(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(purchaseStatisticsList, null).toString());
        }catch (Exception e){
            log.error("purchase statistics by supplier error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
