package com.gmodelo.cutoverback.beans;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "MOBMAT", primaryKeys = {"matnr", "maktx", "meins", "meinh", "umren", "umren"})
public class MobileMaterialBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5684316925363247131L;
    @NonNull
    String matnr;
    @NonNull
    String maktx;
    @NonNull
    String meins;
    @NonNull
    String meinh;
    @NonNull
    String umrez;
    @NonNull
    String umren;
    @NonNull
    String eannr;
    String ean11;
    String zeinr;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getMeinh() {
        return meinh;
    }

    public void setMeinh(String meinh) {
        this.meinh = meinh;
    }

    public String getUmrez() {
        return umrez;
    }

    public void setUmrez(String umrez) {
        this.umrez = umrez;
    }

    public String getUmren() {
        return umren;
    }

    public void setUmren(String umren) {
        this.umren = umren;
    }

    public String getEannr() {
        return eannr;
    }

    public void setEannr(String eannr) {
        this.eannr = eannr;
    }

    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
    }

    public String getZeinr() {
        return zeinr;
    }

    public void setZeinr(String zeinr) {
        this.zeinr = zeinr;
    }

    public MobileMaterialBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MobileMaterialBean(@NonNull String matnr, @NonNull String maktx, @NonNull String meins, @NonNull String meinh, @NonNull String umrez, @NonNull String umren, @NonNull String eannr, String ean11, String zeinr) {
        this.matnr = matnr;
        this.maktx = maktx;
        this.meins = meins;
        this.meinh = meinh;
        this.umrez = umrez;
        this.umren = umren;
        this.eannr = eannr;
        this.ean11 = ean11;
        this.zeinr = zeinr;
    }

    @Override
    public String toString() {
        return "MobileMaterialBean [matnr=" + matnr + ", maktx=" + maktx + ", meins=" + meins + ", meinh=" + meinh
                + ", umrez=" + umrez + ", umren=" + umren + ", eannr=" + eannr + ", ean11=" + ean11 + ", zeinr=" + zeinr
                + "]";
    }

}
