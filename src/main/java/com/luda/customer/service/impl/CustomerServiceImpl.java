package com.luda.customer.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.customer.dao.CustomerDao;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.service.CustomerService;
import com.luda.store.dao.StoreDao;
import com.luda.store.model.StoreModel;
import com.luda.store.service.StoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDao customerDao;
    private Logger log = Logger.getLogger(CustomerServiceImpl.class);


    @Override
    public List<CustomerModel> fetchCustomerList() {




        return customerDao.fetchCustomerList();
    }

    @Override
    public ResultHandle<CustomerModel> saveCustomer(CustomerModel customerModel) {

        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();

        try {
            this.customerDao.saveCustomer(customerModel);
            resultHandle.setMsg("创建客户成功");

        }catch (Exception e){
            resultHandle.setMsg("创建客户失败");
            log.error("save customer error", e);
        }



        return resultHandle;
    }

    @Override
    public CustomerModel getById(int customerId) {
        return this.customerDao.getById(customerId);
    }

    @Override
    public ResultHandle<CustomerModel> updateCustomer(CustomerModel customerModel) {


        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();

        try {
            this.customerDao.updateCustomer(customerModel);

            resultHandle.setMsg("更新客户成功");

        }catch (Exception e){
            resultHandle.setMsg("更新客户失败");
            log.error("update customer error", e);
        }


        return resultHandle;
    }

    @Override
    public ResultHandle<CustomerModel> removeCustomer(int customerId) {
        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();

        try {
            this.customerDao.removeCustomer(customerId);

            resultHandle.setMsg("更新客户成功");

        }catch (Exception e){
            resultHandle.setMsg("更新客户失败");
            log.error("update customer error", e);
        }



        return resultHandle;
    }
}
