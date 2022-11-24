package com.gmodelo.cutoverback.Utilities;

import android.view.View;

public class MaterialListBean {

    String matnr;
    String matnr_ext;
    String matkx;
    String colorValue;
    String storedValue;
    String storedKey;
    int rightIcon;
    View.OnClickListener rightListener;
    int leftIcon;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMatnr_ext() {
        return matnr_ext;
    }

    public void setMatnr_ext(String matnr_ext) {
        this.matnr_ext = matnr_ext;
    }

    public String getMatkx() {
        return matkx;
    }

    public void setMatkx(String matkx) {
        this.matkx = matkx;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    public int getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(int rightIcon) {
        this.rightIcon = rightIcon;
    }

    public int getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        this.leftIcon = leftIcon;
    }

    public String getStoredValue() {
        return storedValue;
    }

    public void setStoredValue(String storedValue) {
        this.storedValue = storedValue;
    }

    public String getStoredKey() {
        return storedKey;
    }

    public void setStoredKey(String storedKey) {
        this.storedKey = storedKey;
    }

    public View.OnClickListener getRightListener() {
        return rightListener;
    }

    public void setRightListener(View.OnClickListener rightListener) {
        this.rightListener = rightListener;
    }

    @Override
    public String toString() {
        return "MaterialListBean{" +
                "matnr='" + matnr + '\'' +
                ", matnr_ext='" + matnr_ext + '\'' +
                ", matkx='" + matkx + '\'' +
                ", colorValue='" + colorValue + '\'' +
                ", storedValue='" + storedValue + '\'' +
                ", storedKey='" + storedKey + '\'' +
                ", rightIcon=" + rightIcon +
                ", leftIcon=" + leftIcon +
                '}';
    }


    public MaterialListBean(String matnr, String matnr_ext, String matkx, String colorValue, String storedValue, String storedKey, int rightIcon, int leftIcon) {
        this.matnr = matnr;
        this.matnr_ext = matnr_ext;
        this.matkx = matkx;
        this.colorValue = colorValue;
        this.storedValue = storedValue;
        this.storedKey = storedKey;
        this.rightIcon = rightIcon;
        this.leftIcon = leftIcon;
    }

    public MaterialListBean() {
        super();
        // TODO Auto-generated constructor stub
    }

}
