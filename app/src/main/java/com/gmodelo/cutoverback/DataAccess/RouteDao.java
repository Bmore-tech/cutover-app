package com.gmodelo.cutoverback.DataAccess;

import androidx.room.Dao;
import androidx.room.Query;

import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DaoBeans.TarimasDescriptionBean;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;

import java.util.List;

@Dao
public interface RouteDao {

    @Query("SELECT matnr, maktx FROM MOBMAT WHERE matnr = :matnr GROUP BY matnr, maktx LIMIT 1")
    MaterialDescrptionBean getMaterialById(String matnr);

    @Query("SELECT matnr, maktx FROM MOBMAT WHERE eannr = :eanS or ean11=:eanS or matnr =:eanS or zeinr = :eanS group by matnr, maktx")
    List<MaterialDescrptionBean> getMaterialByEan(String eanS);

    @Query("SELECT vhilm, maktx FROM MATXTAR where vhilm!=:vhilm and paitemtype = 'P' and packitem = '000010' and  packnr in " +
            "(SELECT DISTINCT packnr FROM MATXTAR where vhilm=:vhilm and paitemtype = 'I') group by vhilm, maktx")
    List<TarimasDescriptionBean> getpacknrByMat(String vhilm);

    @Query("SELECT * FROM MOBMAT WHERE matnr =:matnr AND (MEINH LIKE 'CPC' OR MEINH LIKE 'CPP' OR MEINH LIKE '[A-Z][0-9][0-9]' OR MEINH = 'PAL')")
    List<MobileMaterialBean> getInfoForMaterial(String matnr);

    @Query("SELECT * FROM MOBMAT WHERE matnr =:matnr limit 1")
    MobileMaterialBean getInfoForSingleMaterial(String matnr);

    @Query("SELECT matnr, maktx from MOBMAT where maktx like :maktx GROUP BY matnr, maktx ORDER BY matnr desc")
    List<MaterialDescrptionBean> getInfoAutocompleteDesciption(String maktx);

    @Query("SELECT matnr,maktx from MOBMAT WHERE matnr LIKE :matnr GROUP BY matnr, maktx")
    List<MaterialDescrptionBean> getInfoAutocompleteMaterial(String matnr);

    @Query("SELECT matnr, maktx from MOBMAT where maktx like :matrn OR matnr like :matrn GROUP BY matnr, maktx ORDER BY matnr ASC LIMIT 200")
    List<MaterialDescrptionBean> getInfoMatchCode(String matrn);

    @Query("SELECT object, smbez, atflv, atnam, maktx FROM SYSCLASS WHERE object = :matnr")
    List<ZIACST_I360_OBJECTDATA_SapEntity> getSysClassMaterial(String matnr);

    @Query("SELECT bwtar, kkref, krftx FROM SYSVAL")
    List<E_ClassVal_SapEntity> getSysClassValuation();

    @Query("DELETE FROM MATXTAR")
    void deleteMATXTAR();

    @Query("DELETE FROM MOBMAT")
    void deleteMOBMAT();

    @Query("DELETE FROM SYSCLASS")
    void deleteSYSCLASS();

    @Query("DELETE FROM SYSVAL")
    void deleteSYSVALUATION();

}
