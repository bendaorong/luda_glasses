package com.luda.supplier.service.impl;

import com.luda.comm.po.ResultHandle;
import com.luda.supplier.dao.SupplierDao;
import com.luda.supplier.model.SupplierContactModel;
import com.luda.supplier.model.SupplierModel;
import com.luda.supplier.service.SupplierService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao supplierDao;


    @Override
    public List<SupplierModel> fetchSupplierList() {
        return supplierDao.fetchSupplierList();
    }

    @Override
    public ResultHandle<SupplierModel> saveSupplier(SupplierModel supplierModel) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();

        // 验证供应商信息
        String errorMsg = checkSupplier(supplierModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化供应商
        initForAddSupplier(supplierModel);

        // 保存供应商
        int result = supplierDao.saveSupplier(supplierModel);

        if(result >= 1){
            resultHandle.setReturnContent(supplierModel);
        }else {
            resultHandle.setMsg("供应商创建失败");
        }
        return resultHandle;
    }

    /**
     * 创建供应商时初始化一些信息
     * @param supplierModel
     */
    private void initForAddSupplier(SupplierModel supplierModel) {
        // 编号
        supplierModel.setSupplierCode(getNextSupplierCode());
        // 停用(默认为1=>使用)
        supplierModel.setUseFlag(1);
    }

    /**
     * 获取门店编号
     * @return
     */
    private synchronized String getNextSupplierCode() {
        String currentSupplierCode = supplierDao.getMaxSupplierCode();
        // 默认从0001开始计数
        if(StringUtils.isEmpty(currentSupplierCode)){
            return "0001";
        }

        // 新编号=当前编号+1
        int supplierCode = Integer.valueOf(currentSupplierCode);
        return String.format("%04d", supplierCode + 1);
    }

    /**
     * 创建供应商验证
     * @param supplierModel
     * @return
     */
    private String checkSupplier(SupplierModel supplierModel) {
        if(StringUtils.isEmpty(supplierModel.getSupplierName())){
            return "请填写供应商名称";
        }
        if(StringUtils.isEmpty(supplierModel.getContactPerson())){
            return "请填写负责人";
        }
        if(StringUtils.isEmpty(supplierModel.getContactPhone())){
            return "请填写电话号码";
        }
        if(StringUtils.isEmpty(supplierModel.getRegTaxNo())){
            return "请填写工商税务登记证号";
        }
        return null;
    }

    @Override
    public ResultHandle<SupplierContactModel> saveSupplierContact(SupplierContactModel supplierContactModel) {
        ResultHandle<SupplierContactModel> resultHandle = new ResultHandle<>();
        int result = supplierDao.saveSupplierContact(supplierContactModel);
        if(result >= 1){
            resultHandle.setReturnContent(supplierContactModel);
        }else {
            resultHandle.setMsg("供应商联系人创建失败");
        }
        return resultHandle;
    }

    @Override
    public SupplierModel getSupplierById(int supplierId) {
        return supplierDao.getSupplierById(supplierId);
    }

    @Override
    public List<SupplierContactModel> getSupplierContactBySupplierId(int supplierId) {
        return null;
    }

    @Override
    public SupplierContactModel getSupplierContactById(int contactId) {
        return null;
    }

    @Override
    public ResultHandle<SupplierModel> updateSupplier(SupplierModel supplierModel) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();

        // 验证供应商信息
        String errorMsg = checkSupplier(supplierModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 保存供应商
        int result = supplierDao.updateSupplier(supplierModel);

        if(result >= 1){
            resultHandle.setReturnContent(supplierModel);
        }else {
            resultHandle.setMsg("供应商创建失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SupplierContactModel> updateSupplierContact(SupplierContactModel supplierContactModel) {
        return null;
    }
}
