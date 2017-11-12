package com.luda.customer.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.customer.dao.CustomerDao;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.model.OptometryRecord;
import com.luda.customer.service.CustomerService;
import com.luda.store.dao.StoreDao;
import com.luda.store.model.StoreModel;
import com.luda.store.service.StoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
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
            customerModel.setCode(getNextCustomerCode());
            this.customerDao.saveCustomer(customerModel);
            resultHandle.setReturnContent(customerModel);
        }catch (Exception e){
            resultHandle.setMsg("创建客户失败");
            log.error("save customer error", e);
        }
        return resultHandle;
    }

    /**
     * 获取下一个客户编号
     * @return
     */
    private synchronized  String getNextCustomerCode() {
        String currentCode = customerDao.getMaxCode();
        // 默认从0001开始计数
        if(StringUtils.isEmpty(currentCode)){
            return "0001";
        }
        // 新编号=当前编号+1，不足4位数则前面补0
        int newCode = Integer.valueOf(currentCode) + 1;
        return String.format("%04d", newCode);
    }

    @Override
    public CustomerModel getById(int customerId) {
        return this.customerDao.getById(customerId);
    }

    @Override
    public ResultHandle<CustomerModel> updateCustomer(CustomerModel customerModel) {
        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();
        this.customerDao.updateCustomer(customerModel);
        return resultHandle;
    }

    @Override
    public ResultHandle<CustomerModel> removeCustomer(int customerId) {
        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();
        this.customerDao.removeCustomer(customerId);
        return resultHandle;
    }

    @Override
    public ResultHandle<OptometryRecord> saveOptometryRecord(OptometryRecord optometryRecord) {
        ResultHandle<OptometryRecord> resultHandle = new ResultHandle<>();
        int result = customerDao.saveOptometryRecord(optometryRecord);
        if(result > 0){
            resultHandle.setReturnContent(optometryRecord);
        }else {
            resultHandle.setMsg("验光单保存失败");
        }
        return resultHandle;
    }

    @Override
    public List<OptometryRecord> fetchOptometryRecordsByCustomerId(int customerId) {
        return customerDao.fetchOptometryRecordsByCustomerId(customerId);
    }
}
