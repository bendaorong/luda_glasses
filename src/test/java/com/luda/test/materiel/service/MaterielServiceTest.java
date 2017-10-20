package com.luda.test.materiel.service;

import com.luda.comm.po.ResultHandle;
import com.luda.materiel.model.MaterielModel;
import com.luda.materiel.service.MaterielService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */
public class MaterielServiceTest extends SpringSimpleJunit{
    @Autowired
    private MaterielService materielService;

    @Test
    public void testSaveMateriel(){
        MaterielModel materielModel = new MaterielModel();
        materielModel.setCode("1710190001");
        materielModel.setName("暴龙镜架");
        materielModel.setBarcode("152521519");
        materielModel.setTypeId(5);
        materielModel.setSellPrice(786);
        materielModel.setTradePrice(0);
        materielModel.setCostPrice(0);
        materielModel.setUnit("副");
        materielModel.setColor("M01");
        materielModel.setMinInventory(10);
        materielModel.setMaxInventory(50);
        materielModel.setBrand("暴龙");
        materielModel.setSpecification("BJ1208");
        materielModel.setTexture("板材");
        materielModel.setManufacturer("厦门雅瑞光学有限公司海仓分公司");
        materielModel.setUseFlag(1);
        materielModel.setRemark("好东西");
        materielModel.setCreatorUserId(1);
        materielModel.setUpdateUserId(1);

        ResultHandle<MaterielModel> resultHandle = materielService.saveMateriel(materielModel);
        print(resultHandle.toString());
    }

    @Test
    public void testFetchMaterielList(){
        List<MaterielModel> list = materielService.fetchMaterielList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list,"yyyy-MM-dd HH:mm:ss").toString());
    }

    @Test
    public void testGetById(){
        MaterielModel materielModel = materielService.getById(1);
        print(CommonUtils.convertBeanToJson(materielModel,null).toString());
    }

    @Test
    public void testUpdateMateriel(){
        MaterielModel materielModel = materielService.getById(1);
        materielModel.setCode("1710190001_");
        materielModel.setName("暴龙镜架_");
        materielModel.setBarcode("152521519_");
        materielModel.setTypeId(6);
        materielModel.setSellPrice(787);
        materielModel.setTradePrice(1);
        materielModel.setCostPrice(1);
        materielModel.setUnit("副_");
        materielModel.setColor("M01_");
        materielModel.setMinInventory(11);
        materielModel.setMaxInventory(51);
        materielModel.setBrand("暴龙_");
        materielModel.setSpecification("BJ12081");
        materielModel.setTexture("板材1");
        materielModel.setManufacturer("厦门雅瑞光学有限公司海仓分公司1");
        materielModel.setUseFlag(1);
        materielModel.setRemark("好东西1");
        materielModel.setCreatorUserId(2);
        materielModel.setUpdateUserId(2);
        ResultHandle<MaterielModel> resultHandle = materielService.updateMateriel(materielModel);
        print(resultHandle.toString());
    }

    @Test
    public void removeMateriel(){
        ResultHandle<MaterielModel> resultHandle = materielService.removeMateriel(1);
        print(resultHandle.toString());
    }
}
