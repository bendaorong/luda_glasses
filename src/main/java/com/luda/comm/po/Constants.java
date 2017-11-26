package com.luda.comm.po;

/**
 * 常量类
 * Created by Administrator on 2017/10/6.
 */
public class Constants {
    /**
     * 门店总店标示
     */
    public static final String STORE_HEAD_OFFICE_FLAG_Y = "Y";  // 总店
    public static final String STORE_HEAD_OFFICE_FLAG_N = "N";  // 分店

    /**
     * 性别
     */
    public static final String GENDER_MALE = "M"; //男
    public static final String GENDER_FEMALE = "F"; //女

    /**
     * 盘点类型
     */
    public static final String INVNT_VERIF_TYPE_WIN = "01"; //盘盈
    public static final String INVNT_VERIF_TYPE_LOSE = "02"; //盘亏

    /**
     * 销售订单类型
     */
    public static final String ORDER_TYPE_SALE = "01"; //销售单
    public static final String ORDER_TYPE_REFUND = "02"; //销售退货单

    /**
     * 采购订单类型
     */
    public static final String ORDER_TYPE_PURCHASE = "01"; //采购单
    public static final String ORDER_TYPE_PURCHASE_REFUND = "02"; //采购退货单

    /**
     * 角色
     */
    public static final String ROLE_SUPERMANAGER = "01"; //总经理
    public static final String ROLE_MANAGER = "02"; //经理
    public static final String ROLE_OPTOMETRIST = "03"; //验光师
    public static final String ROLE_SALES = "04"; //业务员
}
