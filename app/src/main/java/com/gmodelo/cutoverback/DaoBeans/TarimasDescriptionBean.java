package com.gmodelo.cutoverback.DaoBeans;

public class TarimasDescriptionBean {

    String vhilm;
    String maktx;

    public String getVhilm() {
        return vhilm;
    }

    public void setVhilm(String vhilm) {
        this.vhilm = vhilm;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public TarimasDescriptionBean() {
    }

    public TarimasDescriptionBean(String vhilm, String maktx) {
        this.vhilm = vhilm;
        this.maktx = maktx;
    }

    @Override
    public String toString() {
        return "TarimasDescriptionBean{" +
                "vhilm='" + vhilm + '\'' +
                ", maktx='" + maktx + '\'' +
                '}';
    }
}
