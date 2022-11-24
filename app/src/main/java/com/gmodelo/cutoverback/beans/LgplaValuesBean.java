package com.gmodelo.cutoverback.beans;

import com.google.gson.Gson;

public class LgplaValuesBean {


    private String matnr;
    private String vhilm;
    private String matkx;
    private String sec;
    private String tarimas;
    private String camas;
    private String um;// Cantidad de Unidad de medida osea caja
    private String totalConverted;
    private String materialNotes;
    private String prodDate;
    private String vhilmQuan;
    private String cpc;
    private String cpp;
    private String cval;
    private boolean locked;
    private long dateStart;
    private long dateEnd;
    private String lote;
    private String estatusPt;

    public LgplaValuesBean() {
        super();
    }

    public LgplaValuesBean(String matnr, String vhilm, String matkx, String sec, String tarimas, String camas,
                           String um, String totalConverted, String materialNotes, String prodDate, String vhilmQuan, String cpc,
                           String cpp, String cval, boolean locked, long dateStart, long dateEnd) {
        super();
        this.matnr = matnr;
        this.vhilm = vhilm;
        this.matkx = matkx;
        this.sec = sec;
        this.tarimas = tarimas;
        this.camas = camas;
        this.um = um;
        this.totalConverted = totalConverted;
        this.materialNotes = materialNotes;
        this.prodDate = prodDate;
        this.vhilmQuan = vhilmQuan;
        this.cpc = cpc;
        this.cpp = cpp;
        this.cval = cval;
        this.locked = locked;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getVhilm() {
        return vhilm;
    }

    public void setVhilm(String vhilm) {
        this.vhilm = vhilm;
    }

    public String getMatkx() {
        return matkx;
    }

    public void setMatkx(String matkx) {
        this.matkx = matkx;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getTarimas() {
        return tarimas;
    }

    public void setTarimas(String tarimas) {
        this.tarimas = tarimas;
    }

    public String getCamas() {
        return camas;
    }

    public void setCamas(String camas) {
        this.camas = camas;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public String getTotalConverted() {
        return totalConverted;
    }

    public void setTotalConverted(String totalConverted) {
        this.totalConverted = totalConverted;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String toKey(String pkAsgId) {
        return pkAsgId + this.matnr;
    }

    public String getMaterialNotes() {
        return materialNotes;
    }

    public void setMaterialNotes(String materialNotes) {
        this.materialNotes = materialNotes;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getVhilmQuan() {
        return vhilmQuan;
    }

    public void setVhilmQuan(String vhilmQuan) {
        this.vhilmQuan = vhilmQuan;
    }

    public String getCpc() {
        return cpc;
    }

    public void setCpc(String cpc) {
        this.cpc = cpc;
    }

    public String getCpp() {
        return cpp;
    }

    public void setCpp(String cpp) {
        this.cpp = cpp;
    }

    public String getCval() {
        return cval;
    }

    public void setCval(String cval) {
        this.cval = cval;
    }

    public String getEstatusPt() {
        return estatusPt;
    }

    public void setEstatusPt(String estatusPt) {
        this.estatusPt = estatusPt;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((camas == null) ? 0 : camas.hashCode());
        result = prime * result + ((cpc == null) ? 0 : cpc.hashCode());
        result = prime * result + ((cpp == null) ? 0 : cpp.hashCode());
        result = prime * result + ((cval == null) ? 0 : cval.hashCode());
        result = prime * result + (int) (dateEnd ^ (dateEnd >>> 32));
        result = prime * result + (int) (dateStart ^ (dateStart >>> 32));
        result = prime * result + (locked ? 1231 : 1237);
        result = prime * result + ((materialNotes == null) ? 0 : materialNotes.hashCode());
        result = prime * result + ((matkx == null) ? 0 : matkx.hashCode());
        result = prime * result + ((matnr == null) ? 0 : matnr.hashCode());
        result = prime * result + ((prodDate == null) ? 0 : prodDate.hashCode());
        result = prime * result + ((sec == null) ? 0 : sec.hashCode());
        result = prime * result + ((tarimas == null) ? 0 : tarimas.hashCode());
        result = prime * result + ((totalConverted == null) ? 0 : totalConverted.hashCode());
        result = prime * result + ((um == null) ? 0 : um.hashCode());
        result = prime * result + ((vhilm == null) ? 0 : vhilm.hashCode());
        result = prime * result + ((vhilmQuan == null) ? 0 : vhilmQuan.hashCode());
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
        LgplaValuesBean other = (LgplaValuesBean) obj;
        if (camas == null) {
            if (other.camas != null)
                return false;
        } else if (!camas.equals(other.camas))
            return false;
        if (cpc == null) {
            if (other.cpc != null)
                return false;
        } else if (!cpc.equals(other.cpc))
            return false;
        if (cpp == null) {
            if (other.cpp != null)
                return false;
        } else if (!cpp.equals(other.cpp))
            return false;
        if (cval == null) {
            if (other.cval != null)
                return false;
        } else if (!cval.equals(other.cval))
            return false;
        if (dateEnd != other.dateEnd)
            return false;
        if (dateStart != other.dateStart)
            return false;
        if (locked != other.locked)
            return false;
        if (materialNotes == null) {
            if (other.materialNotes != null)
                return false;
        } else if (!materialNotes.equals(other.materialNotes))
            return false;
        if (matkx == null) {
            if (other.matkx != null)
                return false;
        } else if (!matkx.equals(other.matkx))
            return false;
        if (matnr == null) {
            if (other.matnr != null)
                return false;
        } else if (!matnr.equals(other.matnr))
            return false;
        if (prodDate == null) {
            if (other.prodDate != null)
                return false;
        } else if (!prodDate.equals(other.prodDate))
            return false;
        if (sec == null) {
            if (other.sec != null)
                return false;
        } else if (!sec.equals(other.sec))
            return false;
        if (tarimas == null) {
            if (other.tarimas != null)
                return false;
        } else if (!tarimas.equals(other.tarimas))
            return false;
        if (totalConverted == null) {
            if (other.totalConverted != null)
                return false;
        } else if (!totalConverted.equals(other.totalConverted))
            return false;
        if (um == null) {
            if (other.um != null)
                return false;
        } else if (!um.equals(other.um))
            return false;
        if (vhilm == null) {
            if (other.vhilm != null)
                return false;
        } else if (!vhilm.equals(other.vhilm))
            return false;
        if (vhilmQuan == null) {
            return other.vhilmQuan == null;
        } else return vhilmQuan.equals(other.vhilmQuan);
    }

}
