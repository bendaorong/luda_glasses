package com.luda.supplier.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface SupplierDao {
    List<SupplierModel> fetchSupplierList();

    int saveSupplier(SupplierModel supplierModel);

    int saveSupplierContact(SupplierContactModel supplierContactModel);

    String getMaxSupplierCode();

    int updateSupplier(SupplierModel supplierModel);

    SupplierModel getSupplierById(int supplierId);

    int updateSupplierUseFlag(@Param("supplierId") int supplierId, @Param("useFlag") int useFlag);

    int removeSupplier(int supplierId);

    List<SupplierContactModel> getSupplierContactBySupplierId(int supplierId);

    SupplierContactModel getSupplierContactById(int contactId);

    SupplierContactModel getHeadContact(int supplierId);

    int updateSupplierContact(SupplierContactModel supplierContactModel);

    int removeContact(int contactId);

    List<SupplierModel> fetchUseableSupplierList();
}
