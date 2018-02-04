package com.luda.dictionary.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.dictionary.model.*;
import com.luda.dictionary.service.DictionaryService;
import com.luda.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
@Controller
@RequestMapping("/rest/dictionary")
public class DictionaryController extends BaseController{
    private static final Logger log = Logger.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * ------------------------------------商品类型----------------------------------------
     */

    /**
     * 查询商品类型列表
     * @return
     */
    @RequestMapping("/goodsType/list")
    @ResponseBody
    public String fetchGoodsTypeList(){
        String result = "";
        try {
            List<GoodsType> goodsTypeList = dictionaryService.fetchGoodsTypeList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(goodsTypeList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch goods type list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存商品类型
     * @param goodsType
     * @return
     */
    @RequestMapping("/goodsType/saveGoodsType")
    @ResponseBody
    public String saveGoodsType(@RequestBody GoodsType goodsType){
        String result = "";
        try {
            ResultHandle<GoodsType> resultHandle = dictionaryService.saveGoodsType(goodsType);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save goods type error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据id查询商品类型
     * @return
     */
    @RequestMapping("/goodsType/getById/{typeId}")
    @ResponseBody
    public String getGoodsTypeById(@PathVariable int typeId){
        String result = "";
        try {
            GoodsType goodsType = dictionaryService.getGoodsTypeById(typeId);
            String data = CommonUtils.convertBeanToJson(goodsType, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get goods type by id error, typeId:" + typeId, e);
        }
        return result;
    }

    /**
     * 更新产品类型
     */
    @RequestMapping("/goodsType/updateGoodsType")
    @ResponseBody
    public String updateGoodsType(@RequestBody GoodsType goodsType){
        String result = "";
        try {
            ResultHandle<GoodsType> resultHandle = dictionaryService.updateGoodsType(goodsType);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update goods type error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除商品类型
     */
    @RequestMapping("/goodsType/removeGoodsType/{typeId}")
    @ResponseBody
    public String removeGoodsType(@PathVariable int typeId){
        String result = "";
        try {
            ResultHandle<GoodsType> resultHandle = dictionaryService.removeGoodsType(typeId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove goods type error, typeId:" + typeId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * ------------------------------------颜色----------------------------------------
     */

    /**
     * 查询颜色列表
     * @return
     */
    @RequestMapping("/goodsColor/list")
    @ResponseBody
    public String fetchGoodsColorList(){
        String result = "";
        try {
            List<GoodsColor> goodsColorList = dictionaryService.fetchGoodsColorList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(goodsColorList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch goods color list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存颜色
     * @param goodsColor
     * @return
     */
    @RequestMapping("/goodsColor/saveGoodsColor")
    @ResponseBody
    public String saveGoodsType(@RequestBody GoodsColor goodsColor){
        String result = "";
        try {
            ResultHandle<GoodsColor> resultHandle = dictionaryService.saveGoodsColor(goodsColor);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save goods color error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据id查询颜色
     * @return
     */
    @RequestMapping("/goodsColor/getById/{colorId}")
    @ResponseBody
    public String getGoodsColorById(@PathVariable int colorId){
        String result = "";
        try {
            GoodsColor goodsColor = dictionaryService.getGoodsColorById(colorId);
            String data = CommonUtils.convertBeanToJson(goodsColor, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get goods color by id error, colorId:" + colorId, e);
        }
        return result;
    }

    /**
     * 更新颜色
     */
    @RequestMapping("/goodsColor/updateGoodsColor")
    @ResponseBody
    public String updateGoodsColor(@RequestBody GoodsColor goodsColor){
        String result = "";
        try {
            ResultHandle<GoodsColor> resultHandle = dictionaryService.updateGoodsColor(goodsColor);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update goods color error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除颜色
     */
    @RequestMapping("/goodsColor/removeGoodsColor/{colorId}")
    @ResponseBody
    public String removeGoodsColor(@PathVariable int colorId){
        String result = "";
        try {
            ResultHandle<GoodsColor> resultHandle = dictionaryService.removeGoodsColor(colorId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove goods color error, colorId:" + colorId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * ------------------------------------字典----------------------------------------
     */
    /**
     * 查询字典列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchDictionaryList(){
        String result = "";
        try {
            List<DictionaryModel> dictionaryList = dictionaryService.fetchDictionaryList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(dictionaryList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch dictionary list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据类型查询字典
     * @return
     */
    @RequestMapping("/fetchByType/{dictType}")
    @ResponseBody
    public String fetchByType(@PathVariable String dictType){
        String result = "";
        try {
            List<DictionaryModel> dictionaryList = dictionaryService.fetchDictionaryByType(dictType);
            String data = CommonUtils.convertBeanCollectionToJsonArray(dictionaryList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch dictionary list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存字典
     * @param dictionaryModel
     * @return
     */
    @RequestMapping("/saveDictionary")
    @ResponseBody
    public String saveDictionary(@RequestBody DictionaryModel dictionaryModel){
        String result = "";
        try {
            ResultHandle<DictionaryModel> resultHandle = dictionaryService.saveDictionary(dictionaryModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save dictionary error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据id查询字典
     * @return
     */
    @RequestMapping("/getById/{dictId}")
    @ResponseBody
    public String getDictionaryById(@PathVariable int dictId){
        String result = "";
        try {
            DictionaryModel dictionaryModel = dictionaryService.getDictionaryById(dictId);
            String data = CommonUtils.convertBeanToJson(dictionaryModel, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get dictionary by id error, dictId:" + dictId, e);
        }
        return result;
    }

    /**
     * 更新字典
     */
    @RequestMapping("/updateDictionary")
    @ResponseBody
    public String updateDictionary(@RequestBody DictionaryModel dictionaryModel){
        String result = "";
        try {
            ResultHandle<DictionaryModel> resultHandle = dictionaryService.updateDictionary(dictionaryModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update dictionary error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除字典
     */
    @RequestMapping("/removeDictionary/{dictId}")
    @ResponseBody
    public String removeDictionary(@PathVariable int dictId){
        String result = "";
        try {
            ResultHandle<DictionaryModel> resultHandle = dictionaryService.removeDictionary(dictId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove dictionary error, dictId:" + dictId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * ------------------------------------商品种类----------------------------------------
     */

    @RequestMapping("/goodsKind/list")
    @ResponseBody
    public String fetchGoodsKindList(){
        String result = "";
        try {
            List<GoodsKind> goodsKindList = dictionaryService.fetchGoodsKindList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(goodsKindList, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch goods kind list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * ------------------------------------商品品牌----------------------------------------
     */
    /**
     * 查询品牌列表
     * @return
     */
    @RequestMapping("/goodsBrand/list")
    @ResponseBody
    public String fetchGoodsBrandList(){
        String result;
        try {
            List<GoodsBrand> goodsBrandList = dictionaryService.fetchGoodsBrandList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(goodsBrandList,null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch goods brand list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 保存商品品牌
     * @param goodsBrand
     * @return
     */
    @RequestMapping("/goodsBrand/saveGoodsBrand")
    @ResponseBody
    public String saveGoodsBrand(@RequestBody GoodsBrand goodsBrand){
        String result = "";
        try {
            ResultHandle<GoodsBrand> resultHandle = dictionaryService.saveGoodsBrand(goodsBrand);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save goods brand error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 根据id查询品牌
     * @return
     */
    @RequestMapping("/goodsBrand/getById/{brandId}")
    @ResponseBody
    public String getGoodsBrandById(@PathVariable int brandId){
        String result = "";
        try {
            GoodsBrand goodsBrand = dictionaryService.getGoodsBrandById(brandId);
            String data = CommonUtils.convertBeanToJson(goodsBrand, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            result = getFailResult("系统异常");
            log.error("get goods brand by id error, colorId:" + brandId, e);
        }
        return result;
    }

    /**
     * 更新品牌
     */
    @RequestMapping("/goodsBrand/updateGoodsBrand")
    @ResponseBody
    public String updateGoodsBrand(@RequestBody GoodsBrand goodsBrand){
        String result = "";
        try {
            ResultHandle<GoodsBrand> resultHandle = dictionaryService.updateGoodsBrand(goodsBrand);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update goods brand error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    /**
     * 删除品牌
     */
    @RequestMapping("/goodsBrand/removeGoodsBrand/{brandId}")
    @ResponseBody
    public String removeGoodsBrand(@PathVariable int brandId){
        String result = "";
        try {
            ResultHandle<GoodsBrand> resultHandle = dictionaryService.removeGoodsBrand(brandId);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove goods brand error, colorId:" + brandId, e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
