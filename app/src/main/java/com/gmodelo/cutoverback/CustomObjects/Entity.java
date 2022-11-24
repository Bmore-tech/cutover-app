package com.gmodelo.cutoverback.CustomObjects;

public class Entity {

    private String identyId;
    private String idType;
    private String dataSource;
    private String description;
    private String createdBy;

    public String getIdentyId() {
        return identyId;
    }

    public void setIdentyId(String identyId) {
        this.identyId = identyId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Entity [identyId=" + identyId + ", idType=" + idType + ", dataSource=" + dataSource + ", description="
                + description + ", createdBy=" + createdBy + "]";
    }


}