package com.luda.materiel.service;

import com.luda.comm.po.ResultHandle;
import com.luda.materiel.model.MaterielModel;

import java.util.List;

/**
 * 商品
 * Created by Administrator on 2017/10/15.
 */
public interface MaterielService {
    /**
     * 查询商品列表
     * @return
     */
    List<MaterielModel> fetchMaterielList();

    /**
     * 创建商品
     * @param materielModel
     */
    ResultHandle<MaterielModel> saveMateriel(MaterielModel materielModel);

    /**
     * 根据id查询商品
     * @param id
     */
    MaterielModel getById(int id);

    /**
     * 编辑商品
     * @param materielModel
     */
    ResultHandle<MaterielModel> updateMateriel(MaterielModel materielModel);

    /**
     * 删除商品
     * @param id 商品id
     */
    ResultHandle<MaterielModel> removeMateriel(int id);
}
