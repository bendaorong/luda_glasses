package com.luda.dictionary.service.impl;

import com.luda.comm.po.DictionaryType;
import com.luda.comm.po.ResultHandle;
import com.luda.dictionary.DictFactory;
import com.luda.dictionary.dao.DictionaryDao;
import com.luda.dictionary.model.DictionaryModel;
import com.luda.dictionary.model.GoodsColor;
import com.luda.dictionary.model.GoodsKind;
import com.luda.dictionary.model.GoodsType;
import com.luda.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService{
    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public List<GoodsType> fetchGoodsTypeList() {
        return dictionaryDao.fetchGoodsTypeList();
    }

    @Override
    public ResultHandle<GoodsType> saveGoodsType(GoodsType goodsType) {
        ResultHandle<GoodsType> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.saveGoodsType(goodsType);
        if(result > 0){
            resultHandle.setReturnContent(goodsType);
        }else {
            resultHandle.setMsg("商品类型添加失败");
        }
        return resultHandle;
    }

    @Override
    public List<GoodsKind> fetchGoodsKindList() {
        return dictionaryDao.fetchGoodsKindList();
    }

    @Override
    public GoodsType getGoodsTypeById(int typeId) {
        return dictionaryDao.getGoodsTypeById(typeId);
    }

    @Override
    public ResultHandle<GoodsType> updateGoodsType(GoodsType goodsType) {
        ResultHandle<GoodsType> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.updateGoodsType(goodsType);
        if(result > 0){
            resultHandle.setReturnContent(goodsType);
        }else {
            resultHandle.setMsg("商品类型添加失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<GoodsType> removeGoodsType(int typeId) {
        ResultHandle<GoodsType> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.removeGoodsType(typeId);
        if(result < 0){
            resultHandle.setMsg("商品类型删除失败");
        }
        return resultHandle;
    }

    @Override
    public List<GoodsColor> fetchGoodsColorList() {
        return dictionaryDao.fetchGoodsColorList();
    }

    @Override
    public ResultHandle<GoodsColor> saveGoodsColor(GoodsColor goodsColor) {
        ResultHandle<GoodsColor> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.saveGoodsColor(goodsColor);
        if(result < 0){
            resultHandle.setMsg("商品颜色保存失败");
        }
        return resultHandle;
    }

    @Override
    public GoodsColor getGoodsColorById(int colorId) {
        return dictionaryDao.getGoodsColorById(colorId);
    }

    @Override
    public ResultHandle<GoodsColor> updateGoodsColor(GoodsColor goodsColor) {
        ResultHandle<GoodsColor> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.updateGoodsColor(goodsColor);
        if(result < 0){
            resultHandle.setMsg("商品颜色更新失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<GoodsColor> removeGoodsColor(int colorId) {
        ResultHandle<GoodsColor> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.removeGoodsColor(colorId);
        if(result < 0){
            resultHandle.setMsg("商品颜色删除失败");
        }
        return resultHandle;
    }



    @Override
    public List<DictionaryModel> fetchDictionaryList() {
        return dictionaryDao.fetchDictionaryList();
    }

    @Override
    public ResultHandle<DictionaryModel> saveDictionary(DictionaryModel dictionaryModel) {
        ResultHandle<DictionaryModel> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.saveDictionary(dictionaryModel);
        if(result < 0){
            resultHandle.setMsg("字典保存失败");
        }
        return resultHandle;
    }

    @Override
    public DictionaryModel getDictionaryById(int dictId) {
        return dictionaryDao.getDictionaryById(dictId);
    }

    @Override
    public ResultHandle<DictionaryModel> updateDictionary(DictionaryModel dictionaryModel) {
        ResultHandle<DictionaryModel> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.updateDictionary(dictionaryModel);
        if(result < 0){
            resultHandle.setMsg("字典更新失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<DictionaryModel> removeDictionary(int dictId) {
        ResultHandle<DictionaryModel> resultHandle = new ResultHandle<>();
        int result = dictionaryDao.removeDictionary(dictId);
        if(result < 0){
            resultHandle.setMsg("字典删除失败");
        }
        return resultHandle;
    }

    @Override
    public List<DictionaryModel> fetchDictionaryByType(String dictType) {
        if(DictionaryType.GOODS_COLOR.name().equals(dictType)){
            return DictFactory.getInstance().getColorsList();
        }
        if(DictionaryType.GOODS_BRAND.name().equals(dictType)){
            return DictFactory.getInstance().getBrandList();
        }
        if(DictionaryType.GOODS_UNIT.name().equals(dictType)){
            return DictFactory.getInstance().getUnitList();
        }
        if(DictionaryType.REGION.name().equals(dictType)){
            return DictFactory.getInstance().getRegionList();
        }
        return Collections.emptyList();
    }
}
