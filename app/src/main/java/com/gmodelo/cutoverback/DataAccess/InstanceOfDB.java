package com.gmodelo.cutoverback.DataAccess;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gmodelo.cutoverback.Entities.TypeConverter.DateTypeConverter;
import com.gmodelo.cutoverback.Entities.Utils.TableConstants;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;

@Database(entities = {MobileMaterialBean.class, MaterialTarimasBean.class, ZIACST_I360_OBJECTDATA_SapEntity.class, E_ClassVal_SapEntity.class}, version = 10)
@TypeConverters(DateTypeConverter.class)
public abstract class InstanceOfDB extends RoomDatabase {

    public abstract DownloadDao downloadDao();

    public abstract RouteDao routeDao();

    public static InstanceOfDB instanceOfDB;

    public static InstanceOfDB getInstanceOfDB(final Context context) {
        if (instanceOfDB == null) {
            instanceOfDB = Room.databaseBuilder(context, InstanceOfDB.class, TableConstants.DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instanceOfDB;
    }


    public void cleanUp() {
        instanceOfDB = null;
    }
}
