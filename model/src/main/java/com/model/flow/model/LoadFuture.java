package com.model.flow.model;

public class LoadFuture {
    private String flowFileName;
    private boolean isOk;
    private Exception exception;

    public LoadFuture(String flowFileName, boolean isOk, Exception exception) {
        this.flowFileName = flowFileName;
        this.isOk = isOk;
        this.exception = exception;
    }

    public String getFlowFileName() {
        return flowFileName;
    }

    public void setFlowFileName(String flowFileName) {
        this.flowFileName = flowFileName;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
