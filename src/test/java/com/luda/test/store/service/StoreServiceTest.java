package com.luda.test.store.service;

import com.luda.comm.po.ResultHandle;
import com.luda.store.model.StoreModel;
import com.luda.store.service.StoreService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */
public class StoreServiceTest extends SpringSimpleJunit{
    @Autowired
    private StoreService storeService;

    @Test
    public void testFetchStoreList(){
        List<StoreModel> storeList = storeService.fetchStoreList();
        print(CommonUtils.convertBeanCollectionToJsonArray(storeList, "yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    public void testSaveStore(){
        StoreModel storeModel = new StoreModel();
        storeModel.setStoreName("欧尚昆山店2");
        storeModel.setContactPerson("韩梅梅");
        storeModel.setContactPhone("13402176164");
        storeModel.setFaxPhone("0512-12345678");
        storeModel.setStoreAddress("昆山市经济开发区白马泾路105号");
        storeModel.setQqNumber("271482713");
        storeModel.setRemark("这是昆山第一家欧尚超市");
        ResultHandle<StoreModel> resultHandle = storeService.saveStore(storeModel);
    }

    @Test
    public void testGetById(){
        int storeId = 1;
        StoreModel storeModel = storeService.getById(storeId);
        print(storeModel.toString());
    }

    @Test
    public void testUpdateStore(){
        int storeId = 1;
        StoreModel storeModel = storeService.getById(storeId);
        storeModel.setStoreAddress(storeModel.getStoreAddress() + "_修改");
        ResultHandle<StoreModel> resultHandle = storeService.updateStore(storeModel);
        print("result:" + resultHandle.isSuccess());
    }

    @Test
    public void testRemoveStore(){
        int storeId = 1;
        ResultHandle<StoreModel> resultHandle = storeService.removeStore(storeId);
        print("result:" + resultHandle.isSuccess());
    }
}
