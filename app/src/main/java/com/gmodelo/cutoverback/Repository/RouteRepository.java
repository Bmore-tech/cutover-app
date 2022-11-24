package com.gmodelo.cutoverback.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DaoBeans.TarimasDescriptionBean;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.DataAccess.RouteDao;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RouteRepository {

    private RouteDao routeDao;

    public RouteRepository(Application application) {
        InstanceOfDB db = InstanceOfDB.getInstanceOfDB(application);
        routeDao = db.routeDao();
    }

    public MaterialDescrptionBean getMaterialById(MaterialDescrptionBean bean) {
        MaterialDescrptionBean mobileMaterialBean;
        try {
            mobileMaterialBean = new SingleMobileMaterialBean(routeDao).execute(bean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            mobileMaterialBean = null;
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            mobileMaterialBean = null;
        }
        return mobileMaterialBean;
    }


    private class SingleMobileMaterialBean extends AsyncTask<String, Void, MaterialDescrptionBean> {

        RouteDao asyncDao;

        private SingleMobileMaterialBean(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected MaterialDescrptionBean doInBackground(String... matnr) {
            MaterialDescrptionBean returnBean;
            try {
                returnBean = asyncDao.getMaterialById(matnr[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                returnBean = null;
            }
            return returnBean;
        }
    }

    public List<MaterialDescrptionBean> getMaterialByValues(MaterialDescrptionBean requestBean) {
        List<MaterialDescrptionBean> returnList = new ArrayList<>();

        try {
            returnList = new GetMatValuesByData(routeDao).execute(requestBean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            returnList = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            returnList = new ArrayList<>();
        }
        return returnList;
    }

    private class GetMatValuesByData extends AsyncTask<String, Void, List<MaterialDescrptionBean>> {

        RouteDao asyncDao;

        private GetMatValuesByData(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<MaterialDescrptionBean> doInBackground(String... eans) {
            List<MaterialDescrptionBean> returnList = new ArrayList<>();
            try {
                returnList = asyncDao.getMaterialByEan(eans[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                returnList = new ArrayList<>();
            }
            return returnList;
        }
    }

    public List<TarimasDescriptionBean> getTarimaForMaterial(MaterialDescrptionBean bean) {
        List<TarimasDescriptionBean> listBean = new ArrayList<>();
        try {
            listBean = new GetTarimasByMaterial(routeDao).execute(bean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            listBean = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            listBean = new ArrayList<>();
        }

        return listBean;
    }

    private class GetTarimasByMaterial extends AsyncTask<String, Void, List<TarimasDescriptionBean>> {

        RouteDao asyncDao;

        private GetTarimasByMaterial(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<TarimasDescriptionBean> doInBackground(String... matnr) {
            List<TarimasDescriptionBean> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getpacknrByMat(matnr[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }

            return listBean;
        }
    }


    public List<MobileMaterialBean> getDataCPPCPforMaterial(MaterialDescrptionBean requestBean) {
        List<MobileMaterialBean> mobileMaterialBeans = new ArrayList<>();
        try {
            mobileMaterialBeans = new GetCCPCPforMaterial(routeDao).execute(requestBean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            mobileMaterialBeans = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            mobileMaterialBeans = new ArrayList<>();
        }
        return mobileMaterialBeans;
    }

    private class GetCCPCPforMaterial extends AsyncTask<String, Void, List<MobileMaterialBean>> {
        RouteDao asyncDao;

        private GetCCPCPforMaterial(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<MobileMaterialBean> doInBackground(String... matnr) {
            List<MobileMaterialBean> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getInfoForMaterial(matnr[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }

    public List<ZIACST_I360_OBJECTDATA_SapEntity> getDataClassSystem(MaterialDescrptionBean requestBean) {
        List<ZIACST_I360_OBJECTDATA_SapEntity> mobileMaterialBeans = new ArrayList<>();
        try {
            mobileMaterialBeans = new GetMaterialClassSystem(routeDao).execute(requestBean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            mobileMaterialBeans = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            mobileMaterialBeans = new ArrayList<>();
        }
        return mobileMaterialBeans;
    }

    private class GetMaterialClassSystem extends AsyncTask<String, Void, List<ZIACST_I360_OBJECTDATA_SapEntity>> {
        RouteDao asyncDao;

        private GetMaterialClassSystem(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<ZIACST_I360_OBJECTDATA_SapEntity> doInBackground(String... matnr) {
            List<ZIACST_I360_OBJECTDATA_SapEntity> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getSysClassMaterial(matnr[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }


    public MobileMaterialBean getSingleValue(MaterialDescrptionBean requestBean) {
        MobileMaterialBean mobileMaterialBean = new MobileMaterialBean();
        try {
            mobileMaterialBean = new GetValueForSingleMaterial(routeDao).execute(requestBean.getMatnr()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            mobileMaterialBean = null;
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            mobileMaterialBean = null;
        }
        return mobileMaterialBean;
    }


    public class GetValueForSingleMaterial extends AsyncTask<String, Void, MobileMaterialBean> {

        RouteDao asyncDao;

        private GetValueForSingleMaterial(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected MobileMaterialBean doInBackground(String... matnr) {
            MobileMaterialBean listBean = new MobileMaterialBean();
            try {
                listBean = asyncDao.getInfoForSingleMaterial(matnr[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = null;
            }
            return listBean;
        }
    }


    public List<MaterialDescrptionBean> getMateriaByDesc(String materialDesc) {
        List<MaterialDescrptionBean> materialDescrptionBeans = new ArrayList<>();
        try {
            materialDescrptionBeans = new GetValueByMaterialDesc(routeDao).execute(materialDesc).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
        }
        return materialDescrptionBeans;
    }

    public class GetValueByMaterialDesc extends AsyncTask<String, Void, List<MaterialDescrptionBean>> {

        RouteDao asyncDao;

        private GetValueByMaterialDesc(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<MaterialDescrptionBean> doInBackground(String... matnr) {
            List<MaterialDescrptionBean> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getInfoAutocompleteDesciption("%" + matnr[0] + "%");
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }

    public List<MaterialDescrptionBean> getMateriaByMatnr(String materialDesc) {
        List<MaterialDescrptionBean> materialDescrptionBeans = new ArrayList<>();
        try {
            materialDescrptionBeans = new GetValueByMaterialKey(routeDao).execute(materialDesc).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
        }
        return materialDescrptionBeans;
    }

    public class GetValueByMaterialKey extends AsyncTask<String, Void, List<MaterialDescrptionBean>> {

        RouteDao asyncDao;

        private GetValueByMaterialKey(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<MaterialDescrptionBean> doInBackground(String... matnr) {
            List<MaterialDescrptionBean> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getInfoAutocompleteMaterial("%" + matnr[0] + "%");
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }

    public List<MaterialDescrptionBean> getMaterialByMatch(String materialSearch) {
        List<MaterialDescrptionBean> materialDescrptionBeans = new ArrayList<>();
        try {
            materialDescrptionBeans = new GetValueByMatchCode(routeDao).execute(materialSearch).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            materialDescrptionBeans = new ArrayList<>();
        }
        return materialDescrptionBeans;
    }

    public class GetValueByMatchCode extends AsyncTask<String, Void, List<MaterialDescrptionBean>> {

        RouteDao asyncDao;

        private GetValueByMatchCode(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<MaterialDescrptionBean> doInBackground(String... matnr) {
            List<MaterialDescrptionBean> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getInfoMatchCode("%" + matnr[0] + "%");
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }

    public List<E_ClassVal_SapEntity> getEClassValuation() {
        List<E_ClassVal_SapEntity> e_classVal_sapEntities = new ArrayList<>();
        try {
            e_classVal_sapEntities = new GetClassValuation(routeDao).execute().get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            e_classVal_sapEntities = new ArrayList<>();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            e_classVal_sapEntities = new ArrayList<>();
        }
        return e_classVal_sapEntities;
    }

    public class GetClassValuation extends AsyncTask<Void, Void, List<E_ClassVal_SapEntity>> {

        RouteDao asyncDao;

        private GetClassValuation(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected List<E_ClassVal_SapEntity> doInBackground(Void... voids) {
            List<E_ClassVal_SapEntity> listBean = new ArrayList<>();
            try {
                listBean = asyncDao.getSysClassValuation();
            } catch (Exception e) {
                CrashReporter.logException(e);
                listBean = new ArrayList<>();
            }
            return listBean;
        }
    }

    public AbstractResults removeDataFromDatabase() {
        AbstractResults abstractResults = new AbstractResults();
        try {
            abstractResults = new DeleteDatabaseAdminMode(routeDao).execute().get();
        } catch (ExecutionException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
        } catch (InterruptedException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return abstractResults;
    }

    public class DeleteDatabaseAdminMode extends AsyncTask<Void, Void, AbstractResults> {
        RouteDao asyncDao;

        private DeleteDatabaseAdminMode(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(Void... voids) {
            AbstractResults result = new AbstractResults();
            try {
                if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                    asyncDao.deleteMATXTAR();
                    asyncDao.deleteMOBMAT();
                    asyncDao.deleteSYSCLASS();
                    asyncDao.deleteSYSVALUATION();
                }
            } catch (Exception e) {
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs(e.getMessage());
            }
            return result;
        }
    }

    public AbstractResults removeDataClass() {
        AbstractResults abstractResults = new AbstractResults();
        try {
            abstractResults = new DeleteClassAdminMode(routeDao).execute().get();
        } catch (ExecutionException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
        } catch (InterruptedException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return abstractResults;
    }

    public class DeleteClassAdminMode extends AsyncTask<Void, Void, AbstractResults> {
        RouteDao asyncDao;

        private DeleteClassAdminMode(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(Void... voids) {
            AbstractResults result = new AbstractResults();
            try {
                if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                    asyncDao.deleteSYSCLASS();
                }
            } catch (Exception e) {
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs(e.getMessage());
            }
            return result;
        }
    }

    public AbstractResults removeDataClassValuation() {
        AbstractResults abstractResults = new AbstractResults();
        try {
            abstractResults = new DeleteClassValuationAdminMode(routeDao).execute().get();
        } catch (ExecutionException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
        } catch (InterruptedException e) {
            abstractResults.setResultId(ResponseVariability.EXCEPTION);
            abstractResults.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return abstractResults;
    }

    public class DeleteClassValuationAdminMode extends AsyncTask<Void, Void, AbstractResults> {
        RouteDao asyncDao;

        private DeleteClassValuationAdminMode(RouteDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(Void... voids) {
            AbstractResults result = new AbstractResults();
            try {
                if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                    asyncDao.deleteSYSVALUATION();
                }
            } catch (Exception e) {
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs(e.getMessage());
            }
            return result;
        }
    }

}
