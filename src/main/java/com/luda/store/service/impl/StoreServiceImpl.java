package com.luda.store.service.impl;

import com.luda.comm.po.Constants;
import com.luda.comm.po.ResultHandle;
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
@Service("storeService")
public class StoreServiceImpl implements StoreService{
    private Logger log = Logger.getLogger(StoreServiceImpl.class);

    @Autowired
    private StoreDao storeDao;

    @Override
    public List<StoreModel> fetchStoreList() {
        return storeDao.fetchStoreList();
    }

    @Override
    public ResultHandle<StoreModel> saveStore(StoreModel storeModel) {
        ResultHandle<StoreModel> resultHandle = new ResultHandle<>();
        try {
            // 默认为分店
            if(StringUtils.isEmpty(storeModel.getHeadOfficeFlag())){
                storeModel.setHeadOfficeFlag(Constants.STORE_HEAD_OFFICE_FLAG_N);
            }

            // 总店只能有一个，若当前新增门店选为总店，需验证有没有其他总店已存在
            if(Constants.STORE_HEAD_OFFICE_FLAG_Y.equals(storeModel.getHeadOfficeFlag())){
                StoreModel headStore = getHeadStore();
                if(headStore != null){
                    resultHandle.setMsg("总店已存在");
                    return resultHandle;
                }
            }

            // 获取门店编号
            storeModel.setStoreCode(getNextStoreCode());

            storeDao.saveStore(storeModel);
            resultHandle.setReturnContent(storeModel);
        }catch (Exception e){
            resultHandle.setMsg("创建门店失败");
            log.error("save store error", e);
        }
        return resultHandle;
    }

    @Override
    public StoreModel getById(int storeId) {
        return storeDao.getById(storeId);
    }

    @Override
    public ResultHandle<StoreModel> updateStore(StoreModel storeModel) {
        ResultHandle<StoreModel> resultHandle = new ResultHandle<>();
        try {
            // 总店只能有一个，若当前新增门店选为总店，需验证有没有其他总店已存在
            if(Constants.STORE_HEAD_OFFICE_FLAG_Y.equals(storeModel.getHeadOfficeFlag())){
                StoreModel headStore = getHeadStore();
                if(headStore != null && headStore.getStoreId() != storeModel.getStoreId()){
                    resultHandle.setMsg("总店已存在");
                    return resultHandle;
                }
            }

            storeDao.updateStore(storeModel);
            resultHandle.setReturnContent(storeModel);
        }catch (Exception e){
            resultHandle.setMsg("创建门店失败");
            log.error("save store error", e);
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<StoreModel> removeStore(int storeId) {
        ResultHandle<StoreModel> resultHandle = new ResultHandle<>();
        int result = storeDao.removeStore(storeId);
        if(result == 0){
            resultHandle.setMsg("门店删除失败");
        }
        return resultHandle;
    }

    /**
     * 获取总店
     * @return
     */
    private StoreModel getHeadStore() {
        return storeDao.getHeadStore();
    }

    /**
     * 获取门店编号
     * @return
     */
    private synchronized String getNextStoreCode() {
        String currentStoreCode = storeDao.getMaxStoreCode();
        // 默认从0001开始计数
        if(StringUtils.isEmpty(currentStoreCode)){
            return "0001";
        }

        // 新编号=当前编号+1
        int storeCode = Integer.valueOf(currentStoreCode);
        return String.format("%04d", storeCode + 1);
    }
}
