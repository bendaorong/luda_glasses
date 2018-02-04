package com.luda.statistics.controller;

import com.luda.common.controller.BaseController;
import com.luda.statistics.model.*;
import com.luda.statistics.service.StatisticsService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.OutputStream;
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
     * 销售统计图表
     * @param dimension 类型 1-按商品类型 2-按门店
     */
    @RequestMapping("/sale/chart/{dimension}/{beginDate}/{endDate}")
    public void salesStatisticsChart(@PathVariable int dimension, @PathVariable String beginDate, @PathVariable String endDate, HttpServletResponse response){
        StatisticsCondition statisticsCondition = new StatisticsCondition();
        statisticsCondition.setBeginDate(beginDate);
        statisticsCondition.setEndDate(endDate);
        statisticsCondition.setDimension(dimension);

        CategoryDataset ds = getDataSet(statisticsCondition);
        JFreeChart chart = ChartFactory.createBarChart3D(
                "水果产量图", //图表标题
                "水果", //目录轴的显示标签
                "产量", //数值轴的显示标签
                ds, //数据集
                PlotOrientation.VERTICAL, //图表方向
                true, //是否显示图例，对于简单的柱状图必须为false
                false, //是否生成提示工具
                false);         //是否生成url链接

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();

        CategoryAxis domainAxis = categoryplot.getDomainAxis();

            /*------设置X轴坐标上的文字-----------*/
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));

            /*------设置X轴的标题文字------------*/
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

            /*------设置Y轴坐标上的文字-----------*/
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));

            /*------设置Y轴的标题文字------------*/
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

            /*------这句代码解决了底部汉字乱码的问题-----------*/
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

        /*******这句代码解决了标题汉字乱码的问题********/
        chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 12));

        OutputStream out = null;
        try {
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ChartUtilities.writeChartAsJPEG(out, 0.5f, chart, 400, 300, null);
        } catch (Exception e){
            log.error("salesStatisticsChart error", e);
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 查询销售报表统计数据
     * @param statisticsCondition
     * @return
     */
    private CategoryDataset getDataSet(StatisticsCondition statisticsCondition) {
        if(statisticsCondition.getDimension() == 1){ //按商品类型
            List<SaleStatisticsChartByMaterielType> list = statisticsService.saleStatisticsByMaterielType(statisticsCondition);
            DefaultCategoryDataset ds = new DefaultCategoryDataset();
            if(list != null && list.size() > 0){
                for(SaleStatisticsChartByMaterielType type : list){
                    ds.addValue(type.getAmount(), type.getTypeName(), type.getTypeName());
                }
            }
            return ds;
        }else if(statisticsCondition.getDimension() == 2){ // 按门店

        }
        return null;
    }

    /**
     * 销售报表-按商品统计
     * @param data
     * @return
     */
    @RequestMapping("/sale/byMateriel")
    @ResponseBody
    public String saleStatisticsByMateriel(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            // 非总经理只统计本店的数据
            if(!isSuperManage(httpSession)){
                statisticsCondition.setStoreId(getStoreId(httpSession));
            }
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
    public String saleStatisticsByAdminUser(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            // 非总经理只统计本店的数据
            if(!isSuperManage(httpSession)){
                statisticsCondition.setStoreId(getStoreId(httpSession));
            }
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
    public String purchaseStatisticsByMateriel(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            if(!isSuperManage(httpSession)){
                statisticsCondition.setStoreId(getStoreId(httpSession));
            }
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
    public String purchaseStatisticsBySupplier(@RequestBody String data, HttpSession httpSession){
        String result = "";
        try {
            StatisticsCondition statisticsCondition = CommonUtils.convertJsonToBean(
                    JSONObject.fromObject(data), StatisticsCondition.class);
            if(!isSuperManage(httpSession)){
                statisticsCondition.setStoreId(getStoreId(httpSession));
            }
            List<PurchaseStatisticsBySupplier> purchaseStatisticsList = statisticsService.purchaseStatisticsBySupplier(statisticsCondition);
            result = getSuccessResult(CommonUtils.convertBeanCollectionToJsonArray(purchaseStatisticsList, null).toString());
        }catch (Exception e){
            log.error("purchase statistics by supplier error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
