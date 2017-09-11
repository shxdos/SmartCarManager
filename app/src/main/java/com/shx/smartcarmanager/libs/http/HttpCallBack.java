package com.shx.smartcarmanager.libs.http;


import com.shx.smartcarmanager.exception.HttpTrowable;

import java.util.Map;

/**
 * Http请求回调接口
 */
public interface HttpCallBack {
    /**
     * 成功回调
     */
    public boolean doSuccess(ZCResponse respose, String requestUrl);

    /**
     * 失败回调
     */
    public boolean doFaild(HttpTrowable error, Map<String, String> requestParams, String requestUrl);

    /**
     * 通讯回掉前拦截
     * @param result 回调数据
     * @param method 请求标示
     * @return
     */
    public boolean httpCallBackPreFilter(String result, String method);
}