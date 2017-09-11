package com.shx.smartcarmanager.libs.http;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shx.smartcarmanager.base.BaseApplication;
import com.shx.smartcarmanager.commons.LogGloble;
import com.shx.smartcarmanager.commons.SystemConfig;
import com.shx.smartcarmanager.exception.HttpTrowable;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by xuan on 16/3/7.
 */
public class HttpManager {
    private static HttpManager mHttpManager;
    private String TAG = "httplog";

    private HttpManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .connectTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .readTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);


    }

    public static HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                mHttpManager = new HttpManager();

            }
        }
        return mHttpManager;
    }

    /**
     * 用于post提交
     *
     * @param callBack 回调接口
     */
    public void doPost(final String url, final HashMap<String, String> request, final HttpCallBack callBack) {
        if (!isNetworkAvailable(BaseApplication.getContext())) {
//            ToastUtil.getInstance().toastInCenter(BaseApplication.getContext(),"网络异常");
            return;
        }

        PostFormBuilder builder = OkHttpUtils.post();
        builder.url(url);
        for (String key : request.keySet()) {
            //userid+字段名称用base64编码+.jpg
            builder.addParams(key, request.get(key));
        }

        builder.addHeader("charset", "utf-8")
                .tag(this)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        HttpTrowable httpTrowable = new HttpTrowable(e.getMessage(), "999999");
//                        callBack.doFaild(httpTrowable, request.getRequest().getParamsMap(), request.readUrl(), request.readMethod());
                    }

                    @Override
                    public void onResponse(Object result, int id) {
                        LogGloble.d("http：", "url--" + url + "--result--" + result);
                        ZCResponse response = null;
                        try {
                            response = MyJSON.parseObject((String) result, ZCResponse.class);
//                            if (!httpCalllBackPreFilter(response, request.readUrl())) {
//                                callBack.doSuccess(response, request.readUrl(), request.readMethod());
//                            } else {
//                                HttpTrowable httpTrowable = new HttpTrowable(response.getResponse().getMessage(), response.getResponse().getStatus());
//                                callBack.doFaild(httpTrowable, request.getRequest().getParamsMap(), request.readUrl(), request.readMethod());
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            HttpTrowable httpTrowable = new HttpTrowable(e.getMessage(), "99999");
//                            callBack.doFaild(httpTrowable, request.getRequest().getParamsMap(), request.readUrl(), request.readMethod());
                        }
                    }
                });

    }

    /**
     * 用于get请求
     * @param url
     * @param request
     * @param callBack
     */
    public void doGet(final String url, final HashMap<String, String> request, final HttpCallBack callBack) {
        GetBuilder builder = OkHttpUtils
                .get();
        builder.url(SystemConfig.BASE_HOST_RELEASE+url);
        for (String key : request.keySet()) {
            builder.addParams(key, request.get(key));
        }
        builder.build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Object result, int id) {
                LogGloble.d("http：", "url--" + url + "--result--" + result);
                ZCResponse response = null;
                try {
                    response = MyJSON.parseObject((String) result, ZCResponse.class);
                            if (httpCalllBackPreFilter(response)){
                                callBack.doSuccess(response, url);
                            } else {
                                HttpTrowable httpTrowable = new HttpTrowable(response.getError(), response.getErrorCode());
                                callBack.doFaild(httpTrowable, request, url);
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                    HttpTrowable httpTrowable = new HttpTrowable(e.getMessage(), "99999");
                            callBack.doFaild(httpTrowable, request, url);
                }
            }
        });
    }

    /**
     * 统一过滤
     *
     * @param response
     * @return
     */
    public static boolean httpCalllBackPreFilter(ZCResponse response) {
        return response.isSuccess();
    }

    // 检测网络
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
