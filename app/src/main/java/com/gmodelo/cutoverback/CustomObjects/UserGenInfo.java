package com.gmodelo.cutoverback.CustomObjects;

public class UserGenInfo {

    private String name;
    private String lastName;
    private String email;
    private String language;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    @Override
    public String toString() {
        return "UserGenInfo [name=" + name + ", lastName=" + lastName + ", email=" + email + ", language=" + language
                + "]";
    }

}