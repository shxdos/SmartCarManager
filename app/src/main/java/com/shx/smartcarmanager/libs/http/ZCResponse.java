package com.shx.smartcarmanager.libs.http;

/**
 * Created by admin on 2016/3/21.
 */
public class ZCResponse {
//    {"total":0,"rows":"","error":"","errorCode":0,"success":true}
    private Integer total;
    private Integer rows;
    private String error;
    private String errorCode;
    private boolean success;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
