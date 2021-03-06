package com.luda.test.supplier.service;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public class SupplierServiceTest extends SpringSimpleJunit{
    @Autowired
    private SupplierService supplierService;

    @Test
    public void testFetchSupplierList(){
        List<SupplierModel> supplierList = supplierService.fetchSupplierList();
        print(CommonUtils.convertBeanCollectionToJsonArray(supplierList, "yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    public void testSaveSupplier(){
        SupplierModel supplierModel = new SupplierModel();
        supplierModel.setSupplierCode("0001");
        supplierModel.setSupplierName("供应商A");
        supplierModel.setBank("中国建设银行");
        supplierModel.setBankAccount("6222021001022943275");
        supplierModel.setContactPerson("张鑫");
        supplierModel.setContactPhone("13402176554");
        supplierModel.setRegTaxNo("000111");
        supplierModel.setUseFlag(1);
        supplierModel.setRemark("我供应方便面");

        ResultHandle<SupplierModel> handle = supplierService.saveSupplier(supplierModel);
        print(handle.getMsg());
    }

    @Test
    public void testSaveSupplierContact(){
        SupplierContactModel supplierContactModel = new SupplierContactModel();
        supplierContactModel.setSupplierId(1);
        supplierContactModel.setContactName("李白");
        supplierContactModel.setGender(Constants.GENDER_MALE);
        supplierContactModel.setEmail("libai@126.com");
        supplierContactModel.setAddress("中山西路27号");
        supplierContactModel.setMobileNumber("13420987637");
        supplierContactModel.setTelNumber("021-02922824");
        supplierContactModel.setPostcode("221009");
        supplierContactModel.setHeadFlag("N");

        ResultHandle<SupplierContactModel> handle = supplierService.saveSupplierContact(supplierContactModel);
        print(handle.getMsg());
    }

    @Test
    public void testUpdateSupplier(){
        SupplierModel supplierModel = supplierService.getSupplierById(1);
        supplierModel.setSupplierName("XXX公司");
        ResultHandle<SupplierModel> resultHandle = supplierService.updateSupplier(supplierModel);
        print(resultHandle.getMsg());
    }

    @Test
    public void testGetSupplierContactBySupplierId(){
        List<SupplierContactModel> list = supplierService.getSupplierContactBySupplierId(1);
        JSONArray arr = CommonUtils.convertBeanCollectionToJsonArray(list, "yyyy-MM-dd");
        print(arr.toString());
    }

    @Test
    public void testGetSupplierContactById(){
        SupplierContactModel supplierContactModel = supplierService.getSupplierContactById(2);
        print(CommonUtils.convertBeanToJson(supplierContactModel,null).toString());
    }

    @Test
    public void testUpdateContact(){
        SupplierContactModel supplierContactModel = supplierService.getSupplierContactById(2);
        supplierContactModel.setContactName("王化_");
        supplierContactModel.setHeadFlag("Y");
        ResultHandle resultHandle = supplierService.updateSupplierContact(supplierContactModel);
        print(resultHandle.getMsg());
    }

    @Test
    public void testRemoveContact(){
       ResultHandle<SupplierModel> resultHandle = supplierService.removeContact(2);
       print(""+resultHandle.isSuccess());
    }
}
