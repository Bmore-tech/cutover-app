package com.gmodelo.cutoverback.StoredBeans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteStoredBean {

    Map<String, String> lgortMapValue = new HashMap<>();
    Map<String, List<ZoneStoredBean>> zoneByLgort = new HashMap<>();
    Integer iPosRoute;
    Integer iMaxPosRoute;
    Integer iPosZone;
    Integer iMaxPosZone;
    Integer iPosLgpla;


    public Map<String, String> getLgortMapValue() {
        return lgortMapValue;
    }

    public void setLgortMapValue(Map<String, String> lgortMapValue) {
        this.lgortMapValue = lgortMapValue;
    }

    public Map<String, List<ZoneStoredBean>> getZoneByLgort() {
        return zoneByLgort;
    }

    public void setZoneByLgort(Map<String, List<ZoneStoredBean>> zoneByLgort) {
        this.zoneByLgort = zoneByLgort;
    }

    public Integer getiPosRoute() {
        return iPosRoute;
    }

    public void setiPosRoute(Integer iPosRoute) {
        this.iPosRoute = iPosRoute;
    }

    public Integer getiPosZone() {
        return iPosZone;
    }

    public void setiPosZone(Integer iPosZone) {
        this.iPosZone = iPosZone;
    }


    public Integer getiPosLgpla() {
        return iPosLgpla;
    }

    public void setiPosLgpla(Integer iPosLgpla) {
        this.iPosLgpla = iPosLgpla;
    }

    public Integer getiMaxPosRoute() {
        return iMaxPosRoute;
    }

    public void setiMaxPosRoute(Integer iMaxPosRoute) {
        this.iMaxPosRoute = iMaxPosRoute;
    }

    public Integer getiMaxPosZone() {
        return iMaxPosZone;
    }

    public void setiMaxPosZone(Integer iMaxPosZone) {
        this.iMaxPosZone = iMaxPosZone;
    }

    public RouteStoredBean() {
    }

    public RouteStoredBean(Map<String, String> lgortMapValue, Map<String, List<ZoneStoredBean>> zoneByLgort, Integer iPosRoute, Integer iMaxPosRoute, Integer iPosZone, Integer iMaxPosZone, Integer iPosLgpla) {
        this.lgortMapValue = lgortMapValue;
        this.zoneByLgort = zoneByLgort;
        this.iPosRoute = iPosRoute;
        this.iMaxPosRoute = iMaxPosRoute;
        this.iPosZone = iPosZone;
        this.iMaxPosZone = iMaxPosZone;
        this.iPosLgpla = iPosLgpla;
    }

    @Override
    public String toString() {
        return "RouteStoredBean{" +
                "lgortMapValue=" + lgortMapValue +
                ", zoneByLgort=" + zoneByLgort +
                ", iPosRoute=" + iPosRoute +
                ", iMaxPosRoute=" + iMaxPosRoute +
                ", iPosZone=" + iPosZone +
                ", iMaxPosZone=" + iMaxPosZone +
                ", iPosLgpla=" + iPosLgpla +
                '}';
    }
}


