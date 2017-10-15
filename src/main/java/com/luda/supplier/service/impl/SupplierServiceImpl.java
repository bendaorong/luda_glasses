package com.luda.supplier.service.impl;

import com.luda.comm.po.Constants;
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

        String errorMsg = checkContact(supplierContactModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化数据
        initForAddContact(supplierContactModel);

        int result = supplierDao.saveSupplierContact(supplierContactModel);
        if(result >= 1){
            resultHandle.setReturnContent(supplierContactModel);
        }else {
            resultHandle.setMsg("供应商联系人创建失败");
        }
        return resultHandle;
    }

    /**
     * 初始化联系人
     * @param supplierContactModel
     */
    private void initForAddContact(SupplierContactModel supplierContactModel) {
        // 初始化主要联系人(默认不是主要联系人)
        if(StringUtils.isEmpty(supplierContactModel.getHeadFlag())){
            supplierContactModel.setHeadFlag("N");
        }
        // 初始化性别
        if(StringUtils.isEmpty(supplierContactModel.getGender())){
            supplierContactModel.setGender(Constants.GENDER_MALE);
        }
    }

    @Override
    public SupplierModel getSupplierById(int supplierId) {
        return supplierDao.getSupplierById(supplierId);
    }

    @Override
    public List<SupplierContactModel> getSupplierContactBySupplierId(int supplierId) {
        return supplierDao.getSupplierContactBySupplierId(supplierId);
    }

    @Override
    public SupplierContactModel getSupplierContactById(int contactId) {
        return supplierDao.getSupplierContactById(contactId);
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
        ResultHandle<SupplierContactModel> resultHandle = new ResultHandle<>();

        String errorMsg = checkContact(supplierContactModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        int result = supplierDao.updateSupplierContact(supplierContactModel);
        return resultHandle;
    }

    /**
     * 验证供应商联系人
     * @param supplierContactModel
     * @return
     */
    private String checkContact(SupplierContactModel supplierContactModel) {
        // 名称
        if(StringUtils.isEmpty(supplierContactModel.getContactName())){
            return "请填写联系人名称";
        }
        //手机号码
        if(StringUtils.isEmpty(supplierContactModel.getMobileNumber())
                && StringUtils.isEmpty(supplierContactModel.getTelNumber())){
            return "请填写联系人手机号码或电话号码";
        }
        // 是否主要联系人,一个供应商只能有一个主要联系人
        if("Y".equals(supplierContactModel.getHeadFlag())){
            SupplierContactModel headContact = getHeadContact(supplierContactModel.getSupplierId());
            if(headContact != null){
                return "每个供应商只能设置一个主要联系人";
            }
        }

        return null;
    }

    /**
     * 查询供应商主要联系人
     * @param supplierId 供应商id
     * @return
     */
    private SupplierContactModel getHeadContact(int supplierId) {
        return supplierDao.getHeadContact(supplierId);
    }

    @Override
    public ResultHandle<SupplierModel> enableSupplier(int supplierId) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();

        SupplierModel supplierModel = supplierDao.getSupplierById(supplierId);
        if(supplierModel == null){
            resultHandle.setMsg("供应商不存在");
            return resultHandle;
        }
        if(supplierModel.getUseFlag() == 1){
            resultHandle.setMsg("供应商不可启用");
        }

        int result = supplierDao.updateSupplierUseFlag(supplierId, 1);
        if(result > 0){
            supplierModel.setUseFlag(1);
            resultHandle.setReturnContent(supplierModel);
        }else {
            resultHandle.setMsg("供应商启用失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SupplierModel> disableSupplier(int supplierId) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();

        SupplierModel supplierModel = supplierDao.getSupplierById(supplierId);
        if(supplierModel == null){
            resultHandle.setMsg("供应商不存在");
            return resultHandle;
        }
        if(supplierModel.getUseFlag() == 0){
            resultHandle.setMsg("供应商不可停用");
        }

        int result = supplierDao.updateSupplierUseFlag(supplierId, 0);
        if(result > 0){
            supplierModel.setUseFlag(0);
            resultHandle.setReturnContent(supplierModel);
        }else {
            resultHandle.setMsg("供应商停用失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SupplierModel> removeSupplier(int supplierId) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();
        int result = supplierDao.removeSupplier(supplierId);
        if(result < 0){
            resultHandle.setMsg("供应商删除失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<SupplierModel> removeContact(int contactId) {
        ResultHandle<SupplierModel> resultHandle = new ResultHandle<>();
        int result = supplierDao.removeContact(contactId);
        if(result < 0){
            resultHandle.setMsg("供应商联系人删除失败");
        }
        return resultHandle;
    }
}
