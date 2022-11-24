package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

public class ResponseRoute {
    private AbstractResults abstractResult;
    private RouteUserBean lsObject;

    public AbstractResults getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(AbstractResults abstractResult) {
        this.abstractResult = abstractResult;
    }

    public RouteUserBean getLsObject() {
        return lsObject;
    }

    public void setLsObject(RouteUserBean lsObject) {
        this.lsObject = lsObject;
    }

    public ResponseRoute() {
    }

    public ResponseRoute(AbstractResults abstractResult, RouteUserBean lsObject) {
        this.abstractResult = abstractResult;
        this.lsObject = lsObject;
    }

    @Override
    public String toString() {
        return "ResponseRoute{" +
                "abstractResult=" + abstractResult +
                ", lsObject=" + lsObject +
                '}';
    }
}
