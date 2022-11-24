package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.structure.ZIACFM_CLVAL;
import com.gmodelo.cutoverback.structure.ZIACMF_I360_EXT_SIS_CLAS;

import java.io.Serializable;
import java.util.List;

public class DownloadDataBean implements Serializable {

    private static final long serialVersionUID = 4032005834320492589L;
    private List<MaterialTarimasBean> listMaterialTarimas;
    private List<MobileMaterialBean> listMobileMaterial;
    private ZIACMF_I360_EXT_SIS_CLAS ziacmf_I360_EXT_SIS_CLAS;
    private ZIACFM_CLVAL ziacfm_CLVAL;

    public List<MaterialTarimasBean> getListMaterialTarimas() {
        return listMaterialTarimas;
    }

    public void setListMaterialTarimas(List<MaterialTarimasBean> listMaterialTarimas) {
        this.listMaterialTarimas = listMaterialTarimas;
    }

    public List<MobileMaterialBean> getListMobileMaterial() {
        return listMobileMaterial;
    }

    public void setListMobileMaterial(List<MobileMaterialBean> listMobileMaterial) {
        this.listMobileMaterial = listMobileMaterial;
    }

    public ZIACMF_I360_EXT_SIS_CLAS getZiacmf_I360_EXT_SIS_CLAS() {
        return ziacmf_I360_EXT_SIS_CLAS;
    }

    public void setZiacmf_I360_EXT_SIS_CLAS(ZIACMF_I360_EXT_SIS_CLAS ziacmf_I360_EXT_SIS_CLAS) {
        this.ziacmf_I360_EXT_SIS_CLAS = ziacmf_I360_EXT_SIS_CLAS;
    }

    public ZIACFM_CLVAL getZiacfm_CLVAL() {
        return ziacfm_CLVAL;
    }

    public void setZiacfm_CLVAL(ZIACFM_CLVAL ziacfm_CLVAL) {
        this.ziacfm_CLVAL = ziacfm_CLVAL;
    }

    public DownloadDataBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "DownloadDataBean [listMaterialTarimas=" + listMaterialTarimas + ", listMobileMaterial="
                + listMobileMaterial + ", ziacmf_I360_EXT_SIS_CLAS=" + ziacmf_I360_EXT_SIS_CLAS + ", ziacfm_CLVAL="
                + ziacfm_CLVAL + "]";
    }

    public DownloadDataBean(List<MaterialTarimasBean> listMaterialTarimas, List<MobileMaterialBean> listMobileMaterial,
                            ZIACMF_I360_EXT_SIS_CLAS ziacmf_I360_EXT_SIS_CLAS, ZIACFM_CLVAL ziacfm_CLVAL) {
        super();
        this.listMaterialTarimas = listMaterialTarimas;
        this.listMobileMaterial = listMobileMaterial;
        this.ziacmf_I360_EXT_SIS_CLAS = ziacmf_I360_EXT_SIS_CLAS;
        this.ziacfm_CLVAL = ziacfm_CLVAL;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((listMaterialTarimas == null) ? 0 : listMaterialTarimas.hashCode());
        result = prime * result + ((listMobileMaterial == null) ? 0 : listMobileMaterial.hashCode());
        result = prime * result + ((ziacfm_CLVAL == null) ? 0 : ziacfm_CLVAL.hashCode());
        result = prime * result + ((ziacmf_I360_EXT_SIS_CLAS == null) ? 0 : ziacmf_I360_EXT_SIS_CLAS.hashCode());
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
        DownloadDataBean other = (DownloadDataBean) obj;
        if (listMaterialTarimas == null) {
            if (other.listMaterialTarimas != null)
                return false;
        } else if (!listMaterialTarimas.equals(other.listMaterialTarimas))
            return false;
        if (listMobileMaterial == null) {
            if (other.listMobileMaterial != null)
                return false;
        } else if (!listMobileMaterial.equals(other.listMobileMaterial))
            return false;
        if (ziacfm_CLVAL == null) {
            if (other.ziacfm_CLVAL != null)
                return false;
        } else if (!ziacfm_CLVAL.equals(other.ziacfm_CLVAL))
            return false;
        if (ziacmf_I360_EXT_SIS_CLAS == null) {
            if (other.ziacmf_I360_EXT_SIS_CLAS != null)
                return false;
        } else if (!ziacmf_I360_EXT_SIS_CLAS.equals(other.ziacmf_I360_EXT_SIS_CLAS))
            return false;
        return true;
    }
}
