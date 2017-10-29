package com.luda.materiel.service.impl;

import com.luda.comm.po.ResultHandle;
import com.luda.materiel.dao.MaterielDao;
import com.luda.materiel.model.MaterielModel;
import com.luda.materiel.service.MaterielService;
import com.luda.util.CodeBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
@Service("materielService")
public class MaterielServiceImpl implements MaterielService{
    // 商品编号缓存
    private static String CURRENT_CODE = null;

    @Autowired
    private MaterielDao materielDao;

    @Override
    public List<MaterielModel> fetchMaterielList() {
        return materielDao.fetchMaterielList();
    }

    @Override
    public ResultHandle<MaterielModel> saveMateriel(MaterielModel materielModel) {
        ResultHandle<MaterielModel> resultHandle = new ResultHandle();

        // 验证商品信息
        String errorMsg = checkMateriel(materielModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        // 初始化商品信息
        initMateriel(materielModel);

        int result = materielDao.saveMateriel(materielModel);
        if(result > 0){
            resultHandle.setReturnContent(materielModel);
        }else {
            resultHandle.setMsg("商品创建失败");
        }
        return resultHandle;
    }

    // 初始化商品信息
    private void initMateriel(MaterielModel materielModel) {
        materielModel.setCode(getMaterielCode());
        materielModel.setUseFlag(1);
    }

    /**
     * 获取商品编号
     * 商品编号格式：1710200001，171020为当前日期，0001为当前计数
     * 从缓存计数器中获取商品编号最新值，若为当天的，则计数部分+1，
     * 若不是当天的，生成当天日期前缀，并从1开始计数，不足4位补0
     **/
    private synchronized String getMaterielCode() {
        // 取商品最大编号初始化缓存数据
        if(StringUtils.isEmpty(CURRENT_CODE)){
            CURRENT_CODE = materielDao.getMaxCode();
        }

        String nextCode = CodeBuilder.buildMaterielCode(CURRENT_CODE);

        // 刷新缓存
        CURRENT_CODE = nextCode;

        return nextCode;
    }

    // 验证商品信息
    private String checkMateriel(MaterielModel materielModel) {
        if(StringUtils.isEmpty(materielModel.getName())){
            return "请填写商品名称";
        }
        if(StringUtils.isEmpty(materielModel.getBarcode())){
            return "请填写条码";
        }
        if(materielModel.getTypeId() == 0){
            return "请选择商品类型";
        }
        return null;
    }

    @Override
    public MaterielModel getById(int id) {
        return materielDao.getById(id);
    }

    @Override
    public ResultHandle<MaterielModel> updateMateriel(MaterielModel materielModel) {
        ResultHandle<MaterielModel> resultHandle = new ResultHandle();
        // 数据验证
        String errorMsg = checkMateriel(materielModel);
        if(StringUtils.isNotEmpty(errorMsg)){
            resultHandle.setMsg(errorMsg);
            return resultHandle;
        }

        int result = materielDao.updateMateriel(materielModel);
        if(result > 0){
            resultHandle.setReturnContent(materielModel);
        }else {
            resultHandle.setMsg("商品更新失败");
        }
        return resultHandle;
    }

    @Override
    public ResultHandle<MaterielModel> removeMateriel(int id) {
        ResultHandle<MaterielModel> resultHandle = new ResultHandle();
        int result = materielDao.removeMateriel(id);
        if(result <= 0){
            resultHandle.setMsg("商品删除失败");
        }
        return resultHandle;
    }
}
