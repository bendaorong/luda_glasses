package com.luda.dictionary;

import com.luda.comm.po.DictionaryType;
import com.luda.dictionary.model.DictionaryModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictFactory {
    private static volatile DictFactory instance = null;
    // 颜色
    private Map<String, String> colorMap = new HashMap<String, String>();
    private List<DictionaryModel> colorList = new ArrayList<>();

    // 品牌
    private Map<String, String> brandMap = new HashMap<String, String>();
    private List<DictionaryModel> brandList = new ArrayList<>();

    // 单位
    private Map<String, String> unitMap = new HashMap<String, String>();
    private List<DictionaryModel> unitList = new ArrayList<>();

    // 地区
    private Map<String, String> regionMap = new HashMap<>();
    private List<DictionaryModel> regionList = new ArrayList<>();

    /**
     *  初始化字典数据
     */
    private void init() {
        // 读取字典数据文件
        String dictData = loadDictDatas();
        if(StringUtils.isNotBlank(dictData)){
            JSONObject dictJson = JSONObject.fromObject(dictData);
            // 读取颜色
            initColorData(dictJson.getJSONArray("color"));
            // 读取品牌
            initBrandData(dictJson.getJSONArray("brand"));
            // 读取单位
            initUnitData(dictJson.getJSONArray("unit"));
            // 读取地区
            initRegionData(dictJson.getJSONArray("region"));
        }
    }

    /**
     * 初始化地区数据
     * @param regions
     */
    private void initRegionData(JSONArray regions) {
        for(int i=0; i<regions.size(); i++){
            JSONObject region = regions.getJSONObject(i);
            int id = region.getInt("id");
            String name = region.getString("name");
            regionMap.put(String.valueOf(id), name);
            regionList.add(new DictionaryModel(id, name, DictionaryType.REGION.name()));
        }
    }

    // 初始化颜色字典数据
    private void initColorData(JSONArray colorArr){
        for(int i=0; i<colorArr.size(); i++){
            JSONObject color = colorArr.getJSONObject(i);
            int id = color.getInt("id");
            String name = color.getString("name");
            colorMap.put(String.valueOf(id), name);
            colorList.add(new DictionaryModel(id, name, DictionaryType.GOODS_COLOR.name()));
        }
    }

    // 初始化品牌字典数据
    private void initBrandData(JSONArray colorArr){
        for(int i=0; i<colorArr.size(); i++){
            JSONObject color = colorArr.getJSONObject(i);
            int id = color.getInt("id");
            String name = color.getString("name");
            brandMap.put(String.valueOf(id), name);
            brandList.add(new DictionaryModel(id, name, DictionaryType.GOODS_BRAND.name()));
        }
    }

    // 初始化单位字典数据
    private void initUnitData(JSONArray colorArr){
        for(int i=0; i<colorArr.size(); i++){
            JSONObject color = colorArr.getJSONObject(i);
            int id = color.getInt("id");
            String name = color.getString("name");
            unitMap.put(String.valueOf(id), name);
            unitList.add(new DictionaryModel(id, name, DictionaryType.GOODS_UNIT.name()));
        }
    }

    public Map<String, String> getColorsMap(){
        return this.colorMap;
    }
    public List<DictionaryModel> getColorsList(){
        return colorList;
    }

    public Map<String, String> getBrandMap(){
        return this.brandMap;
    }
    public List<DictionaryModel> getBrandList(){
        return this.brandList;
    }

    public Map<String, String> getUnitMap(){
        return this.unitMap;
    }
    public List<DictionaryModel> getUnitList(){
        return this.unitList;
    }

    public Map<String, String> getRegionMap() {
        return regionMap;
    }

    public List<DictionaryModel> getRegionList() {
        return regionList;
    }

    private DictFactory() {
        init();
    }

    public static DictFactory getInstance() {
        if (instance == null) {
            synchronized (DictFactory.class) {
                if (instance == null) {
                    instance = new DictFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 加载字典数据
     * @return
     * @throws IOException
     */
    private String loadDictDatas() {
        try{
            InputStream in = DictFactory.class.getResourceAsStream("/dict.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            return builder.toString();
        }catch (Exception e){
            return null;
        }
    }
}