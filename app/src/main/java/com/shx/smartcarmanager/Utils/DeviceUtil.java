package com.shx.smartcarmanager.Utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.UUID;

import static android.text.TextUtils.isEmpty;

/**
 * Created by 邵鸿轩 on 2017/6/15.
 */

public class DeviceUtil {
    /**
     * 获取设备IMEI码
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        String imei;
        try {
            imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            imei = "myTrace22";
        }
        return imei;
    }

    /**
     * eviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为：
     * 1，andriod（a）
     * <p>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            //wifi mac地址
//            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            String wifiMac = info.getMacAddress();
//            if (!isEmpty(wifiMac)) {
//                deviceId.append("wifi");
//                if(wifiMac.contains(":")){
//                    wifiMac=wifiMac.replace(":","");
//                }
//                deviceId.append(wifiMac);
//                Log.e("getDeviceId : ", deviceId.toString());
//                return deviceId.toString();
//            }
//            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                Log.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        Log.e("getDeviceId : ", deviceId.toString());
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = SharedPreferencesUtil.getStringValue(context,"uuid", "");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SharedPreferencesUtil.saveValue(context, "uuid", uuid);
        }
        Log.e("DeviceUtil", "getUUID : " + uuid);
        return uuid;
    }
}
