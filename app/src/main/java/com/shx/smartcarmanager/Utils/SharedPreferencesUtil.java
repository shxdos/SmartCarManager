package com.shx.smartcarmanager.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.shx.smartcarmanager.base.CommonValues;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


/**
 * 描述:本地文件存储（形式：键值对）工具类
 *
 * @author 邵鸿轩
 */
public class SharedPreferencesUtil {

    /**
     * 描述：保存字段到本地默认文件 默认保留到chinamworldbocsp文件下
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValue(Context context, String key, String value) {
        saveValueTOFile(context, key, value, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);
    }

    /**
     * 描述：保存字段到本地默认文件 默认保留到chinamworldbocsp文件下
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValue(Context context, String key, int value) {
        saveValueTOFile(context, key, value, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);

    }

    /**
     * 描述：保存字段到本地默认文件 默认保留到chinamworldbocsp文件下
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValue(Context context, String key, boolean value) {
        saveValueTOFile(context, key, value, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);

    }

    /**
     * 描述：获取保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return String 保存的value值
     */
    public static String getStringValue(Context context, String key, String defaultValue) {
        return getStringValueOfFile(context, key, defaultValue, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);
    }

    /**
     * 描述：获取保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return int 保存的value值
     */
    public static int getIntValue(Context context, String key, int defaultValue) {
        return getIntValueOfFile(context, key, defaultValue, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);
    }

    /**
     * 描述：获取保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return boolean 保存的value值
     */
    public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return getBooleanValueOfFile(context, key, defaultValue, CommonValues.DEFAULT_SHAREDPREFERENCES_NAME);
    }

    /**
     * 描述：保存字段到指定文件夹
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValueTOFile(Context context, String key, String value, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 描述：保存字段到指定文件夹
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValueTOFile(Context context, String key, int value, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    /**
     * 描述：保存字段到指定文件夹
     *
     * @param context 上下文对象
     * @param key     保存的key值
     * @param value   保存的value值
     */
    public static void saveValueTOFile(Context context, String key, boolean value, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    /**
     * 描述：获取指定文件下的保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return String 保存的value值
     */
    public static String getStringValueOfFile(Context context, String key, String defaultValue, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 描述：获取指定文件下的保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return int 保存的value值
     */
    public static int getIntValueOfFile(Context context, String key, int defaultValue, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 描述：获取指定文件下的保存的字段值
     *
     * @param context
     * @param key          保存的key值
     * @param defaultValue 获取不到的默认返回值
     * @return boolean 保存的value值
     */
    public static boolean getBooleanValueOfFile(Context context, String key, boolean defaultValue, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 描述：删除指定文件夹下的所有字段
     *
     * @param context
     * @param fileName 文件名称
     * @return boolean 操作是否成功
     */
    public static boolean clearFile(Context context, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.edit().clear().commit();
    }
    /**
     * desc:保存对象

     * @param context
     * @param key
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    public static void saveObject(Context context, String key , Object obj){
        try {
            // 保存对象
            Editor sharedata = context.getSharedPreferences(CommonValues.DEFAULT_SHAREDPREFERENCES_NAME, 0).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }
    /**
     * desc:获取保存的Object对象
     * @param context
     * @param key
     * @return
     * modified:
     */
    public static Object readObject(Context context, String key ){
        try {
            SharedPreferences sharedata = context.getSharedPreferences(CommonValues.DEFAULT_SHAREDPREFERENCES_NAME, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringUtil.stringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

}
