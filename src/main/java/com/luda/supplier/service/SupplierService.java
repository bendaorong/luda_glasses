package com.luda.supplier.service;

import com.luda.comm.po.ResultHandle;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface SupplierService {
    /**
     * 获取供应商列表
     * @return
     */
    List<SupplierModel> fetchSupplierList();

    /**
     * 新增供应商
     * @param supplierModel 供应商
     */
    ResultHandle<SupplierModel> saveSupplier(SupplierModel supplierModel);

    /**
     * 新增供应商联系人
     * @param supplierContactModel 供应商联系人
     */
    ResultHandle<SupplierContactModel> saveSupplierContact(SupplierContactModel supplierContactModel);

    /**
     * 查询供应商详情
     * @param supplierId 供应商id
     */
    SupplierModel getSupplierById(int supplierId);

    /**
     * 查询供应商所有联系人
     * @param supplierId 供应商id
     */
    List<SupplierContactModel> getSupplierContactBySupplierId(int supplierId);

    /**
     * 查询供应商联系人详情
     * @param contactId 联系人id
     */
    SupplierContactModel getSupplierContactById(int contactId);

    /**
     * 修改供应商
     * @param supplierModel 供应商
     */
    ResultHandle<SupplierModel> updateSupplier(SupplierModel supplierModel);

    /**
     * 修改供应商联系人
     * @param supplierContactModel
     */
    ResultHandle<SupplierContactModel> updateSupplierContact(SupplierContactModel supplierContactModel);

    /**
     * 启用供应商
     * @param supplierId 供应商id
     * @return
     */
    ResultHandle<SupplierModel> enableSupplier(int supplierId);

    /**
     * 停用供应商
     * @param supplierId 供应商id
     * @return
     */
    ResultHandle<SupplierModel> disableSupplier(int supplierId);

    /**
     * 删除供应商
     * @param supplierId 供应商id
     * @return
     */
    ResultHandle<SupplierModel> removeSupplier(int supplierId);

    /**
     * 删除联系人
     * @param contactId
     * @return
     */
    ResultHandle<SupplierModel> removeContact(int contactId);
}
