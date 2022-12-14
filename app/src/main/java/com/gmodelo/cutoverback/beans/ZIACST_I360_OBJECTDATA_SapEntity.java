package com.gmodelo.cutoverback.beans;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "SYSCLASS", primaryKeys = {"object", "smbez", "atflv", "atnam"})
public class ZIACST_I360_OBJECTDATA_SapEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1399274843408940942L;
    @NonNull
    String object;
    @NonNull
    String smbez;
    @NonNull
    String atflv;
    @NonNull
    String atnam;
    String maktx;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSmbez() {
        return smbez;
    }

    public void setSmbez(String smbez) {
        this.smbez = smbez;
    }

    public String getAtflv() {
        return atflv;
    }

    public void setAtflv(String atflv) {
        this.atflv = atflv;
    }

    public String getAtnam() {
        return atnam;
    }

    public void setAtnam(String atnam) {
        this.atnam = atnam;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public ZIACST_I360_OBJECTDATA_SapEntity() {
        super();
        maktx ="";
        // TODO Auto-generated constructor stub
    }


    public ZIACST_I360_OBJECTDATA_SapEntity(String object, String smbez, String atflv, String atnam) {
        super();
        this.object = object;
        this.smbez = smbez;
        this.atflv = atflv;
        this.atnam = atnam;
    }


    @Override
    public String toString() {
        return "ZIACST_I360_OBJECTDATA_SapEntity [object=" + object + ", smbez=" + smbez + ", atflv=" + atflv
                + ", atnam=" + atnam + ", maktx=" + maktx + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((atflv == null) ? 0 : atflv.hashCode());
        result = prime * result + ((atnam == null) ? 0 : atnam.hashCode());
        result = prime * result + ((maktx == null) ? 0 : maktx.hashCode());
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        result = prime * result + ((smbez == null) ? 0 : smbez.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ZIACST_I360_OBJECTDATA_SapEntity other = (ZIACST_I360_OBJECTDATA_SapEntity) obj;
        if (atflv == null) {
            if (other.atflv != null)
                return false;
        } else if (!atflv.equals(other.atflv))
            return false;
        if (atnam == null) {
            if (other.atnam != null)
                return false;
        } else if (!atnam.equals(other.atnam))
            return false;
        if (maktx == null) {
            if (other.maktx != null)
                return false;
        } else if (!maktx.equals(other.maktx))
            return false;
        if (object == null) {
            if (other.object != null)
                return false;
        } else if (!object.equals(other.object))
            return false;
        if (smbez == null) {
            if (other.smbez != null)
                return false;
        } else if (!smbez.equals(other.smbez))
            return false;
        return true;
    }


}
