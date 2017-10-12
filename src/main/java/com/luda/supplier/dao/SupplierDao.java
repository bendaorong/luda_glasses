package com.luda.supplier.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface SupplierDao {
    List<SupplierModel> fetchSupplierList();

    int saveSupplier(SupplierModel supplierModel);

    int saveSupplierContact(SupplierContactModel supplierContactModel);
}
