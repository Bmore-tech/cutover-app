package com.gmodelo.cutoverback.Utilities;

import com.balsikandar.crashreporter.CrashReporter;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

public class LogginAPI {

    Exception e;
    Long timeStamp;
    String shortText;
    String user;
    String exceptionS;


    public LogginAPI(@NotNull Exception e, @NotNull Long timeStamp, @NotNull String user) {
        this.e = e;
        this.timeStamp = timeStamp;
        this.user = user;
        this.exceptionS = stackTraceToString(e);
        this.shortText = timeStamp + " - " + e.getMessage();
    }

    protected String stackTraceToString(Exception e) {
        StringWriter errors = new StringWriter();
        try {
            errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
        } catch (Exception ex) {
            CrashReporter.logException(e);
            CrashReporter.logException(ex);
        }
        return errors.toString();
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getExceptionS() {
        return exceptionS;
    }

    public void setExceptionS(String exceptionS) {
        this.exceptionS = exceptionS;
    }

    @Override
    public String toString() {
        return "LogginAPI{" +
                "e=" + e +
                ", timeStamp=" + timeStamp +
                ", shortText='" + shortText + '\'' +
                ", user='" + user + '\'' +
                ", exceptionS='" + exceptionS + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogginAPI logginAPI = (LogginAPI) o;
        return Objects.equals(e, logginAPI.e) &&
                Objects.equals(timeStamp, logginAPI.timeStamp) &&
                Objects.equals(shortText, logginAPI.shortText) &&
                Objects.equals(user, logginAPI.user) &&
                Objects.equals(exceptionS, logginAPI.exceptionS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e, timeStamp, shortText, user, exceptionS);
    }
}
