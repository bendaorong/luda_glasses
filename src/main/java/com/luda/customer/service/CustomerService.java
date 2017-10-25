package com.luda.customer.service;

import com.luda.comm.po.ResultHandle;
import com.luda.customer.model.CustomerModel;
import com.luda.inventory.model.PurchaseOrderItem;

import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
public interface CustomerService {
    /**
     * 查询门店信息
     * @return
     */
    List<CustomerModel> fetchCustomerList();

    /**
     * 保存门店信息
     * @param CustomerModel
     * @return
     */
    ResultHandle<CustomerModel> saveCustomer(CustomerModel CustomerModel);

    /**
     *
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
}
