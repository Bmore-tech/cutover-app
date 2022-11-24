package com.gmodelo.cutoverback.beans;

public class StoredRouteValue {
    Boolean hasRoute;
    String jsonRoute;

    public Boolean getHasRoute() {
        return hasRoute;
    }

    public void setHasRoute(Boolean hasRoute) {
        this.hasRoute = hasRoute;
    }

    public String getJsonRoute() {
        return jsonRoute;
    }

    public void setJsonRoute(String jsonRoute) {
        this.jsonRoute = jsonRoute;
    }

    public StoredRouteValue() {
    }

    public StoredRouteValue(Boolean hasRoute, String jsonRoute) {
        this.hasRoute = hasRoute;
        this.jsonRoute = jsonRoute;
    }

    @Override
    public String toString() {
        return "StoredRouteValue{" +
                "hasRoute=" + hasRoute +
                ", jsonRoute='" + jsonRoute + '\'' +
                '}';
    }

    
}
