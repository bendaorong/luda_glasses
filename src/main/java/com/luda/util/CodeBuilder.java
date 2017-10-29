package com.luda.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * 业务员编生成器
 * Created by Administrator on 2017/10/21.
 */
public class CodeBuilder {
    /**
     * 生成商品编号
     * eg.1710200001
     * @param currentCode 当前最大商品编号
     * @return 下一个商品编号
     */
    public static String buildMaterielCode(String currentCode) {
        String today = DateFormatUtils.format(new Date(), "yyMMdd");

        // 当前编号为空，则取今天第一个
        if(StringUtils.isEmpty(currentCode)){
            return today + String.format("%04d", 1);
        }

        String dateSuffix = currentCode.substring(0, 6);
        String count = currentCode.substring(6);
        if(dateSuffix.equals(today)){
            return dateSuffix + String.format("%04d", Integer.parseInt(count) + 1);
        }else {
            return today + String.format("%04d", 1);
        }
    }

    /**
     * 生成采购入库单编号
     * eg.CR1710200001
     * @param currentCode 当前最大采购单编号
     * @return 下一个采购单编号
     */
    public static String buildPurchaseOrderCode(String currentCode) {
        String today = DateFormatUtils.format(new Date(), "yyMMdd");

        // 当前编号为空，则取今天第一个
        if(StringUtils.isEmpty(currentCode)){
            return "CR" + today + String.format("%04d", 1);
        }

        String dateSuffix = currentCode.substring(0, 8);
        String count = currentCode.substring(8);

        if(dateSuffix.equals("CR" + today)){
            return dateSuffix + String.format("%04d", Integer.parseInt(count) + 1);
        }else {
            return "CR" + today + String.format("%04d", 1);
        }
    }

    /**
     * 生成库存盘点单编号
     * @param currentCode
     * @return
     */
    public static String buildInvtVerifCode(String currentCode) {
        String today = DateFormatUtils.format(new Date(), "yyMMdd");

        // 当前编号为空，则取今天第一个
        if(StringUtils.isEmpty(currentCode)){
            return "PD" + today + String.format("%04d", 1);
        }

        String dateSuffix = currentCode.substring(0, 8);
        String count = currentCode.substring(8);

        if(dateSuffix.equals("PD" + today)){
            return dateSuffix + String.format("%04d", Integer.parseInt(count) + 1);
        }else {
            return "PD" + today + String.format("%04d", 1);
        }
    }

    /**
     * 生成调拨单编号
     * @param currentCode
     * @return
     */
    public static String buildTransferOrderCode(String currentCode) {
        String today = DateFormatUtils.format(new Date(), "yyMMdd");

        // 当前编号为空，则取今天第一个
        if(StringUtils.isEmpty(currentCode)){
            return "DB" + today + String.format("%04d", 1);
        }

        String dateSuffix = currentCode.substring(0, 8);
        String count = currentCode.substring(8);

        if(dateSuffix.equals("DB" + today)){
            return dateSuffix + String.format("%04d", Integer.parseInt(count) + 1);
        }else {
            return "DB" + today + String.format("%04d", 1);
        }
    }

    /**
     * 生成销售单编号
     * @param currentCode 当前编号
     * @return
     */
    public static String buildSalesOrderCode(String currentCode) {
        String prefix = "XS";
        String today = DateFormatUtils.format(new Date(), "yyMMdd");

        // 当前编号为空，则取今天第一个
        if(StringUtils.isEmpty(currentCode)){
            return prefix + today + String.format("%04d", 1);
        }

        String dateSuffix = currentCode.substring(0, 8);
        String count = currentCode.substring(8);

        if(dateSuffix.equals(prefix + today)){
            return dateSuffix + String.format("%04d", Integer.parseInt(count) + 1);
        }else {
            return prefix + today + String.format("%04d", 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(buildSalesOrderCode("XS171020003"));
    }
}
