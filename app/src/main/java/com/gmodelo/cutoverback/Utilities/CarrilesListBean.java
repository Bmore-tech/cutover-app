package com.gmodelo.cutoverback.Utilities;

public class CarrilesListBean {


    String lgpla;
    String lgplaDesc;
    Integer leftIcon;
    Integer lgplaPosition;

    public String getLgpla() {
        return lgpla;
    }

    public void setLgpla(String lgpla) {
        this.lgpla = lgpla;
    }

    public String getLgplaDesc() {
        return lgplaDesc;
    }

    public void setLgplaDesc(String lgplaDesc) {
        this.lgplaDesc = lgplaDesc;
    }

    public Integer getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(Integer leftIcon) {
        this.leftIcon = leftIcon;
    }

    public Integer getLgplaPosition() {
        return lgplaPosition;
    }

    public void setLgplaPosition(Integer lgplaPosition) {
        this.lgplaPosition = lgplaPosition;
    }

    public CarrilesListBean() {
    }

    public CarrilesListBean(String lgpla, String lgplaDesc, Integer leftIcon) {
        this.lgpla = lgpla;
        this.lgplaDesc = lgplaDesc;
        this.leftIcon = leftIcon;
    }

    @Override
    public String toString() {
        return "CarrilesListBean{" +
                "lgpla='" + lgpla + '\'' +
                ", lgplaDesc='" + lgplaDesc + '\'' +
                ", leftIcon=" + leftIcon +
                ", lgplaPosition=" + lgplaPosition +
                '}';
    }
}
