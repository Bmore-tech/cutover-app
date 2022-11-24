package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

public class ResponseAllData {

    private AbstractResults abstractResult;
    private DownloadDataBean lsObject;

    public ResponseAllData() {
    }

    public ResponseAllData(AbstractResults abstractResult, DownloadDataBean lsObject) {
        this.abstractResult = abstractResult;
        this.lsObject = lsObject;
    }

    @Override
    public String toString() {
        return "ResponseMobileData{" +
                "abstractResult=" + abstractResult +
                ", lsObject=" + lsObject +
                '}';
    }

    public AbstractResults getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(AbstractResults abstractResult) {
        this.abstractResult = abstractResult;
    }

    public DownloadDataBean getLsObject() {
        return lsObject;
    }

    public void setLsObject(DownloadDataBean lsObject) {
        this.lsObject = lsObject;
    }
}
