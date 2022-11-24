package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

public class ResponseMobileData {

    private AbstractResults abstractResult;
    private String lsObject;

    public ResponseMobileData() {
    }

    public ResponseMobileData(AbstractResults abstractResult, String lsObject) {
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

    public String getLsObject() {
        return lsObject;
    }

    public void setLsObject(String lsObject) {
        this.lsObject = lsObject;
    }
}
