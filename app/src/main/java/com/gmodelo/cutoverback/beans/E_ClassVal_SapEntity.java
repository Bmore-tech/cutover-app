package com.gmodelo.cutoverback.beans;



import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "SYSVAL", primaryKeys = {"bwtar", "kkref", "krftx"})
public class E_ClassVal_SapEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6277652687541597866L;

    @NotNull
    String bwtar;
    @NotNull
    String kkref;
    @NotNull
    String krftx;

    public String getBwtar() {
        return bwtar;
    }

    public void setBwtar(String bwtar) {
        this.bwtar = bwtar;
    }

    public String getKkref() {
        return kkref;
    }

    public void setKkref(String kkref) {
        this.kkref = kkref;
    }

    public String getKrftx() {
        return krftx;
    }

    public void setKrftx(String krftx) {
        this.krftx = krftx;
    }

    @Override
    public String toString() {
        return "E_ClassVal_SapEntity [bwtar=" + bwtar + ", kkref=" + kkref + ", krftx=" + krftx + "]";
    }

    public E_ClassVal_SapEntity(String bwtar, String kkref, String krftx) {
        super();
        this.bwtar = bwtar;
        this.kkref = kkref;
        this.krftx = krftx;
    }

    public E_ClassVal_SapEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bwtar == null) ? 0 : bwtar.hashCode());
        result = prime * result + ((kkref == null) ? 0 : kkref.hashCode());
        result = prime * result + ((krftx == null) ? 0 : krftx.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        E_ClassVal_SapEntity other = (E_ClassVal_SapEntity) obj;
        if (bwtar == null) {
            if (other.bwtar != null)
                return false;
        } else if (!bwtar.equals(other.bwtar))
            return false;
        if (kkref == null) {
            if (other.kkref != null)
                return false;
        } else if (!kkref.equals(other.kkref))
            return false;
        if (krftx == null) {
            if (other.krftx != null)
                return false;
        } else if (!krftx.equals(other.krftx))
            return false;
        return true;
    }

}
