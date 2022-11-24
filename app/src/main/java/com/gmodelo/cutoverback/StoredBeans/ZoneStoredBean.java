package com.gmodelo.cutoverback.StoredBeans;

import java.util.Objects;

public class ZoneStoredBean {
    String zoneID;
    String zoneD;
    Integer currentPosition;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneStoredBean that = (ZoneStoredBean) o;
        return Objects.equals(zoneID, that.zoneID) &&
                Objects.equals(zoneD, that.zoneD) &&
                Objects.equals(currentPosition, that.currentPosition);
    }

    @Override
    public int hashCode() {

        return Objects.hash(zoneID, zoneD, currentPosition);
    }

    @Override
    public String toString() {
        return "ZoneStoredBean{" +
                "zoneID='" + zoneID + '\'' +
                ", zoneD='" + zoneD + '\'' +
                ", currentPosition=" + currentPosition +
                '}';
    }

    public ZoneStoredBean(String zoneID, String zoneD, Integer currentPosition) {
        this.zoneID = zoneID;
        this.zoneD = zoneD;
        this.currentPosition = currentPosition;
    }

    public ZoneStoredBean() {
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneD() {
        return zoneD;
    }

    public void setZoneD(String zoneD) {
        this.zoneD = zoneD;
    }

    public Integer getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }
}
