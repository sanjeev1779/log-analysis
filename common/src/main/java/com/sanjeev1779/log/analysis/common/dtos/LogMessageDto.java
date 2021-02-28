package com.sanjeev1779.log.analysis.common.dtos;

public class LogMessageDto {
    private String className;
    private long timestamp;
    private String logLevel;
    private Object message;
    private Object[] getArgumentArray;
    private String getFormattedMessage;

    public Object[] getGetArgumentArray() {
        return getArgumentArray;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setGetArgumentArray(Object[] getArgumentArray) {
        this.getArgumentArray = getArgumentArray;
    }

    public String getGetFormattedMessage() {
        return getFormattedMessage;
    }

    public void setGetFormattedMessage(String getFormattedMessage) {
        this.getFormattedMessage = getFormattedMessage;
    }
}
