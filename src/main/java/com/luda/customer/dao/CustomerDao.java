package com.luda.customer.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.model.OptometryRecord;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.store.model.StoreModel;

import java.util.List;

/**
 * Created by I075723 on 10/25/2017.
 */
public interface CustomerDao {


    List<CustomerModel> fetchCustomerList();

    int saveCustomer(CustomerModel customerModel);

    CustomerModel getById(int customerId);

    int updateCustomer(CustomerModel customerModel);

    int removeCustomer(int customerId);

    String getMaxCode();

    int saveOptometryRecord(OptometryRecord optometryRecord);

    List<OptometryRecord> fetchOptometryRecordsByCustomerId(int customerId);

    int getCustomerTotalCount(CommonQueryBean queryBean);

    List<CustomerModel> fetchCustomerListPage(CommonQueryBean queryBean);
}
