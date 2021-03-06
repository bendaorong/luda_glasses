package com.luda.customer.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
import com.luda.customer.dao.CustomerDao;
import com.luda.customer.model.CustomerModel;
import com.luda.customer.model.OptometryRecord;
import com.luda.customer.service.CustomerService;
import com.luda.inventory.model.CommonQueryBean;
import com.luda.store.dao.StoreDao;
import com.luda.store.model.StoreModel;
import com.luda.store.service.StoreService;
import com.luda.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.xml.transform.Result;
import java.util.Date;
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
    public int getCustomerTotalCount(CommonQueryBean queryBean) {
        return customerDao.getCustomerTotalCount(queryBean);
    }

    @Override
    public List<CustomerModel> fetchCustomerListPage(CommonQueryBean queryBean) {
        return customerDao.fetchCustomerListPage(queryBean);
    }

    @Override
    public List<CustomerModel> fetchCustomerList() {
        return customerDao.fetchCustomerList();
    }

    @Override
    public ResultHandle<CustomerModel> saveCustomer(CustomerModel customerModel) {
        ResultHandle<CustomerModel> resultHandle = new ResultHandle<CustomerModel>();
        try {
            String errorMsg = checkCustomer(customerModel);
            if(StringUtils.isNotEmpty(errorMsg)){
                resultHandle.setMsg(errorMsg);
                return resultHandle;
            }

            initAddCustomer(customerModel);
            this.customerDao.saveCustomer(customerModel);

            resultHandle.setReturnContent(customerModel);
        }catch (Exception e){
            resultHandle.setMsg("创建客户失败");
            log.error("save customer error", e);
        }
        return resultHandle;
    }

    // 验证客户信息
    private String checkCustomer(CustomerModel customerModel) {
        //验证客户名称
        if(StringUtils.isEmpty(customerModel.getName())){
            return "请填写客户姓名";
        }
        //验证手机号码
        if(StringUtils.isNotBlank(customerModel.getMobileNumber())){
            if(!CommonUtils.isMobileNumber(customerModel.getMobileNumber())){
                return "手机号码不合法";
            }
        }
        return null;
    }

    /**
     * 初始化客户资料
     * @param customerModel
     */
    private void initAddCustomer(CustomerModel customerModel) {
        customerModel.setCode(getNextCustomerCode());
        if(StringUtils.isEmpty(customerModel.getGender())){
            customerModel.setGender(Constants.GENDER_MALE);
        }
        if(StringUtils.isEmpty(customerModel.getRegion())){
            customerModel.setRegion("瑞昌市");
        }
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
        String errorMsg = checkCustomer(customerModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }
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
        optometryRecord.setOptometryDate(new Date());
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
