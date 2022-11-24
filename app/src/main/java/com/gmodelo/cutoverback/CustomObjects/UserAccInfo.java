package com.gmodelo.cutoverback.CustomObjects;

import java.util.Date;

public class UserAccInfo {

    private String password;
    private byte lockAcc;
    private byte lockPass;
    private byte passChangReq;
    private Date lastPassChange;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public byte getLockAcc() {
        return lockAcc;
    }
    public void setLockAcc(byte lockAcc) {
        this.lockAcc = lockAcc;
    }
    public byte getLockPass() {
        return lockPass;
    }
    public void setLockPass(byte lockPass) {
        this.lockPass = lockPass;
    }
    public byte getPassChangReq() {
        return passChangReq;
    }
    public void setPassChangReq(byte passChangReq) {
        this.passChangReq = passChangReq;
    }
    public Date getLastPassChange() {
        return lastPassChange;
    }
    public void setLastPassChange(Date lastPassChange) {
        this.lastPassChange = lastPassChange;
    }
    @Override
    public String toString() {
        return "UserAccInfo [password=" + password + ", lockAcc=" + lockAcc + ", lockPass=" + lockPass
                + ", passChangReq=" + passChangReq + ", lastPassChange=" + lastPassChange + "]";
    }

}