package com.luda.store.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.store.model.StoreModel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/5.
 */
public interface StoreDao {
    List<StoreModel> fetchStoreList();

    void saveStore(StoreModel storeModel);

    String getMaxStoreCode();

    StoreModel getHeadStore();

    StoreModel getById(int storeId);

    int updateStore(StoreModel storeModel);

    int removeStore(int storeId);
}
