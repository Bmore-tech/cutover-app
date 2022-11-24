package com.gmodelo.cutoverback.CustomObjects;

import java.util.Date;

public class LoginEntity {
    LoginBean loginBean;
    Date lastLogin;

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

    public LoginEntity() {
        super();
    }

    public LoginEntity(LoginBean loginBean, Date lastLogin) {
        this.loginBean = loginBean;
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "loginBean=" + loginBean +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
