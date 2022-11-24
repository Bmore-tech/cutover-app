package com.gmodelo.cutoverback.beans;

import java.util.Objects;

public class ServerBean {

    String url;
    String port;
    String commonName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public ServerBean() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerBean that = (ServerBean) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(port, that.port) &&
                Objects.equals(commonName, that.commonName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, port, commonName);
    }

    @Override
    public String toString() {
        return "ServerBean{" +
                "url='" + url + '\'' +
                ", port='" + port + '\'' +
                ", commonName='" + commonName + '\'' +
                '}';
    }

    public ServerBean(String url, String port, String commonName) {
        this.url = url;
        this.port = port;
        this.commonName = commonName;
    }

    public String baseServer() {
        return this.url + ":" + this.port;
    }


}
