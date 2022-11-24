package com.gmodelo.cutoverback.StoredBeans;

import java.util.List;
import java.util.Objects;

public class SpecialSapCountBean {

    Boolean isSpecial;
    List<String> permitedList;

    public Boolean getSpecial() {
        return isSpecial;
    }

    public void setSpecial(Boolean special) {
        isSpecial = special;
    }

    public List<String> getPermitedList() {
        return permitedList;
    }

    public void setPermitedList(List<String> permitedList) {
        this.permitedList = permitedList;
    }

    public SpecialSapCountBean() {
    }

    @Override
    public String toString() {
        return "SpecialSapCountBean{" +
                "isSpecial=" + isSpecial +
                ", permitedList=" + permitedList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialSapCountBean that = (SpecialSapCountBean) o;
        return Objects.equals(isSpecial, that.isSpecial) &&
                Objects.equals(permitedList, that.permitedList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSpecial, permitedList);
    }

    public SpecialSapCountBean(Boolean isSpecial, List<String> permitedList) {
        this.isSpecial = isSpecial;
        this.permitedList = permitedList;
    }
}
