package com.luda.dictionary.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.dictionary.model.*;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
public interface DictionaryDao {
    List<GoodsType> fetchGoodsTypeList();

    int saveGoodsType(GoodsType goodsType);

    List<GoodsKind> fetchGoodsKindList();

    GoodsType getGoodsTypeById(int typeId);

    int updateGoodsType(GoodsType goodsType);

    int removeGoodsType(int typeId);

    List<GoodsColor> fetchGoodsColorList();

    int saveGoodsColor(GoodsColor goodsColor);

    GoodsColor getGoodsColorById(int colorId);

    int updateGoodsColor(GoodsColor goodsColor);

    int removeGoodsColor(int colorId);


    List<DictionaryModel> fetchDictionaryList();

    int saveDictionary(DictionaryModel dictionaryModel);

    DictionaryModel getDictionaryById(int dictId);

    int updateDictionary(DictionaryModel dictionaryModel);

    int removeDictionary(int dictId);


    List<GoodsBrand> fetchGoodsBrandList();

    int saveGoodsBrand(GoodsBrand goodsBrand);

    GoodsBrand getGoodsBrandById(int brandId);

    int updateGoodsBrand(GoodsBrand goodsBrand);

    int removeGoodsBrand(int brandId);
}
