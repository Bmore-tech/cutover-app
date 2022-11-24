package com.gmodelo.cutoverback.StoredBeans;

import java.util.HashMap;
import java.util.Objects;

public class CountBean {

    HashMap<String, Integer> countMap;
    String valueOf;

    public HashMap<String, Integer> getCountMap() {
        return countMap;
    }

    public void setCountMap(HashMap<String, Integer> countMap) {
        this.countMap = countMap;
    }

    public String getValueOf() {
        return valueOf;
    }

    public void setValueOf(String valueOf) {
        this.valueOf = valueOf;
    }

    public CountBean() {
    }

    @Override
    public String toString() {
        return "CountBean{" +
                "countMap=" + countMap +
                ", valueOf='" + valueOf + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountBean countBean = (CountBean) o;
        return Objects.equals(countMap, countBean.countMap) &&
                Objects.equals(valueOf, countBean.valueOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countMap, valueOf);
    }
}
