package com.gmodelo.cutoverback.DataAccess;




import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;

import java.util.List;

@Dao
public interface DownloadDao {


    @Insert(onConflict = REPLACE)
    void loadMaterialTarimas(List<MaterialTarimasBean> materialTarimasBeans);

    @Insert(onConflict = REPLACE)
    void loadMobileMaterial(List<MobileMaterialBean> mobileMaterialBeans);

    @Insert(onConflict = REPLACE)
    void loadClassSystem(List<ZIACST_I360_OBJECTDATA_SapEntity> ziacst_i360_objectdata_sapEntities);

    @Insert(onConflict = REPLACE)
    void loadClassValuation(List<E_ClassVal_SapEntity> e_classVal_sapEntities);

    @Query("SELECT count(*) FROM  MOBMAT")
    Integer getCountFromMaterial();

    @Query("SELECT count(*) FROM  MATXTAR")
    Integer getCountFromTarimas();

    @Query("SELECT count(*) FROM  SYSCLASS")
    Integer getCountFromClassSys();

    @Query("SELECT count(*) FROM  SYSVAL")
    Integer getCountFromClassVal();
}
