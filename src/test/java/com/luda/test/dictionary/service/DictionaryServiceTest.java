package com.luda.test.dictionary.service;

import com.luda.comm.po.DictionaryType;
import com.luda.comm.po.ResultHandle;
import com.luda.dictionary.DictFactory;
import com.luda.dictionary.model.DictionaryModel;
import com.luda.dictionary.model.GoodsColor;
import com.luda.dictionary.model.GoodsKind;
import com.luda.dictionary.model.GoodsType;
import com.luda.dictionary.service.DictionaryService;
import com.luda.test.SpringSimpleJunit;
import com.luda.util.CommonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */
public class DictionaryServiceTest extends SpringSimpleJunit{
    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void testFetchGoodsTypeList(){
        List<GoodsType> goodsTypeList = dictionaryService.fetchGoodsTypeList();
        print(CommonUtils.convertBeanCollectionToJsonArray(goodsTypeList, null).toString());
    }

    @Test
    public void testSaveGoodsType(){
        GoodsType goodsType = new GoodsType();
        goodsType.setTypeName("隐形眼镜");
        goodsType.setKindId(2);
        ResultHandle<GoodsType> resultHandle = dictionaryService.saveGoodsType(goodsType);
        print("success:" + resultHandle.isSuccess() + " typeId:" + resultHandle.getReturnContent().getTypeId());
    }

    @Test
    public void testFetchGoodsKindList(){
        List<GoodsKind> goodsKindList = dictionaryService.fetchGoodsKindList();
        print(CommonUtils.convertBeanCollectionToJsonArray(goodsKindList, null).toString());
    }

    @Test
    public void testGetGoodsTypeById(){
        GoodsType goodsType = dictionaryService.getGoodsTypeById(2);
        print(CommonUtils.convertBeanToJson(goodsType, null).toString());
    }

    @Test
    public void testUpdateGoodsType(){
        GoodsType goodsType = dictionaryService.getGoodsTypeById(2);
        goodsType.setTypeName(goodsType.getTypeName()+"_修改");
        ResultHandle<GoodsType> resultHandle = dictionaryService.updateGoodsType(goodsType);
        print(resultHandle.isSuccess() + " " + resultHandle.getMsg());
    }

    @Test
    public void testRemoveGoodsType(){
        ResultHandle<GoodsType> resultHandle = dictionaryService.removeGoodsType(2);
        print(resultHandle.toString());
    }

    @Test
    public void testSaveGoodsColor(){
        GoodsColor goodsColor = new GoodsColor();
        goodsColor.setColorName("蓝");
        ResultHandle resultHandle = dictionaryService.saveGoodsColor(goodsColor);
        print(resultHandle.toString());
    }

    @Test
    public void testFetchGoodsColorList(){
        List<GoodsColor> list = dictionaryService.fetchGoodsColorList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
    }

    @Test
    public void testGetGoodsColorById(){
        GoodsColor goodsColor = dictionaryService.getGoodsColorById(1);
        print(CommonUtils.convertBeanToJson(goodsColor, null).toString());
    }

    @Test
    public void testUpdateGoodsColor(){
        GoodsColor goodsColor = dictionaryService.getGoodsColorById(1);
        goodsColor.setColorName("粉红");
        ResultHandle resultHandle = dictionaryService.updateGoodsColor(goodsColor);
        print(resultHandle.toString());
    }

    @Test
    public void testRemoveGoodsColor(){
        ResultHandle resultHandle = dictionaryService.removeGoodsColor(1);
        print(resultHandle.toString());
    }


    @Test
    public void testSaveDictionary(){
        DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setDictType(DictionaryType.GOODS_BRAND.name());
        dictionaryModel.setDictName("暴龙");
        ResultHandle resultHandle = dictionaryService.saveDictionary(dictionaryModel);
        print(resultHandle.toString());
    }

    @Test
    public void testFetchDictList(){
        List<DictionaryModel> list = dictionaryService.fetchDictionaryList();
        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
    }

    @Test
    public void testGetDictionaryById(){
        DictionaryModel dictionaryModel = dictionaryService.getDictionaryById(1);
        print(CommonUtils.convertBeanToJson(dictionaryModel, null).toString());
    }

    @Test
    public void testUpdateDictionary(){
        DictionaryModel dictionaryModel = dictionaryService.getDictionaryById(1);
        dictionaryModel.setDictName(dictionaryModel.getDictName()+"_修改");
        dictionaryService.updateDictionary(dictionaryModel);

    }

    @Test
    public void testReomveDictionary(){
        dictionaryService.removeDictionary(1);
    }

    @Test
    public void testFetchByType(){
        List<DictionaryModel> list = dictionaryService.fetchDictionaryByType(DictionaryType.GOODS_BRAND.name());
        print(CommonUtils.convertBeanCollectionToJsonArray(list,null).toString());
    }

    @Test
    public void testDictFactory(){
        DictFactory dictFactory = DictFactory.getInstance();
        Map<String, String> colors = dictFactory.getColorsMap();
        Map<String, String> brands = dictFactory.getBrandMap();
        Map<String, String> units = dictFactory.getUnitMap();
        System.out.println(colors);
        System.out.println(brands);
        System.out.println(units);
        System.out.println(dictFactory.getColorsList());
    }
}
