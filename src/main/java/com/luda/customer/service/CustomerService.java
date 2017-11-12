package com.luda.customer.service;

import com.luda.comm.po.ResultHandle;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.model.OptometryRecord;
import com.luda.inventory.model.PurchaseOrderItem;

import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
public interface CustomerService {
    /**
     * 查询客户信息
     * @return
     */
    List<CustomerModel> fetchCustomerList();

    /**
     * 保存客户信息
     * @param CustomerModel
     * @return
     */
    ResultHandle<CustomerModel> saveCustomer(CustomerModel CustomerModel);

    /**
     * 查询客户
     * @param customerId
     */
    CustomerModel getById(int customerId);

    /**
     * 修改customer
     * @param CustomerModel
     * @return
     */
    ResultHandle<CustomerModel> updateCustomer(CustomerModel CustomerModel);

    /**
     * 删除customer
     * @param customerId
     */
    ResultHandle<CustomerModel> removeCustomer(int customerId);

    /**
     * 保存验光单
     */
    ResultHandle<OptometryRecord> saveOptometryRecord(OptometryRecord optometryRecord);

    /**
     * 查询验光单
     * @param customerId 客户id
     */
    List<OptometryRecord> fetchOptometryRecordsByCustomerId(int customerId);
}
