package com.gmodelo.cutoverback.CustomObjects;


public class Response<K> {

    private AbstractResults abstractResult;
    private K lsObject;

    public Response() {
        super();
    }

    public AbstractResults getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(AbstractResults abstractResult) {
        this.abstractResult = abstractResult;
    }

    public K getLsObject() {
        return lsObject;
    }

    public void setLsObject(K lsObject) {
        this.lsObject = lsObject;
    }

    @Override
    public String toString() {
        return "Response [abstractResult=" + abstractResult + ", lsObject=" + lsObject + "]";
    }


}