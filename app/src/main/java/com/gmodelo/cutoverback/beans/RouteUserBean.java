package com.gmodelo.cutoverback.beans;

import com.google.gson.Gson;

import java.util.List;

public class RouteUserBean {
    String routeId;
    String bukrs;
    String werks;
    String taskId;
    String rdesc;
    String type;
    String bdesc;
    String wdesc;
    Boolean reconteo;
    Boolean sapSpecial;
    long dateIni;
    long dateEnd;

    List<RouteUserPositionBean> positions;


    public RouteUserBean() {
        super();
        this.reconteo = false;
        this.sapSpecial = false;
        // TODO Auto-generated constructor stub
    }

    public RouteUserBean(String routeId, String bukrs, String werks, String taskId, String rdesc, String type, String bdesc, String wdesc, Boolean reconteo, Boolean sapSpecial, long dateIni, long dateEnd, List<RouteUserPositionBean> positions) {
        this.routeId = routeId;
        this.bukrs = bukrs;
        this.werks = werks;
        this.taskId = taskId;
        this.rdesc = rdesc;
        this.type = type;
        this.bdesc = bdesc;
        this.wdesc = wdesc;
        this.reconteo = reconteo;
        this.sapSpecial = sapSpecial;
        this.dateIni = dateIni;
        this.dateEnd = dateEnd;
        this.positions = positions;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getDateIni() {
        return dateIni;
    }

    public void setDateIni(long dateIni) {
        this.dateIni = dateIni;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public String getWdesc() {
        return wdesc;
    }

    public void setWdesc(String wdesc) {
        this.wdesc = wdesc;
    }

    public List<RouteUserPositionBean> getPositions() {
        return positions;
    }

    public void setPositions(List<RouteUserPositionBean> positions) {
        this.positions = positions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getRdesc() {
        return rdesc;
    }

    public void setRdesc(String rdesc) {
        this.rdesc = rdesc;
    }

    public Boolean getReconteo() {
        return reconteo;
    }

    public void setReconteo(Boolean reconteo) {
        this.reconteo = reconteo;
    }

    public Boolean getSapSpecial() {
        return sapSpecial;
    }

    public void setSapSpecial(Boolean sapSpecial) {
        this.sapSpecial = sapSpecial;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

