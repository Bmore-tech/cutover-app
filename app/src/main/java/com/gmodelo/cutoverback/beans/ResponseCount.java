package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

import java.util.HashMap;
import java.util.Objects;

public class ResponseCount {


    private AbstractResults abstractResult;
    private HashMap<String, Integer> lsObject;

    public AbstractResults getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(AbstractResults abstractResult) {
        this.abstractResult = abstractResult;
    }

    public HashMap<String, Integer> getLsObject() {
        return lsObject;
    }

    public void setLsObject(HashMap<String, Integer> lsObject) {
        this.lsObject = lsObject;
    }

    public ResponseCount() {
    }

    public ResponseCount(AbstractResults abstractResult, HashMap<String, Integer> lsObject) {
        this.abstractResult = abstractResult;
        this.lsObject = lsObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseCount that = (ResponseCount) o;
        return Objects.equals(abstractResult, that.abstractResult) &&
                Objects.equals(lsObject, that.lsObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abstractResult, lsObject);
    }

    @Override
    public String toString() {
        return "ResponseCount{" +
                "abstractResult=" + abstractResult +
                ", lsObject=" + lsObject +
                '}';
    }
}
