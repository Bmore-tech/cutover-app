package com.gmodelo.cutoverback.beans;

import com.gmodelo.cutoverback.CustomObjects.AbstractResults;

public class AsyncDbProg {

    DownloadDataBean downloadDataBean;
    AbstractResults results;
    Integer setTo;
    String message;

    public DownloadDataBean getDownloadDataBean() {
        return downloadDataBean;
    }

    public void setDownloadDataBean(DownloadDataBean downloadDataBean) {
        this.downloadDataBean = downloadDataBean;
    }

    public AbstractResults getResults() {
        return results;
    }

    public void setResults(AbstractResults results) {
        this.results = results;
    }

    public Integer getSetTo() {
        return setTo;
    }

    public void setSetTo(Integer setTo) {
        this.setTo = setTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AsyncDbProg{" +
                "downloadDataBean=" + downloadDataBean +
                ", results=" + results +
                ", setTo=" + setTo +
                ", message='" + message + '\'' +
                '}';
    }

    public AsyncDbProg() {
    }

    public AsyncDbProg(DownloadDataBean downloadDataBean, AbstractResults results, Integer setTo, String message) {
        this.downloadDataBean = downloadDataBean;
        this.results = results;
        this.setTo = setTo;
        this.message = message;
    }
}
