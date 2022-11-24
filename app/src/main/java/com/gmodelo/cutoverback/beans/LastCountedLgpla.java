package com.gmodelo.cutoverback.beans;

import java.util.LinkedHashMap;

public class LastCountedLgpla {

    LinkedHashMap<String, LgplaValuesBean> lastCounted;

    public LinkedHashMap<String, LgplaValuesBean> getLastCounted() {
        return lastCounted;
    }

    public void setLastCounted(LinkedHashMap<String, LgplaValuesBean> lastCounted) {
        this.lastCounted = lastCounted;
    }

    public LastCountedLgpla() {
    }

    public LastCountedLgpla(LinkedHashMap<String, LgplaValuesBean> lastCounted) {
        this.lastCounted = lastCounted;
    }

    @Override
    public String toString() {
        return "LastCountedLgpla{" +
                "lastCounted=" + lastCounted +
                '}';
    }
}
