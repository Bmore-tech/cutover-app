package com.gmodelo.cutoverback.beans;

public class MobileDataFetch {
    Long lastMobileDataFetch;

    @Override
    public String toString() {
        return "MobileDataFetch{" +
                "lastMobileDataFetch=" + lastMobileDataFetch +
                '}';
    }

    public MobileDataFetch() {
    }

    public MobileDataFetch(Long lastMobileDataFetch) {
        this.lastMobileDataFetch = lastMobileDataFetch;
    }

    public Long getLastMobileDataFetch() {
        return lastMobileDataFetch;
    }

    public void setLastMobileDataFetch(Long lastMobileDataFetch) {
        this.lastMobileDataFetch = lastMobileDataFetch;
    }
}

