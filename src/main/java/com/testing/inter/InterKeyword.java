package com.testing.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.testing.common.AutoTools;
import com.testing.common.ExcelWriter;
import com.testing.driverself.HttpDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname InterKeyword
 * @Description 类型说明
 * @Date 2022/6/25 21:59
 * @Created by 特斯汀Roy
 */
public class InterKeyword extends AutoTools {
    private HttpDriver driver;

    /**
     * 只有发包类的方法才会修改result，其它方法都是使用它。
     */
    public String result;

    public ExcelWriter writer;

    /**
     * 成员变量 常量用于记录当前要写入的成功和失败的信息
     **/
    public static final String PASS = "pass";

    public static final String FAIL = "fail";

    /***
     * 写入的位置
     */
    public int writeLine;

    public void setWriteLine(int writeLine) {
        this.writeLine = writeLine;
    }

    /*******用来表示写入执行结果和实际返回的列******/
    public static final int RESULT_COL = 10;

    public static final int ACTUAL_COL = 11;


    /**
     * 构造方法支持两种用法，一种不带excel数据驱动，一种可以使用excel数据驱动。
     */
    public InterKeyword() {
        driver = new HttpDriver();
    }

    public InterKeyword(ExcelWriter writer) {
        this.writer = writer;
        driver = new HttpDriver();
    }

    public void setPass() {
        if (writer != null) {
            writer.writeCell(writeLine, RESULT_COL, PASS);
        }
    }

    public void setFail() {
        if (writer != null) {
            writer.writeFailCell(writeLine, RESULT_COL, FAIL);
        }
    }

    /***
     * 写入实际返回结果
     */
    public void writePassResult() {
        if (writer != null)
            writer.writeCell(writeLine, ACTUAL_COL, result);
    }

    /**
     * 通过是否输入信息，来决定写入自定义信息还是返回结果
     * @param msg
     */
    public void writeFailResult(String ... msg) {
        if (writer != null) {
            //如果没有传信息，就直接写result
            if(msg.length==0) {
                writer.writeFailCell(writeLine, ACTUAL_COL, result);
            }else{
                writer.writeFailCell(writeLine,ACTUAL_COL,msg[0]);
            }
        }
    }


    /***
     * 完成发包的方法
     * 由于接口测试的特殊性，任何一个接口调用，都应该做断言操作
     * 所以不在发包方法中判断执行是否成功。
     */

    public void get(String url) {
        result = decodeUnicode(driver.get(replaceParam(url)));
        log.info(result);
    }

    public void post(String url, String type, String param) {
        result = decodeUnicode(driver.post(replaceParam(url), type, replaceParam(param)));
        log.info(result);
    }

    /**
     * 请求url格式
     *
     * @param url
     * @param param
     */
    public void postUrl(String url, String param) {
        result = decodeUnicode(driver.post(replaceParam(url), "url", replaceParam(param)));
        log.info(result);
    }

    public void delete(String url) {
        result = decodeUnicode(driver.delete(replaceParam(url)));
        log.info(result);
    }

    public void put(String url, String type, String param) {
        result = decodeUnicode(driver.put(replaceParam(url), type, replaceParam(param)));
        log.info(result);
    }

    /*********************************头域与Cookie管理机制******************************************/

    public void useHeader() {
        driver.useHeader();
        setPass();
    }

    public void notUseHeader() {
        driver.notUseHeader();
        setPass();
    }

    /**
     * header支持json格式传入，可以一次传入多个头域进行存储
     *
     * @param header
     */
    public void addHeader(String header) {
        try {
            JSONObject headers = JSON.parseObject(replaceParam(header));
            for (String key : headers.keySet()) {
                driver.addHeader(key, headers.get(key).toString());
            }
            setPass();
        } catch (Exception e) {
            setFail();
            log.error("添加头域失败", e.fillInStackTrace());
            writeFailResult(e.fillInStackTrace().toString());
        }
    }

    public void clearHeader() {
        driver.clearHeader();
        setPass();
    }

    /**
     * 设置是否使用cookie
     */
    public void useCookie() {
        driver.useCookie();
        setPass();
    }

    public void notUseCookie() {
        driver.notUserCookie();
        setPass();
    }

    /****************************************参数处理操作*****************************/
    public String jsonGetResult(String jsonPath) {
        return JSONPath.read(result, jsonPath).toString();
    }

    /**
     * 将返回结果中的json内容解析出来存储为参数。
     *
     * @param key
     * @param jsonPath
     */
    public void saveJsonParam(String key, String jsonPath) {
        try {
            saveParam(key, jsonGetResult(jsonPath));
            log.info("解析" + jsonPath + "得到的结果是" + jsonGetResult(jsonPath) + "存储为" + key);
            setPass();
        } catch (Exception e) {
            log.error("解析json内容失败", e.fillInStackTrace());
            setFail();
            writeFailResult(e.fillInStackTrace().toString());
        }
    }

    /**
     * 通过正则表达式获取返回结果中的数据存储为参数
     *
     * @param key
     * @param pattern
     */
    public void saveRegexParam(String key, String pattern) {
        try {
            Pattern resultPat = Pattern.compile(pattern);
            Matcher matcher = resultPat.matcher(result);
            if (matcher.find()) {
                paramMap.put(key, matcher.group(1));
                log.info("解析正则获取到的结果是"+matcher.group(1));
                setPass();
            } else {
                paramMap.put(key, "正则获取结果失败了");
                setFail();
                writeFailResult("正则获取"+pattern+"结果失败了");
            }
        } catch (Exception e) {
            log.error("正则获取报错",e.fillInStackTrace());
            setFail();
            writeFailResult("正则获取"+pattern+"结果失败了，报错是："+e.fillInStackTrace());
        }
    }

    /***************************断言类方法*******************************/

    /**
     * 用jsonpath进行解析并断言。
     *
     * @param jsonPath
     * @param expected
     * @return
     */
    public boolean jsonValueCheck(String jsonPath, String expected) {
        try {
            String actual = JSONPath.read(result, jsonPath).toString();
            if (actual.equals(expected)) {
                log.info("测试成功");
                setPass();
                writePassResult();
                return true;
            } else {
                log.error("测试失败，实际值是" + actual);
                setFail();
                writeFailResult();
                return false;
            }
        } catch (Exception e) {
            log.error("测试失败", e.fillInStackTrace());
            setFail();
            writeFailResult("返回结果是"+result+"但是json解析失败了，报错是："+e.fillInStackTrace().toString());
            return false;
        }
    }


    public boolean assertJsonContains(String jsonPath, String expected) {
        try {
            String actual = JSONPath.read(result, jsonPath).toString();
            if (actual.contains(expected)) {
                log.info("测试成功");
                setPass();
                writePassResult();
                return true;
            } else {
                log.error("测试失败，实际值是" + actual);
                setFail();
                writeFailResult();
                return false;
            }
        } catch (Exception e) {
            log.error("测试失败", e.fillInStackTrace());
            setFail();
            writeFailResult("返回结果是"+result+"但是json解析失败了，报错是："+e.fillInStackTrace().toString());
            return false;
        }
    }


    /**
     * 正则表达式解析结果并进行断言
     *
     * @param regex
     * @param expected
     * @return
     */
    public boolean regexCheck(String regex, String expected) {
        try {
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(result);
            if (matcher.find()) {
                String actual = matcher.group(1);
                if (actual.equals(expected)) {
                    log.info("测试成功");
                    setPass();
                    writePassResult();
                    return true;
                } else {
                    log.error("测试失败，解析到的值是" + actual);
                    setFail();
                    writeFailResult();
                    return false;
                }
            } else {
                log.error("测试失败，正则表达式" + regex + "么有匹配到内容");
                setFail();
                writeFailResult();
                return false;
            }
        } catch (Exception e) {
            log.error("正则断言失败", e.fillInStackTrace());
            setFail();
            writeFailResult("正则匹配失败，报错是："+e.fillInStackTrace());
            return false;
        }
    }


}
