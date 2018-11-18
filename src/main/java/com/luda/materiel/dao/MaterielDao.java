package com.luda.materiel.dao;

import com.luda.comm.po.ResultHandle;
import com.luda.inventory.model.MaterielQueryBean;
import com.luda.materiel.model.MaterielModel;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public interface MaterielDao {
    List<MaterielModel> fetchMaterielList();

    int saveMateriel(MaterielModel materielModel);

    MaterielModel getById(int id);

    int updateMateriel(MaterielModel materielModel);

    int removeMateriel(int id);

    String getMaxCode();

    int getMaterielTotalCount(MaterielQueryBean queryBean);

    List<MaterielModel> fetchMaterielListPage(MaterielQueryBean queryBean);
}
