package com.shx.smartcarmanager.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2017/10/26.
 */

public class ImageUtils {
    /**
     * 得到网络上的bitmap
     *
     * @param imageUrl
     * @return
     */
    public static Bitmap getBitmapFromNetWork(String imageUrl){
        URL url=null;
        Bitmap bitmap=null;
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        try {
            url=new URL(imageUrl);
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5*1000);
            httpURLConnection.setReadTimeout(10*1000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            if (httpURLConnection.getResponseCode()==200) {
                inputStream=httpURLConnection.getInputStream();
                byteArrayOutputStream=new ByteArrayOutputStream();
                int len=0;
                byte [] buffer=new byte[1024];
                while((len=inputStream.read(buffer))!=-1){
                    byteArrayOutputStream.write(buffer, 0, len);
                    byteArrayOutputStream.flush();
                }
                byte [] imageData=byteArrayOutputStream.toByteArray();
                bitmap=BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            } else {
                System.out.println("图片请求失败");
            }
        } catch (Exception e) {
            System.out.println("e="+e.toString());
        }finally{
            try {
                if (byteArrayOutputStream!=null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream!=null) {
                    inputStream.close();
                }
                if (httpURLConnection!=null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                System.out.println("e="+e.toString());
            }
        }

        return bitmap;
    }
}
