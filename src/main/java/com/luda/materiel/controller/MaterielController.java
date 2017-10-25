package com.luda.materiel.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.common.controller.BaseController;
import com.luda.materiel.model.MaterielModel;
import com.luda.materiel.service.MaterielService;
import com.luda.user.model.AdminUserModel;
import com.luda.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 物料管理
 * Created by Administrator on 2017/10/15.
 */
@Controller
@RequestMapping("/rest/materiel")
public class MaterielController extends BaseController{
    private static final Logger log = Logger.getLogger(MaterielController.class);

    @Autowired
    private MaterielService materielService;

    /**
     * 商品列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public String fetchMaterielList(){
        String result = "";
        try {
            List<MaterielModel> materiels = materielService.fetchMaterielList();
            String data = CommonUtils.convertBeanCollectionToJsonArray(materiels, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("fetch materiel list error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/saveMateriel")
    @ResponseBody
    public String saveMateriel(@RequestBody MaterielModel materielModel, HttpSession session){
        String result = "";
        try {
            AdminUserModel adminUserModel = getLoginUser(session);
            materielModel.setCreatorUserId(adminUserModel.getAdminUserId());
            materielModel.setUpdateUserId(adminUserModel.getAdminUserId());

            ResultHandle resultHandle = materielService.saveMateriel(materielModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("save materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/getById/{id}")
    @ResponseBody
    public String getById(@PathVariable int id){
        String result = "";
        try {
            MaterielModel materielModel = materielService.getById(id);
            String data = CommonUtils.convertBeanToJson(materielModel, null).toString();
            result = getSuccessResult(data);
        }catch (Exception e){
            log.error("get by id error, id:" + id, e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/updateMateriel")
    @ResponseBody
    public String updateMateriel(@RequestBody MaterielModel materielModel, HttpSession session){
        String result = "";
        try {
            AdminUserModel adminUserModel = (AdminUserModel) session.getAttribute("sessionInfo");
            materielModel.setUpdateUserId(adminUserModel.getAdminUserId());

            ResultHandle resultHandle = materielService.updateMateriel(materielModel);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                result = getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("update materiel error", e);
            result = getFailResult("系统异常");
        }
        return result;
    }

    @RequestMapping("/removeMateriel/{id}")
    @ResponseBody
    public String removeMateriel(@PathVariable int id){
        String result = "";
        try {
            ResultHandle<MaterielModel> resultHandle = materielService.removeMateriel(id);
            if(resultHandle.isSuccess()){
                result = getSuccessResult();
            }else {
                getFailResult(resultHandle.getMsg());
            }
        }catch (Exception e){
            log.error("remove materiel error, id:" + id, e);
            result = getFailResult("系统异常");
        }
        return result;
    }
}
