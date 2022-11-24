package com.gmodelo.cutoverback.CustomObjects;

public class LoginBean {
    String loginId;
    String loginPass;
    String loginLang;
    Integer activeInterval;
    String relationUUID;
    User lsObjectLB;
    private String token;

    public LoginBean(String loginId, String loginPass, String loginLang, Integer activeInterval, String relationUUID,
                     User lsObjectLB) {
        super();
        this.loginId = loginId;
        this.loginPass = loginPass;
        this.loginLang = loginLang;
        this.activeInterval = activeInterval;
        this.relationUUID = relationUUID;
        this.lsObjectLB = lsObjectLB;
    }

    public LoginBean() {
        super();
    }


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getLoginLang() {
        return loginLang;
    }

    public void setLoginLang(String loginLang) {
        this.loginLang = loginLang;
    }

    public Integer getActiveInterval() {
        return activeInterval;
    }

    public void setActiveInterval(Integer activeInterval) {
        this.activeInterval = activeInterval;
    }

    public String getRelationUUID() {
        return relationUUID;
    }

    public void setRelationUUID(String relationUUID) {
        this.relationUUID = relationUUID;
    }

    public User getLsObjectLB() {
        return lsObjectLB;
    }

    public void setLsObjectLB(User lsObjectLB) {
        this.lsObjectLB = lsObjectLB;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginId='" + loginId + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", loginLang='" + loginLang + '\'' +
                ", activeInterval=" + activeInterval +
                ", relationUUID='" + relationUUID + '\'' +
                ", lsObjectLB=" + lsObjectLB +
                ", token='" + token + '\'' +
                '}';
    }
}
