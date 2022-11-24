package com.gmodelo.cutoverback.Views;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DaoBeans.TarimasDescriptionBean;
import com.gmodelo.cutoverback.Repository.RouteRepository;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;

import java.util.List;

public class RouteViewModel extends AndroidViewModel {
    private RouteRepository routeRepository;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        routeRepository = new RouteRepository(application);
    }

    public MaterialDescrptionBean getMaterialById(MaterialDescrptionBean requestBean) {
        return routeRepository.getMaterialById(requestBean);
    }

    public List<MaterialDescrptionBean> getMaterialByValues(MaterialDescrptionBean requestbean) {
        return routeRepository.getMaterialByValues(requestbean);
    }

    public List<TarimasDescriptionBean> getTarimaByMaterial(MaterialDescrptionBean requestBean) {
        return routeRepository.getTarimaForMaterial(requestBean);
    }

    public List<MobileMaterialBean> getCXPCPPbyMaterial(MaterialDescrptionBean requestBean) {
        return routeRepository.getDataCPPCPforMaterial(requestBean);
    }

    public MobileMaterialBean getSingleMaterialValue(MaterialDescrptionBean requestBean) {
        return routeRepository.getSingleValue(requestBean);
    }

    public List<MaterialDescrptionBean> getMaterialesByDesc(String materialDesc) {
        return routeRepository.getMateriaByDesc(materialDesc);
    }

    public List<MaterialDescrptionBean> getMaterialByKey(String materialDesc) {
        return routeRepository.getMateriaByMatnr(materialDesc);
    }

    public List<MaterialDescrptionBean> getMaterialByMatch(String materialSearch) {
        return routeRepository.getMaterialByMatch(materialSearch);
    }

    public List<ZIACST_I360_OBJECTDATA_SapEntity> getCCPCPCbyMaterial(MaterialDescrptionBean requestBean){
        return routeRepository.getDataClassSystem(requestBean);
    }

    public List<E_ClassVal_SapEntity> getEClassVal(){
        return routeRepository.getEClassValuation();
    }


    public AbstractResults deleteAdminMode(){
        return routeRepository.removeDataFromDatabase();
    }

    public AbstractResults deleteClass(){
        return routeRepository.removeDataClass();
    }
    public AbstractResults deleteClassValuation(){
        return routeRepository.removeDataClassValuation();
    }

}
