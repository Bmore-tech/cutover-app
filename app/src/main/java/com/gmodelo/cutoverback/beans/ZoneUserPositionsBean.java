package com.gmodelo.cutoverback.beans;

import com.google.gson.Gson;

import java.util.LinkedHashMap;

public class ZoneUserPositionsBean {
    int pkAsgId;
    String zoneId;
    String lgtyp;
    String lgpla;
    String secuency;
    String imwm;
    boolean isDone;
    int secuencia;

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    LinkedHashMap<String, LgplaValuesBean> lgplaValues;

    public ZoneUserPositionsBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getPkAsgId() {
        return pkAsgId;
    }

    public void setPkAsgId(int pkAsgId) {
        this.pkAsgId = pkAsgId;
    }

    public LinkedHashMap<String, LgplaValuesBean> getLgplaValues() {
        return lgplaValues;
    }

    public void setLgplaValues(LinkedHashMap<String, LgplaValuesBean> lgplaValues) {
        this.lgplaValues = lgplaValues;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLgtyp() {
        return lgtyp;
    }

    public void setLgtyp(String lgtyp) {
        this.lgtyp = lgtyp;
    }

    public String getLgpla() {
        return lgpla;
    }

    public void setLgpla(String lgpla) {
        this.lgpla = lgpla;
    }

    public String getSecuency() {
        return secuency;
    }

    public void setSecuency(String secuency) {
        this.secuency = secuency;
    }

    public String getImwm() {
        return imwm;
    }

    public void setImwm(String imwm) {
        this.imwm = imwm;
    }

    public Boolean getIdDone() {
        return isDone;
    }

    public void setIdDone(Boolean idDone) {
        this.isDone = idDone;
    }

    public ZoneUserPositionsBean(int pkAsgId, String zoneId, String lgtyp, String lgpla, String secuency, String imwm,
                                 LinkedHashMap<String, LgplaValuesBean> lgplaValues) {
        super();
        this.pkAsgId = pkAsgId;
        this.zoneId = zoneId;
        this.lgtyp = lgtyp;
        this.lgpla = lgpla;
        this.secuency = secuency;
        this.imwm = imwm;
        this.lgplaValues = lgplaValues;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
