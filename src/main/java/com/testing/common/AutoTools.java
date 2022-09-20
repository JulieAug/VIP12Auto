package com.testing.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname AutoTools
 * @Description 类型说明
 * @Date 2022/6/7 20:25
 * @Created by 特斯汀Roy
 *
 */
public class AutoTools {

    public static Logger log = LogManager.getLogger(AutoTools.class);

    //静态变量只有一份拷贝，paramMap在整个项目中只有一个。
    public static Map<String, String> paramMap = new HashMap<String, String>();

    /**
     * 生成指定范围内的随机数字
     *
     * @param bound
     * @return
     */
    public static int createRandomInt(int bound) {
        Random random = new Random();
        int nextInt = random.nextInt(bound);
        return nextInt;
    }


    /**
     * 输入可用的字符串列表，返回其中的随机一个
     *
     * @param availableValue
     * @return
     */
    public static String pickRandomString(String... availableValue) {
        //可变参数会作为一个数组来进行调用
        Random random = new Random();
        int indexNext = random.nextInt(availableValue.length - 1);
        return availableValue[indexNext];
    }


    /**
     * 生成指定格式的时间戳字符串
     *
     * @param format
     * @return
     */
    public static String timeStampString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String timeStamp = sdf.format(new Date());
        return timeStamp;
    }

    /**
     * 获得当前时间，格式yyyy-MM-dd hh:mm:ss
     *
     * @param
     * @return
     */
    /** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);

    public static String getDateTime() {
        try {
            return sdf_datetime_format.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            System.out.println("DateUtil.getDateTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 将变量存储到Map中。
     *
     * @param paramName
     * @param value
     */
    public static void saveParam(String paramName, String value) {
        paramMap.put(paramName, value);
    }

    /**
     * 基于一个输入值，生成拼接时间戳之后的字符串。
     *
     * @param paramName 比如随机商品
     * @param initValue 比如VIP12测试商品
     */
    public static void saveTimeStampParam(String paramName, String initValue, String format) {
        paramMap.put(paramName, initValue + timeStampString(format));
    }

    /**
     * 存储加密数据。
     * @param paramName
     * @param origin
     */
    public static void encryptParam(String paramName,String origin){
        EncryptUtils enc=new EncryptUtils();
        paramMap.put(paramName,enc.enCrypt(origin));
    }


//    roy:老王   Mi：可爱  will:帅哥
//
//    {roy}是个{Mi} => 老王是个{Mi} => 老王是个可爱

    /**
     * 替换字符串中的{变量名}为存储的值。
     *
     * @param origin
     * @return
     */
    public static String replaceParam(String origin) {
        String result = origin;
        for (String key : paramMap.keySet()) {
            result = result.replaceAll("\\{" + key + "\\}", paramMap.get(key));
        }
        return result;
    }

    /**
     * 输入秒数，转换为毫秒，调用thread.sleep来实现等待。
     *
     * @param second 输入一个秒数，可以是0.几秒
     */
    public void halt(String second) {
        try {
            float times = Float.parseFloat(second);
            Thread.sleep((long) (times * 1000));
            log.info("等待" + times + "秒");
        } catch (Exception e) {
            log.error("等待失败了，请检查输入参数。", e.fillInStackTrace());
        }
    }

    /**
     * 执行cmd命令的方法
     *
     * @param command
     */
    public void runCmd(String command) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c start " + command);
    }


    /**
     * 将unicode编码转换为对应的字符。
     * @param origin
     * @return
     */
    public static String decodeUnicode(String origin) {
        //1、通过正则找到字符串中的unicode编码 中的数字部分也就是匹配之后的matcher.group(1)。
        Pattern pattern = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher matcher = pattern.matcher(origin);
        //2、将unicode编码替换为对应的字符
        StringBuffer stringBuffer = new StringBuffer(origin.length());
        while (matcher.find()) {
            //3、将对应字符拼接到原本的字符串中。
            matcher.appendReplacement(stringBuffer, Character.toString((char) Integer.parseInt(matcher.group(1), 16)));
        }
        //将匹配完的内容拼接完之后，剩下的内容，也拼到strignbuffer中。
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 用来将特殊字符转换为url编码格式
     * @param origin
     * @return
     */
    public static String encodeUrl(String origin){
        try {
            String encode = URLEncoder.encode(origin,"utf-8");
            return encode;
        } catch (UnsupportedEncodingException e) {
            return "转码url失败";
        }
    }

    public static String decodeUrl(String origin){
        try {
            String encode = URLDecoder.decode(origin,"utf-8");
            return encode;
        } catch (UnsupportedEncodingException e) {
            return "解码url失败";
        }
    }

    /**
     * 通过读取一行的内容，通过名字找到关键字方法，并将名字后面的几列内容，作为参数在调用时使用。
     * @param keyword   关键字对象
     * @param rowContent    excel一行的内容
     * @param col   关键字所在列
     */
    public static void invokeKeyWord(Object keyword, List<String> rowContent, int col){
        Method method;
        //找到没有参数的对应方法,并尝试调用
        try {
            method= keyword.getClass().getMethod(rowContent.get(col));
            method.invoke(keyword);
            //找到了方法，执行完就不往下继续执行了
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是无参的方法");
        }
        //找到1个参数的对应方法,并尝试调用
        try {
            method= keyword.getClass().getMethod(rowContent.get(col),String.class);
            method.invoke(keyword,rowContent.get(col+1));
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是1个参数的方法");
        }
        try {
            method = keyword.getClass().getMethod(rowContent.get(col), String.class, String.class);
            method.invoke(keyword,rowContent.get(col+1),rowContent.get(col+2));
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是2个参数的方法");
        }
        try {
            method = keyword.getClass().getMethod(rowContent.get(col), String.class, String.class, String.class);
            method.invoke(keyword,rowContent.get(col+1),rowContent.get(col+2),rowContent.get(col+3));
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是3个参数的方法");
        }
        try {
            method = keyword.getClass().getMethod(rowContent.get(col), String.class, String.class, String.class, String.class);
            method.invoke(keyword,rowContent.get(col+1),rowContent.get(col+2),rowContent.get(col+3),rowContent.get(col+4));
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是4个参数的方法");
        }
        try {
            method = keyword.getClass().getMethod(rowContent.get(col), String.class, String.class, String.class, String.class, String.class);
            method.invoke(keyword,rowContent.get(col+1),rowContent.get(col+2),rowContent.get(col+3),rowContent.get(col+4),rowContent.get(col+5));
            return;
        } catch (Exception e) {
//            AutoTools.log.info("查找的方法" + rowContent.get(col) + "不是5个参数的方法");
        }
    }



}
