package com.gmodelo.cutoverback.Utilities;

import java.util.HashMap;
import java.util.Objects;

public class LogginMap {

    HashMap<String, LogginAPI> logMap;

    public HashMap<String, LogginAPI> getLogMap() {
        return logMap;
    }

    public void setLogMap(HashMap<String, LogginAPI> logMap) {
        this.logMap = logMap;
    }

    public LogginMap() {
        this.logMap =  new HashMap<>();
    }

    @Override
    public String toString() {
        return "LogginMap{" +
                "logMap=" + logMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogginMap logginMap = (LogginMap) o;
        return Objects.equals(logMap, logginMap.logMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logMap);
    }
}
