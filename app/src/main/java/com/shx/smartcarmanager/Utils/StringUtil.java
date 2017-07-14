package com.shx.smartcarmanager.Utils;

/**
 * 文件名	：StringUtil.java
 * 创建日期	：2012-10-15
 * Copyright (c) 2003-2012 北京联龙博通
 * <p/>
 * All rights reserved.
 */

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * StringUtil.java 字符串处理工具类
 *
 * @version 1.0
 */
public class StringUtil {

    private static final String TAG = "StringUtil";

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            boolean empty = true;
            for (int i = 0; i < object.length; i++)
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            return empty;
        }
        return false;
    }


    /**
     * 字符串是否全数字（无符号、小数点）
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    /**
     * 字符串是否全数字或英文字母（无符号、小数点）
     *
     * @param str
     * @return
     */
    public static boolean isAlphaDigit(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < '0' || c > '9' && c < 'A' || c > 'Z' && c < 'a' || c > 'z')
                return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }


    /**
     * 按千位分割格式格式化数字 千分位
     *
     * @param originalNumStr 原来的要被格式化的数字字符串
     * @param scale          小数点保留位数
     * @return
     */
    public static String getKibolitDivisionStr(String originalNumStr, int scale) {
        try {
            if (originalNumStr == null || "".equals(originalNumStr) || "null".equals(originalNumStr)) {
                return "_";
            }
            if (originalNumStr.contains(",") || originalNumStr.contains("，")) {
                return originalNumStr;
            }
            String temp = "###,###,###,###,###,###,###,##0";
            if (scale > 0)
                temp += ".";
            for (int i = 0; i < scale; i++)
                temp += "0";
            DecimalFormat format = new DecimalFormat(temp);
            Double d = StringUtil.getDoubleFromStr(originalNumStr);
            return format.format(d).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return originalNumStr;
        }

    }


    /**
     * 按千位分割格式格式化数字
     *
     * @param text  原数字
     * @param scale 小数点保留位数
     * @return
     */
    public static String parseStringPattern2(String text, int scale) {
        if (text == null || "".equals(text) || "null".equals(text)) {
            return parseStringPattern2("0", scale);
        }
        if (text.contains(",") || text.contains("，")) {
            return text;
        }
        String temp = "###,###,###,###,###,###,###,##0";
        if (scale > 0)
            temp += ".";
        for (int i = 0; i < scale; i++)
            temp += "0";
        DecimalFormat format = new DecimalFormat(temp);
        Double d = StringUtil.getDoubleFromStr(text);
        return format.format(d).toString();
    }

    /**
     * 取得类的简单名
     *
     * @param obj
     * @return 类的简单名
     */
    public static String getSimpleName(Object obj) {
        if (obj == null)
            return null;
        String name = obj.getClass().getName();
        if (name.toLowerCase().indexOf("$proxy") >= 0) {
            name = obj.toString();
            int idx = name.lastIndexOf("@");
            if (idx > 0)
                name = name.substring(0, idx);
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }


    /**
     * 获取字符串的长度, 如果为null则长度为0
     *
     * @param str
     * @return
     */
    public static int length(String str) {
        if (isNullOrEmpty(str))
            return 0;
        else
            return str.trim().length();

    }


    public static Locale toLocale(String localeStr) {
        if (localeStr == null)
            return null;
        String[] part = localeStr.split("_");
        if (part.length == 1)
            return new Locale(part[0].toLowerCase());
        if (part.length == 2)
            return new Locale(part[0].toLowerCase(), part[1].toUpperCase());
        if (part.length == 3)
            return new Locale(part[0].toLowerCase(), part[1].toUpperCase(),
                    part[2].toUpperCase());
        return new Locale(localeStr);
    }

    /**
     * 使用“,”对原有描述进行分隔，对所有为空的内容替换为“-”，重新组合后返回
     * <p/>
     * 特殊注意：当传入值为null时，返回”“ 当传入值为”“时，返回”-“ 当传入值只包含”,“时，返回”-,-“
     *
     * @param args
     * @return fillStr
     */
    public static String fillNullArgs(String args) {

        String fillStr = "";

        // 参数为空指针返回空串
        if (args == null) {
            return fillStr;
        }

        // 无限次匹配，保留末尾空数组
        String[] strArgs = args.split(",", -1);

        // 替换空元素
        for (int i = 0; i < strArgs.length; i++) {
            if (StringUtil.isNullOrEmpty(strArgs[i])) {
                strArgs[i] = "-";
            }
            fillStr = fillStr + strArgs[i] + ",";
        }

        // 去掉最后的”,“
        if (!StringUtil.isNullOrEmpty(fillStr)
                && (fillStr.substring(fillStr.length() - 1, fillStr.length())
                .equals(","))) {
            fillStr = fillStr.substring(0, fillStr.length() - 1);
        }

        return fillStr;
    }

    /**
     * 验证时间（小时分钟）是否合法 0000-2359
     *
     * @param time
     * @return
     */
    public static boolean checkTime(String time) {
        boolean flag = true;

        if (null == time || "".equals(time))
            flag = false;
        /** 必须为4位数字 **/
        if (time != null && 4 != time.length())
            flag = false;
        /** 必须是数字 **/
        if (time != null && !isDigit(time))
            flag = false;

        if (flag) {
            int num1 = Integer.parseInt(time.substring(0, 2));
            int num2 = Integer.parseInt(time.substring(2, 4));
            if (num1 > 23)
                flag = false;
            if (num2 > 59)
                flag = false;
        }

        return flag;

    }

    /**
     * @param nativeString
     * @return
     * @Title: getISOString
     * @Description: 获取转换成ISO8859-1的字符串
     */
    public static String getISOString(String nativeString) {
        String checkNickNameStr = "";
        try {
            //转化编码，能够测出中英文混合长度
            checkNickNameStr = new String(nativeString.getBytes("GBK"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return checkNickNameStr;
    }


    /**
     * 类型转换
     *
     * @param str
     * @return
     */
    public static float getFloatFromStr(String str) {
        float f = 0f;
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 类型转换
     *
     * @param str
     * @return
     */
    public static double getDoubleFromStr(String str) {
        double d = 0;
        try {
            d = Double.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Integer getIntFromStr(String str) {
        Integer f = 0;
        try {
            f = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 自动播放文本
     *
     * @param target
     * @param start
     * @param end
     * @param duration
     * @param scale
     */
    public static void autoTextPlay(final TextView target, final float start,
                                    final float end, long duration, final int scale) {

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String temp = "###,###,###,###,###,###,###,##0";
                if (scale > 0)
                    temp += ".";
                for (int i = 0; i < scale; i++)
                    temp += "0";
                FloatEvaluator evalutor = new FloatEvaluator();
                DecimalFormat format = new DecimalFormat(temp);
                float fraction = valueAnimator.getAnimatedFraction();
                float currentValue = evalutor.evaluate(fraction, start, end);
                target.setText(format.format(currentValue));
            }
        });
        animator.setDuration(duration);
        animator.start();
    }


    /**
     * 秒数转换成分钟和秒数
     *
     * @param totleSeconds
     * @return
     */
    public static String getLastTime(int totleSeconds) {
        int month = totleSeconds / 60;
        String monthStr = "" + month;
        if (month < 10) {
            monthStr = "0" + month;
        }
        int second = totleSeconds % 60;
        String secondStr = "" + second;
        if (second < 10) {
            secondStr = "0" + second;
        }
        return monthStr + "分" + secondStr + "秒";
    }

    /**
     * 秒数转换成小时分钟和秒数
     *
     * @param totleSeconds
     * @return
     */
    public static String getLastTimeWithhour(int totleSeconds) {
        int hour = totleSeconds / 60 / 60;
        String hourStr = "" + hour;
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        int month = (totleSeconds / 60) % 60;
        String monthStr = "" + month;
        if (month < 10) {
            monthStr = "0" + month;
        }
        int second = totleSeconds % 60;
        String secondStr = "" + second;
        if (second < 10) {
            secondStr = "0" + second;
        }
        return hourStr + "时" + monthStr + "分" + secondStr + "秒";
    }

    /**
     * 高亮放大字符串的一部分字符
     *
     * @param str      字符串
     * @param lightStr 需要高亮的部分字符串
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder lightStr(String str, String lightStr) {
        int fstart = str.indexOf(lightStr);
        int fend = fstart + lightStr.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new RelativeSizeSpan(1.3f), fstart, fend, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new StyleSpan(Typeface.BOLD), fstart, fend, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder lightStr(String str, String lightStr, String color) {
        int fstart = str.indexOf(lightStr);
        int fend = fstart + lightStr.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new RelativeSizeSpan(1.3f), fstart, fend, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 获取用户星级
     *
     * @param score
     * @return
     */
    public static float getStar(float score) {
        if (score > 100) {
            return 5f;
        }
        float step = 0.5f;
        float startemp = score / 20;
        float decimalPart = startemp - (int) startemp;
        if (decimalPart > step) {
            decimalPart = step;
        } else {
            decimalPart = 0;
        }
        float star = (int) startemp + decimalPart;
        System.out.println(star);
        return star;
    }

    /**
     * 使用gzip进行压缩
     */
    public static String zip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return new String(Base64.encode(out.toByteArray(), Base64.DEFAULT));
    }

    /**
     * <p>
     * Description:使用gzip进行解压缩
     * </p>
     *
     * @param compressedStr
     * @return
     */
    public static String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = Base64.decode(compressedStr, Base64.DEFAULT);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] stringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * 将价格格式化为带人民币符号
     */
    public static SpannableStringBuilder formatMoney(String price) {
        String str=parseStringPattern2(price, 2);
        return resizeStr(addRmbTag(str),2);
    }

    /**
     * price 价格
     * color 颜色
     *
     */
    public static SpannableStringBuilder formatMoney(double price) {
        String str = parseStringPattern2(String.valueOf(price), 2);
        return  resizeStr(addRmbTag(str),2);
    }

    public static String addRmbTag(String price){
        String moneyStr = "¥%s";
        return String.format(moneyStr, price);
    }

    /**
     * 得到服务费
     */
    public static String getServiceMoney(double totalPrice, double ticketPrice, int num) {
        double totalTicketPrice = ticketPrice * num;
        return String.valueOf(totalPrice - totalTicketPrice);
    }

    /**
     * 计算相差价格
     */
    public static String getDifferPrice(double basePrice, double ticketPrice) {
        double diff = ticketPrice - basePrice;
        StringBuilder sb = new StringBuilder();
        if (diff == 0) {
            return "";
        } else if (diff > 0) {
            sb.append("+");
        } else if (diff < 0) {
            sb.append("-");
        }
        sb.append(formatMoney(diff));
        return sb.toString();
    }

    /**
     * 将int类型的价格转为带人民币符号的
     */
    public static String formatMoney(int price) {
        String moneyStr = "¥%d";
        return String.format(moneyStr, price);
    }

    /**
     * 将价格格式化
     */
    public static String formatIncomeMoney(double price) {
        String moneyStr = "+%f元";
        return String.format(moneyStr, price);
    }

    public static String formatStr(String formatStr, String str) {
        return String.format(formatStr, str);
    }


    /**
     * 分离6:00-8:00这样的时间
     **/
    public static String[] separateTime(String time) {
        int index = time.indexOf("-");
        String[] str = new String[2];
        str[0] = time.substring(0, index);
        str[1] = time.substring(index + 1, time.length());
        return str;
    }

    /**
     * 合成发票的类型和抬头
     */
    public static String invoiceCompound(String invoiceType, String invoiceTitle) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(invoiceType);
        stringBuilder.append("-");
        stringBuilder.append(invoiceTitle);
        return stringBuilder.toString();
    }

    /**
     * 商品类型后面加上运力二字
     */
    public static String capacityCompound(String businessType) {
        StringBuilder sb = new StringBuilder();
        sb.append(businessType);
        sb.append("运力");
        return sb.toString();
    }

    public static String unitCompound(String number, String unit) {
        if (TextUtils.isEmpty(number)) {
            return "空";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(number);
            sb.append(unit);
            return sb.toString();
        }
    }

    /**
     * 拼接两个字符串，不判断是否为空
     * */
    public static String strCompound(String... str){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<str.length;i++){
            sb.append(str[i]);
        }
        return  sb.toString();
    }

    /**
     * 得到待付款金额
     */
    public static String getWaitPayPrice(double totalPrice, double unconfirmedPrice, double paidPrice) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String str = format.format(totalPrice - unconfirmedPrice - paidPrice);
        return str;
    }

    /**
     * 根据分隔符分割字符串
     */
    public static String[] splitStr(String str, String symbol) {
        String[] split;
        if (TextUtils.isEmpty(str)) {
            return new String[]{};
        }
        if (str.contains(symbol)) {
            split = str.split(symbol);
        } else {
            split = new String[]{str};
        }
        return split;
    }


    /***
     * 单价*数量
     */
    public static double getSumPrice(double unitPrice,int number){
        return unitPrice*number;
    }
    /**
     * 金额字符串的小数点后缩小
     *
     * @param str      字符串
     * @param resizeNumber 需要缩小的部分字符串长度
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder resizeStr(String str, int resizeNumber) {
        int fstart = str.length()-resizeNumber;
        int fend = str.length();
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new RelativeSizeSpan(0.7f), fstart, fend, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }
}
