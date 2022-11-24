package com.gmodelo.cutoverback.Repository;

import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.EXCEPTION;

import android.app.Application;
import android.os.AsyncTask;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DataAccess.DownloadDao;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;
import com.gmodelo.cutoverback.structure.ZIACFM_CLVAL;
import com.gmodelo.cutoverback.structure.ZIACMF_I360_EXT_SIS_CLAS;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DownloadRepository {

    private DownloadDao downloadDao;

    public DownloadRepository(Application application) {
        InstanceOfDB db = InstanceOfDB.getInstanceOfDB(application);
        downloadDao = db.downloadDao();
    }


    public AbstractResults doMaterialTarimasInsert(List<MaterialTarimasBean> materialTarimasBeans) {
        AbstractResults results = new AbstractResults();
        try {
            results = new MaterialTarimasInsert(downloadDao).execute(materialTarimasBeans).get();
        } catch (InterruptedException e) {
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
        }
        return results;
    }

    private class MaterialTarimasInsert extends AsyncTask<List<MaterialTarimasBean>, Void, AbstractResults> {

        private DownloadDao asyncDao;

        public MaterialTarimasInsert(DownloadDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(List<MaterialTarimasBean>... lists) {
            AbstractResults results = new AbstractResults();
            try {
                asyncDao.loadMaterialTarimas(lists[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                results.setResultId(EXCEPTION);
                results.setResultMsgAbs(e.getMessage());
            }
            return results;
        }
    }

    public AbstractResults doClassInsert(ZIACMF_I360_EXT_SIS_CLAS ziacmf_i360_ext_sis_clas) {
        AbstractResults results = new AbstractResults();
        try {
            results = new ClassificationSystemInsert(downloadDao).execute(ziacmf_i360_ext_sis_clas.getObjectData()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
        }
        return results;

    }

    private class ClassificationSystemInsert extends AsyncTask<List<ZIACST_I360_OBJECTDATA_SapEntity>, Void, AbstractResults> {
        private DownloadDao asyncDao;

        public ClassificationSystemInsert(DownloadDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(List<ZIACST_I360_OBJECTDATA_SapEntity>... lists) {
            AbstractResults results = new AbstractResults();
            try {
                asyncDao.loadClassSystem(lists[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                results.setResultId(EXCEPTION);
                results.setResultMsgAbs(e.getMessage());
            }
            return results;
        }
    }

    public AbstractResults doClassValInsert(ZIACFM_CLVAL ziacfm_clval) {
        AbstractResults results = new AbstractResults();
        try {
            results = new ClassificationValuationInsert(downloadDao).execute(ziacfm_clval.geteClassVal_SapEntities()).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
        }
        return results;

    }

    private class ClassificationValuationInsert extends AsyncTask<List<E_ClassVal_SapEntity>, Void, AbstractResults> {
        private DownloadDao asyncDao;

        public ClassificationValuationInsert(DownloadDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(List<E_ClassVal_SapEntity>... lists) {
            AbstractResults results = new AbstractResults();
            try {
                asyncDao.loadClassValuation(lists[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                results.setResultId(EXCEPTION);
                results.setResultMsgAbs(e.getMessage());
            }
            return results;
        }
    }


    public AbstractResults doMobileMaterialInsert(List<MobileMaterialBean> mobileMaterialBeans) {
        AbstractResults results = new AbstractResults();
        try {
            results = new MobileMaterialInsert(downloadDao).execute(mobileMaterialBeans).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
            results.setResultId(EXCEPTION);
            results.setResultMsgAbs(e.getMessage());
        }
        return results;
    }

    private class MobileMaterialInsert extends AsyncTask<List<MobileMaterialBean>, Void, AbstractResults> {

        private DownloadDao asyncDao;

        public MobileMaterialInsert(DownloadDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected AbstractResults doInBackground(List<MobileMaterialBean>... lists) {
            AbstractResults results = new AbstractResults();
            try {
                asyncDao.loadMobileMaterial(lists[0]);
            } catch (Exception e) {
                CrashReporter.logException(e);
                results.setResultId(EXCEPTION);
                results.setResultMsgAbs(e.getMessage());
            }
            return results;
        }
    }

    public HashMap<String, Integer> doCountMasterData() {
        HashMap<String, Integer> doCount = ResponseVariability.INV_E_COUNTED_VALUES;
        try {
            doCount = new MobileMaterialCount(downloadDao).execute(doCount).get();
        } catch (InterruptedException e) {
            CrashReporter.logException(e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            CrashReporter.logException(e);
        }
        return doCount;
    }

    private class MobileMaterialCount extends AsyncTask<HashMap<String, Integer>, Void, HashMap<String, Integer>> {

        private DownloadDao asyncDao;

        public MobileMaterialCount(DownloadDao asyncDao) {
            this.asyncDao = asyncDao;
        }


        @Override
        protected HashMap<String, Integer> doInBackground(HashMap<String, Integer>... maps) {
            try {
                if (CommonUtilities.checkAuthorization("AUTHORIZATION")) {
                    maps[0].put(ResponseVariability.INV_E_MATERIALS, asyncDao.getCountFromMaterial());
                    maps[0].put(ResponseVariability.INV_E_TARIMAS, asyncDao.getCountFromTarimas());
                    maps[0].put(ResponseVariability.INV_E_CLASS_SYSTEM, asyncDao.getCountFromClassSys());
                    maps[0].put(ResponseVariability.INV_E_CLASS_VAL, asyncDao.getCountFromClassVal());
                }
            } catch (Exception e) {
                CrashReporter.logException(e);
            }
            return maps[0];
        }
    }
}
