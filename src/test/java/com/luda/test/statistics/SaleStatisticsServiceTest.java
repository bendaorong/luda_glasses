package com.luda.test.statistics;

import com.luda.statistics.model.StatisticsCondition;
import com.luda.statistics.service.StatisticsService;
import com.luda.test.SpringSimpleJunit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
public class SaleStatisticsServiceTest extends SpringSimpleJunit{
    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testSaleStatisticsByMateriel(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        //condition.setTypeId(4);
        List list = statisticsService.saleStatisticsByMateriel(condition);
        System.out.println(list);
    }

    @Test
    public void saleStatisticsByAdminUser(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        List list = statisticsService.saleStatisticsByAdminUser(condition);
        System.out.println(list);
    }

    @Test
    public void saleStatisticsByStore(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        List list = statisticsService.saleStatisticsByStore(condition);
        System.out.println(list);
    }

    @Test
    public void purchaseStatisticsByMateriel(){
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-11-10");
        condition.setEndDate("2017-11-19");
        List list = statisticsService.purchaseStatisticsByMateriel(condition);
        System.out.println(list);
    }

    /**
     * 统计图表
     */
    @Test
    public void jfreechart() throws IOException {
        JFreeChart chart = ChartFactory.createBarChart3D(
                "图书销量统计图", // 图表标题
                "图书", // 目录轴的显示标签
                "销量", // 数值轴的显示标签
                getDataSet2(), // 数据集
//PlotOrientation.HORIZONTAL , // 图表方向：水平
                PlotOrientation.VERTICAL , // 图表方向：垂直
                false, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );
//重新设置图标标题，改变字体
        chart.setTitle(new TextTitle("图书销量统计图", new Font("黑体", Font.ITALIC , 22)));
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
//设置柱状图到图片上端的距离
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.1);
//取得横轴
        CategoryAxis categoryAxis = plot.getDomainAxis();
//设置横轴显示标签的字体
        categoryAxis.setLabelFont(new Font("宋体" , Font.BOLD , 22));
//分类标签以45度角倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        categoryAxis.setTickLabelFont(new Font("宋体" , Font.BOLD , 18));
//取得纵轴
        NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
//设置纵轴显示标签的字体
        numberAxis.setLabelFont(new Font("宋体" , Font.BOLD , 22));
//在柱体的上面显示数据
        BarRenderer custombarrenderer3d = new BarRenderer();
        custombarrenderer3d.setBaseItemLabelPaint(Color.BLACK);//数据字体的颜色
        custombarrenderer3d
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        custombarrenderer3d.setBaseItemLabelsVisible(true);
        plot.setRenderer(custombarrenderer3d);

        FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\aaa.png");
        ChartUtilities.writeChartAsPNG(fos, chart, 800, 600);
        fos.close();
    }

    private static CategoryDataset getDataSet2()
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        dataset.addValue(45000 , "北京" , "Spring2.0宝典");
        dataset.addValue(38000 , "北京" , "轻量级J2EE企业实战");
        dataset.addValue(24000 , "北京" , "基于J2EE的Ajax宝典");
        dataset.addValue(32000 , "北京" , "JavaScript权威指南");
        dataset.addValue(21000 , "北京" , "Ajax In Action");
        dataset.addValue(37000 , "上海" , "Spring2.0宝典");
        dataset.addValue(36000 , "上海" , "轻量级J2EE企业实战");
        dataset.addValue(34000 , "上海" , "基于J2EE的Ajax宝典");
        dataset.addValue(42000 , "上海" , "JavaScript权威指南");
        dataset.addValue(12000 , "上海" , "Ajax In Action");
        dataset.addValue(42000 , "广州" , "Spring2.0宝典");
        dataset.addValue(40000 , "广州" , "轻量级J2EE企业实战");
        dataset.addValue(34000 , "广州" , "基于J2EE的Ajax宝典");
        dataset.addValue(18000 , "广州" , "JavaScript权威指南");
        dataset.addValue(26000 , "广州" , "Ajax In Action");
        return dataset;
    }



    @Test
    public void bbb(){
        CategoryDataset ds = getDataSetbbb();
        JFreeChart chart = ChartFactory.createBarChart3D(
                "销售统计分析图\n商品总金额:¥ 4467201", //图表标题
                null, //目录轴的显示标签
                null, //数值轴的显示标签
                ds, //数据集
                PlotOrientation.VERTICAL, //图表方向
                true, //是否显示图例，对于简单的柱状图必须为false
                false, //是否生成提示工具
                false);         //是否生成url链接

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();

        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelPaint(Color.BLACK);
        renderer.setBaseItemLabelFont(new Font("宋书",Font.PLAIN,15));
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER));

        categoryplot.setRenderer(renderer);

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

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\bbb.png");
            ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static CategoryDataset getDataSetbbb() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(70588, "护理产品", "护理产品");
        ds.addValue(168208, "隐形眼镜", "隐形眼镜");
        ds.addValue(1745245, "镜架", "镜架");
        ds.addValue(275021, "太阳镜", "太阳镜");
        ds.addValue(2058596, "镜片", "镜片");
        ds.addValue(138574, "老花成镜", "老花成镜");
        return ds;
    }
}
