package com.gmodelo.cutoverback.StoredBeans;

import com.gmodelo.cutoverback.CustomObjects.LoginBean;

import java.util.Date;

public class LoginStored {

    LoginBean loginBean;
    Date lastLogin;

    public LoginStored() {
    }

    public LoginStored(LoginBean loginBean, Date lastLogin) {
        this.loginBean = loginBean;
        this.lastLogin = lastLogin;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "LoginStored{" +
                "loginBean=" + loginBean +
                ", lastLogin=" + lastLogin +
                '}';
    }


}
