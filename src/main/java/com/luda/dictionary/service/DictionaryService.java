package com.luda.dictionary.service;

import com.luda.comm.po.ResultHandle;
import com.luda.dictionary.model.DictionaryModel;
import com.luda.dictionary.model.GoodsColor;
import com.luda.dictionary.model.GoodsKind;
import com.luda.dictionary.model.GoodsType;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
public interface DictionaryService {
    /**
     * 查询商品类型列表
     * @return
     */
    List<GoodsType> fetchGoodsTypeList();

    /**
     * 添加商品类型
     */
    ResultHandle<GoodsType> saveGoodsType(GoodsType goodsType);

    /**
     * 查询商品种类列表
     * @return
     */
    List<GoodsKind> fetchGoodsKindList();

    /**
     * 根据id查询产品类型
     * @param typeId 产品类型id
     * @return
     */
    GoodsType getGoodsTypeById(int typeId);

    /**
     * 更新产品类型
     * @param goodsType
     * @return
     */
    ResultHandle<GoodsType> updateGoodsType(GoodsType goodsType);

    /**
     * 删除商品类型
     * @param typeId 类型id
     * @return
     */
    ResultHandle<GoodsType> removeGoodsType(int typeId);


    /**
     * 查询商品颜色列表
     */
    List<GoodsColor> fetchGoodsColorList();

    /**
     * 保存商品颜色
     */
    ResultHandle<GoodsColor> saveGoodsColor(GoodsColor goodsColor);

    /**
     * 根据id查询商品颜色
     */
    GoodsColor getGoodsColorById(int colorId);

    /**
     * 修改商品颜色
     */
    ResultHandle<GoodsColor> updateGoodsColor(GoodsColor goodsColor);

    /**
     * 删除商品颜色
     */
    ResultHandle<GoodsColor> removeGoodsColor(int colorId);




    /**
     * 查询字典列表
     */
    List<DictionaryModel> fetchDictionaryList();

    /**
     * 保存字典
     */
    ResultHandle<DictionaryModel> saveDictionary(DictionaryModel dictionaryModel);

    /**
     * 根据id查询商品颜色
     * @param dictId 字典id
     */
    DictionaryModel getDictionaryById(int dictId);

    /**
     * 修改字典
     */
    ResultHandle<DictionaryModel> updateDictionary(DictionaryModel dictionaryModel);

    /**
     * 删除字典
     * @param dictId 字典id
     */
    ResultHandle<DictionaryModel> removeDictionary(int dictId);

    List<DictionaryModel> fetchDictionaryByType(String dictType);
}
