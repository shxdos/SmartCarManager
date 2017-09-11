package com.shx.smartcarmanager.exception;

/**
 * Created by 邵鸿轩 on 2016/11/16.
 */

public class HttpTrowable extends Throwable {
    private static final long serialVersionUID = 1479055720893334755L;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpTrowable(String detailMessage, String errorCode) {
        super(detailMessage);
        this.errorCode=errorCode;
    }
}
