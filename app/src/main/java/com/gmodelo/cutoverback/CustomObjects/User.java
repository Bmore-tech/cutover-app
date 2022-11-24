package com.gmodelo.cutoverback.CustomObjects;

public class User {

    private Entity entity;
    private UserGenInfo genInf;
    private UserAccInfo accInf;

    public User() {
        this.entity = new Entity();
        this.genInf = new UserGenInfo();
        this.accInf = new UserAccInfo();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public UserGenInfo getGenInf() {
        return genInf;
    }

    public void setGenInf(UserGenInfo genInf) {
        this.genInf = genInf;
    }

    public UserAccInfo getAccInf() {
        return accInf;
    }

    public void setAccInf(UserAccInfo accInf) {
        this.accInf = accInf;
    }

    @Override
    public String toString() {
        return "User{" +
                "entity=" + entity +
                ", genInf=" + genInf +
                ", accInf=" + accInf +  '\'' +
                '}';
    }
}
