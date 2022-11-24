package com.gmodelo.cutoverback.beans;

import java.util.HashMap;
import java.util.Objects;

public class MapServerBean {

    HashMap<Integer, ServerBean> serverBeanMap;

    public HashMap<Integer, ServerBean> getServerBeanMap() {
        return serverBeanMap;
    }

    public void setServerBeanMap(HashMap<Integer, ServerBean> serverBeanMap) {
        this.serverBeanMap = serverBeanMap;
    }

    public MapServerBean() {
    }

    public MapServerBean(HashMap<Integer, ServerBean> serverBeanMap) {
        this.serverBeanMap = serverBeanMap;
    }

    @Override
    public String toString() {
        return "MapServerBean{" +
                "serverBeanMap=" + serverBeanMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapServerBean that = (MapServerBean) o;
        return Objects.equals(serverBeanMap, that.serverBeanMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverBeanMap);
    }
}
