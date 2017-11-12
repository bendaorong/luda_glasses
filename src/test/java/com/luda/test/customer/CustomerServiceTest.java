package com.luda.test.customer;

import com.luda.comm.po.ResultHandle;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.model.OptometryRecord;
import com.luda.customer.service.CustomerService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.geom.RectangularShape;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/29.
 */
public class CustomerServiceTest extends SpringSimpleJunit{
    @Autowired
    private CustomerService customerService;

    @Test
    public void testSaveCustomer(){
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCode("111");
        customerModel.setName("李四");
        customerModel.setGender("F");
        customerModel.setBirthday("1984-02-02");
        customerModel.setMobileNumber("13402928292");
        customerModel.setWechatno("133222");
        customerModel.setRegion("瑞昌市");
        customerModel.setAddress("fwwefwfe");
        ResultHandle<CustomerModel> resultHandle = customerService.saveCustomer(customerModel);
        print(resultHandle.toString());
    }

    @Test
    public void testUpdateCustomer(){
        CustomerModel customerModel = customerService.getById(1);
        customerModel.setName("haha");
        customerService.updateCustomer(customerModel);
    }

    @Test
    public void testRemoveCustomer(){
        customerService.removeCustomer(1);
    }

    @Test
    public void testSaveOptometryRecord(){
        OptometryRecord optometryRecord = new OptometryRecord();
        optometryRecord.setCustomerId(1);
        optometryRecord.setBusinessManId(1);
        optometryRecord.setOptometryDate(new Date());
        optometryRecord.setRightSphere(1.5);
        optometryRecord.setLeftSphere(1.5);
        optometryRecord.setRightCylinder(1.5);
        optometryRecord.setLeftCylinder(1.5);
        optometryRecord.setRightAxial(1.5);
        optometryRecord.setLeftAxial(1.5);
        optometryRecord.setRightUncorrectedVisualAcuity(0.5);
        optometryRecord.setLeftUncorrectedVisualAcuity(0.6);
        optometryRecord.setRightCorrectedVisualAcuity(1.0);
        optometryRecord.setLeftCorrectedVisualAcuity(1.0);
        customerService.saveOptometryRecord(optometryRecord);
    }

    @Test
    public void testFetchOptometryRecordByCustomerId(){
        List<OptometryRecord> list = customerService.fetchOptometryRecordsByCustomerId(1);
        print(CommonUtils.convertBeanCollectionToJsonArray(list, null).toString());
    }
}
