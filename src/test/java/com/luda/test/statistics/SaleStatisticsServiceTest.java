package com.luda.test.statistics;

import com.luda.statistics.model.StatisticsCondition;
import com.luda.statistics.service.StatisticsService;
import com.luda.test.SpringSimpleJunit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
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
public class SaleStatisticsServiceTest extends SpringSimpleJunit {
    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testSaleStatisticsByMateriel() {
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        //condition.setTypeId(4);
        List list = statisticsService.saleStatisticsByMateriel(condition);
        System.out.println(list);
    }

    @Test
    public void saleStatisticsByAdminUser() {
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        List list = statisticsService.saleStatisticsByAdminUser(condition);
        System.out.println(list);
    }

    @Test
    public void saleStatisticsByStore() {
        StatisticsCondition condition = new StatisticsCondition();
        condition.setBeginDate("2017-10-10");
        condition.setEndDate("2017-11-19");
        List list = statisticsService.saleStatisticsByStore(condition);
        System.out.println(list);
    }

    @Test
    public void purchaseStatisticsByMateriel() {
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
                PlotOrientation.VERTICAL, // 图表方向：垂直
                false, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );
//重新设置图标标题，改变字体
        chart.setTitle(new TextTitle("图书销量统计图", new Font("黑体", Font.ITALIC, 22)));
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//设置柱状图到图片上端的距离
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.1);
//取得横轴
        CategoryAxis categoryAxis = plot.getDomainAxis();
//设置横轴显示标签的字体
        categoryAxis.setLabelFont(new Font("宋体", Font.BOLD, 22));
//分类标签以45度角倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 18));
//取得纵轴
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
//设置纵轴显示标签的字体
        numberAxis.setLabelFont(new Font("宋体", Font.BOLD, 22));
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

    private static CategoryDataset getDataSet2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        dataset.addValue(45000, "北京", "Spring2.0宝典");
        dataset.addValue(38000, "北京", "轻量级J2EE企业实战");
        dataset.addValue(24000, "北京", "基于J2EE的Ajax宝典");
        dataset.addValue(32000, "北京", "JavaScript权威指南");
        dataset.addValue(21000, "北京", "Ajax In Action");
        dataset.addValue(37000, "上海", "Spring2.0宝典");
        dataset.addValue(36000, "上海", "轻量级J2EE企业实战");
        dataset.addValue(34000, "上海", "基于J2EE的Ajax宝典");
        dataset.addValue(42000, "上海", "JavaScript权威指南");
        dataset.addValue(12000, "上海", "Ajax In Action");
        dataset.addValue(42000, "广州", "Spring2.0宝典");
        dataset.addValue(40000, "广州", "轻量级J2EE企业实战");
        dataset.addValue(34000, "广州", "基于J2EE的Ajax宝典");
        dataset.addValue(18000, "广州", "JavaScript权威指南");
        dataset.addValue(26000, "广州", "Ajax In Action");
        return dataset;
    }


    @Test
    public void bbb() {
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
        renderer.setBaseItemLabelFont(new Font("宋书", Font.PLAIN, 15));
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    public static void main(String[] args) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(70588, "护理产品", "护理产品");
        ds.addValue(168208, "隐形眼镜", "隐形眼镜");
        ds.addValue(1745245, "镜架", "镜架");
        ds.addValue(275021, "太阳镜", "太阳镜");
        ds.addValue(2058596, "镜片", "镜片");
        ds.addValue(2058596, "老花镜", "老花镜");
        System.out.println("rowcount:" + ds.getRowCount() + " columncount:" + ds.getColumnCount());

        JFreeChart chart = ChartFactory.createBarChart3D("销售统计分析图",
                "商品类型",
                "销量",
                ds,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

/*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
//底部汉字乱码的问题
        //chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 16));
//设置标题字体
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("宋体", Font.PLAIN, 20));
        //textTitle.setBackgroundPaint(Color.LIGHT_GRAY);//标题背景色
        //textTitle.setPaint(Color.cyan);//标题字体颜色
        textTitle.setText("类型统计图");//标题内容


        CategoryPlot plot = chart.getCategoryPlot();//设置图的高级属性
        BarRenderer3D renderer = new BarRenderer3D();//3D属性修改
        CategoryAxis domainAxis = plot.getDomainAxis();//对X轴做操作
        ValueAxis rAxis = plot.getRangeAxis();//对Y轴做操作

        // 解决中文乱码问题(关键)
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
        rAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

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
        domainAxis.setUpperMargin(0.2);//设置距离图片左端距离
        domainAxis.setLowerMargin(0.2); //设置距离图片右端距离
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
//设置最高的一个 Item 与图片顶端的距离 (在设置rAxis.setRange(100, 600);情况下不起作用)
        rAxis.setUpperMargin(0.15);
//设置最低的一个 Item 与图片底端的距离
        rAxis.setLowerMargin(0.15);
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

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\bbb.png");
            ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
            System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
