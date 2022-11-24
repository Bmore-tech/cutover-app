package com.gmodelo.cutoverback.DaoBeans;

public class MaterialDescrptionBean {

    String matnr;
    String maktx;

    public MaterialDescrptionBean(String matnr, String maktx) {
        this.matnr = matnr;
        this.maktx = maktx;
    }

    public MaterialDescrptionBean() {
    }

    @Override
    public String toString() {
        return "MaterialDescrptionBean{" +
                "matnr='" + matnr + '\'' +
                ", maktx='" + maktx + '\'' +
                '}';
    }

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
}
