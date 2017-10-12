package com.luda.store.service;

import com.luda.comm.po.ResultHandle;
import com.luda.store.model.StoreModel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
public interface StoreService {
    /**
     * 查询门店信息
     * @return
     */
    List<StoreModel> fetchStoreList();

    /**
     * 保存门店信息
     * @param storeModel
     * @return
     */
    ResultHandle<StoreModel> saveStore(StoreModel storeModel);

    /**
     * 根据门店id查询门店
     * @param storeId 门店id
     */
    StoreModel getById(int storeId);

    /**
     * 修改门店信息
     * @param storeModel
     * @return
     */
    ResultHandle<StoreModel> updateStore(StoreModel storeModel);

    /**
     * 删除门店
     * @param storeId 门店id
     */
    ResultHandle<StoreModel> removeStore(int storeId);
}
