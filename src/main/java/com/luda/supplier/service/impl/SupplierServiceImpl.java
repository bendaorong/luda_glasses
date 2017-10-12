package com.luda.supplier.service.impl;

import com.luda.comm.po.ResultHandle;
import com.luda.supplier.dao.SupplierDao;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao supplierDao;


    @Override
    public List<SupplierModel> fetchSupplierList() {
        return supplierDao.fetchSupplierList();
    }

    @Override
    public ResultHandle<SupplierModel> saveSupplier(SupplierModel supplierModel) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();
        int result = supplierDao.saveSupplier(supplierModel);
        if(result >= 1){
            resultHandle.setReturnContent(supplierModel);
        }else {
            resultHandle.setMsg("供应商创建失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SupplierContactModel> saveSupplierContact(SupplierContactModel supplierContactModel) {
        ResultHandle<SupplierContactModel> resultHandle = new ResultHandle<>();
        int result = supplierDao.saveSupplierContact(supplierContactModel);
        if(result >= 1){
            resultHandle.setReturnContent(supplierContactModel);
        }else {
            resultHandle.setMsg("供应商联系人创建失败");
        }
        return resultHandle;
    }

    @Override
    public SupplierModel getSupplierById(int supplierId) {
        return null;
    }

    @Override
    public List<SupplierContactModel> getSupplierContactBySupplierId(int supplierId) {
        return null;
    }

    @Override
    public SupplierContactModel getSupplierContactById(int contactId) {
        return null;
    }

    @Override
    public ResultHandle<SupplierModel> updateSupplier(SupplierModel supplierModel) {
        return null;
    }

    @Override
    public ResultHandle<SupplierContactModel> updateSupplierContact(SupplierContactModel supplierContactModel) {
        return null;
    }
}
