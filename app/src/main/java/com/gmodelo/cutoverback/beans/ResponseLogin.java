package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.LoginBean;

public class ResponseLogin {

    private AbstractResults abstractResult;
    private LoginBean lsObject;

    public ResponseLogin() {
        super();
    }

    public AbstractResults getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(AbstractResults abstractResult) {
        this.abstractResult = abstractResult;
    }

    public LoginBean getLsObject() {
        return lsObject;
    }

    public void setLsObject(LoginBean lsObject) {
        this.lsObject = lsObject;
    }

    @Override
    public String toString() {
        return "Response [abstractResult=" + abstractResult + ", lsObject=" + lsObject + "]";
    }

}
