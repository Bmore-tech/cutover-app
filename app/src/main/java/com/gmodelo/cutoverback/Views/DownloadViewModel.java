package com.gmodelo.cutoverback.Views;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.Repository.DownloadRepository;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.structure.ZIACFM_CLVAL;
import com.gmodelo.cutoverback.structure.ZIACMF_I360_EXT_SIS_CLAS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadViewModel extends AndroidViewModel {
    private DownloadRepository downloadRepository;

    public DownloadViewModel(@NonNull Application application) {
        super(application);
        downloadRepository = new DownloadRepository(application);
    }

    public AbstractResults loadMobileData(List<MobileMaterialBean> mobileMaterialBeans) {
        AbstractResults results = new AbstractResults();
        try {

            if (mobileMaterialBeans.size() > 75000) {
                int maxResult = mobileMaterialBeans.size();
                int i = 0;
                while (i < maxResult) {
                    int j = 0;
                    List<MobileMaterialBean> mobileMaterialBeansSubstract = new ArrayList<>();
                    for (; j < 50000; i++) {
                        try {
                            mobileMaterialBeansSubstract.add(mobileMaterialBeans.get(i));
                            j++;
                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    results = downloadRepository.doMobileMaterialInsert(mobileMaterialBeansSubstract);
                    if (results.getResultId() != ResponseVariability.SUCCESSFULL) {
                        break;
                    }
                }
            } else {
                results = downloadRepository.doMobileMaterialInsert(mobileMaterialBeans);
            }
        } catch (Exception e) {
            CrashReporter.logException(e);
            results.setResultId(ResponseVariability.SUCCESSFULL);
            results.setResultMsgAbs(e.getMessage());
        }
        return results;
    }

    public AbstractResults loadMaterialCrossTarimas(List<MaterialTarimasBean> materialTarimasBeans) {
        return downloadRepository.doMaterialTarimasInsert(materialTarimasBeans);
    }

    public AbstractResults loadClassSystem(ZIACMF_I360_EXT_SIS_CLAS ziacmf_i360_ext_sis_clas) {
        return downloadRepository.doClassInsert(ziacmf_i360_ext_sis_clas);
    }

    public AbstractResults loadValuationSystem(ZIACFM_CLVAL ziacfm_clval) {
        return downloadRepository.doClassValInsert(ziacfm_clval);
    }

    public HashMap<String, Integer> getCountedMasterData() {
        return downloadRepository.doCountMasterData();
    }

}
