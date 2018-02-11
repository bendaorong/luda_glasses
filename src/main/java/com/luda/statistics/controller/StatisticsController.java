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
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
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

        //创建主题样式
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋体",Font.BOLD,20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋体",Font.PLAIN,12));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋体",Font.PLAIN,12));
        //应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        JFreeChart chart = ChartFactory.createBarChart3D("销售统计分析图",
                getCategoryAxisLabel(dimension),
                "销量",
                ds,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        // 副标题
        chart.setSubtitles(getSubtitles(ds));
        // 设置消除字体的锯齿渲染
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        //底部汉字乱码的问题
        //chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 16));
        //设置标题字体
        //TextTitle textTitle = chart.getTitle();
        //textTitle.setFont(new Font("宋体", Font.PLAIN, 20));

        //textTitle.setBackgroundPaint(Color.LIGHT_GRAY);//标题背景色
        //textTitle.setPaint(Color.cyan);//标题字体颜色
        //textTitle.setText("类型统计图");//标题内容


        CategoryPlot plot = chart.getCategoryPlot();//设置图的高级属性
        BarRenderer3D renderer = new BarRenderer3D();//3D属性修改
        CategoryAxis domainAxis = plot.getDomainAxis();//对X轴做操作
        ValueAxis rAxis = plot.getRangeAxis();//对Y轴做操作

        // 解决中文乱码问题(关键)
        //domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        //domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        //rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        //rAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

/***
 * domainAxis设置(x轴一些设置)
 **/

////设置X轴坐标上的文字
//        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
////设置X轴的标题文字
//        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//        domainAxis.setLabel("");//X轴的标题内容
//        domainAxis.setTickLabelPaint(Color.red);//X轴的标题文字颜色
//        domainAxis.setTickLabelsVisible(true);//X轴的标题文字是否显示
//        domainAxis.setAxisLinePaint(Color.red);//X轴横线颜色
//        domainAxis.setTickMarksVisible(true);//标记线是否显示
//        domainAxis.setTickMarkOutsideLength(3);//标记线向外长度
//        domainAxis.setTickMarkInsideLength(3);//标记线向内长度
//        domainAxis.setTickMarkPaint(Color.red);//标记线颜色
        domainAxis.setUpperMargin(0.1);//设置距离图片左端距离
        domainAxis.setLowerMargin(0.1); //设置距离图片右端距离
////横轴上的 Lable 是否完整显示
//        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);
////横轴上的 Lable 45度倾斜
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

/**
 * rAxis设置 Y轴设置
 *
 **/

////设置Y轴坐标上的文字
//        rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
////设置Y轴的标题文字
//        rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
////Y轴取值范围（下面不能出现 rAxis.setAutoRange(true) 否则不起作用）
//        rAxis.setRange(100, 600);
//// rAxis.setLowerBound(100); //Y轴以开始的最小值
//// rAxis.setUpperBound(600);//Y轴的最大值
//        rAxis.setLabel("content");//Y轴内容
//        rAxis.setLabelAngle(1.6);//标题内容显示角度（1.6时候水平）
//        rAxis.setLabelPaint(Color.red);//标题内容颜色
//        rAxis.setMinorTickMarksVisible(true);//标记线是否显示
//        rAxis.setMinorTickCount(7);//节段中的刻度数
//        rAxis.setMinorTickMarkInsideLength(3);//内刻度线向内长度
//        rAxis.setMinorTickMarkOutsideLength(3);//内刻度记线向外长度
//        rAxis.setTickMarkInsideLength(3);//外刻度线向内长度
//        rAxis.setTickMarkPaint(Color.red);//刻度线颜色
//        rAxis.setTickLabelsVisible(true);//刻度数值是否显示
//// 所有Y标记线是否显示（如果前面设置rAxis.setMinorTickMarksVisible(true); 则其照样显示）
//        rAxis.setTickMarksVisible(true);
//        rAxis.setAxisLinePaint(Color.red);//Y轴竖线颜色
//        rAxis.setAxisLineVisible(true);//Y轴竖线是否显示
////设置最高的一个 Item 与图片顶端的距离 (在设置rAxis.setRange(100, 600);情况下不起作用)
        rAxis.setUpperMargin(0.1);
////设置最低的一个 Item 与图片底端的距离
        rAxis.setLowerMargin(0.1);
//        rAxis.setAutoRange(true);//是否自动适应范围
//        rAxis.setVisible(true);//Y轴内容是否显示

//数据轴精度
        NumberAxis na = (NumberAxis) plot.getRangeAxis();
        na.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0.000");
//数据轴数据标签的显示格式
        na.setNumberFormatOverride(df);

/**
 * renderer设置 柱子相关属性设置
 */
        //renderer.setBaseOutlinePaint(Color.ORANGE); //边框颜色
        //renderer.setDrawBarOutline(true);
        //renderer.setWallPaint(Color.gray);//设置墙体颜色
        renderer.setMaximumBarWidth(0.08); //设置柱子宽度
        renderer.setMinimumBarLength(0.1); //设置柱子高度

        for(int i=0; i<ds.getColumnCount(); i++){
            renderer.setSeriesPaint(i, Color.GREEN); //设置柱的颜色
        }

        renderer.setItemMargin(0); //设置每个地区所包含的平行柱的之间距离
//在柱子上显示相应信息
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelPaint(Color.BLACK);//设置数值颜色，默认黑色
//搭配ItemLabelAnchor TextAnchor 这两项能达到不同的效果，但是ItemLabelAnchor最好选OUTSIDE，因为INSIDE显示不出来
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
//下面可以进一步调整数值的位置，但是得根据ItemLabelAnchor选择情况.
        renderer.setItemLabelAnchorOffset(15);

/**
 * plot 设置
 ***/
//设置网格竖线颜色
        //plot.setDomainGridlinePaint(Color.blue);
        //plot.setDomainGridlinesVisible(true);
//设置网格横线颜色
        //plot.setRangeGridlinePaint(Color.blue);

        //plot.setRangeGridlinesVisible(true);
//图片背景色
        //plot.setBackgroundPaint(Color.LIGHT_GRAY);
        //plot.setOutlineVisible(true);
//图边框颜色
        //plot.setOutlinePaint(Color.magenta);
//设置柱的透明度
        plot.setForegroundAlpha(1.0f);
//将类型放到上面
        //plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
//将默认放到左边的数值放到右边
        //plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.setRenderer(renderer);//将修改后的属性值保存到图中

        OutputStream out = null;
        try {
            response.setContentType("image/png");
            out = response.getOutputStream();
            ChartUtilities.writeChartAsPNG(out, chart, 600, 400);
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
     * 获取x轴名称
     * @param dimension
     * @return
     */
    private String getCategoryAxisLabel(int dimension) {
        //return dimension == 1 ? "商品类型" : (dimension == 2 ? "门店" : "销售统计");
        return "";
    }

    /**
     * 生成统计图表的副标题
     * @param ds
     * @return
     */
    private List getSubtitles(CategoryDataset ds) {
        double totalAmount = 0; //总销售额
        int rowcount = ds.getRowCount();
        int columncount = ds.getColumnCount();
        for(int i=0; i<rowcount; i++){
            for(int j=0; j<columncount; j++){
                if(ds.getValue(i, j) != null){
                    totalAmount += ds.getValue(i, j).doubleValue();
                }
            }
        }

        List<Title> titles = new ArrayList<>();
        TextTitle title = new TextTitle("商品总金额：¥ " + totalAmount);
        title.setFont(new Font("宋体", Font.PLAIN, 16));
        titles.add(title);
        return titles;
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
            List<SaleStatisticsChartByStore> list = statisticsService.saleStatisticsChartByStore(statisticsCondition);
            DefaultCategoryDataset ds = new DefaultCategoryDataset();
            if(list != null && list.size() > 0){
                for(SaleStatisticsChartByStore type : list){
                    ds.addValue(type.getAmount(), type.getStoreName(), type.getStoreName());
                }
            }
            return ds;
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
