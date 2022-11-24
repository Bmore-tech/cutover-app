package com.gmodelo.cutoverback.structure;

import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.E_Error_SapEntity;

import java.io.Serializable;
import java.util.List;

public class ZIACFM_CLVAL implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6777356979387285503L;

    private List<E_ClassVal_SapEntity> eClassVal_SapEntities;
    private E_Error_SapEntity eError_SapEntities;

    public List<E_ClassVal_SapEntity> geteClassVal_SapEntities() {
        return eClassVal_SapEntities;
    }

    public void seteClassVal_SapEntities(List<E_ClassVal_SapEntity> eClassVal_SapEntities) {
        this.eClassVal_SapEntities = eClassVal_SapEntities;
    }

    public E_Error_SapEntity geteError_SapEntities() {
        return eError_SapEntities;
    }

    public void seteError_SapEntities(E_Error_SapEntity eError_SapEntities) {
        this.eError_SapEntities = eError_SapEntities;
    }

    @Override
    public String toString() {
        return "ZIACFM_CLVAL [eClassVal_SapEntities=" + eClassVal_SapEntities + ", eError_SapEntities="
                + eError_SapEntities + "]";
    }

    public ZIACFM_CLVAL(List<E_ClassVal_SapEntity> eClassVal_SapEntities, E_Error_SapEntity eError_SapEntities) {
        super();
        this.eClassVal_SapEntities = eClassVal_SapEntities;
        this.eError_SapEntities = eError_SapEntities;
    }

    public ZIACFM_CLVAL() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eClassVal_SapEntities == null) ? 0 : eClassVal_SapEntities.hashCode());
        result = prime * result + ((eError_SapEntities == null) ? 0 : eError_SapEntities.hashCode());
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
        ZIACFM_CLVAL other = (ZIACFM_CLVAL) obj;
        if (eClassVal_SapEntities == null) {
            if (other.eClassVal_SapEntities != null)
                return false;
        } else if (!eClassVal_SapEntities.equals(other.eClassVal_SapEntities))
            return false;
        if (eError_SapEntities == null) {
            if (other.eError_SapEntities != null)
                return false;
        } else if (!eError_SapEntities.equals(other.eError_SapEntities))
            return false;
        return true;
    }

}
