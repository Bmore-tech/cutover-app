package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

public class ResponseTableMap {

    private AbstractResults abstractResult;
    private String lsObject;

    public ResponseTableMap() {
        super();
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

    @Override
    public String toString() {
        return "Response [abstractResult=" + abstractResult + ", lsObject=" + lsObject + "]";
    }
}
