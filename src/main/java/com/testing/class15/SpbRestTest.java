package com.testing.class15;

import com.testing.inter.InterKeyword;

/**
 * @Classname SpbRestTest
 * @Description 类型说明
 * @Date 2022/6/30 21:27
 * @Created by 特斯汀Roy
 */
public class SpbRestTest {
    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.saveTimeStampParam("随机用户名","roy","HHmmss");
        //针对content-Type做了严格校验,如果不是json，那么就会 Unsupported Media Type
        inter.put("http://localhost:8080/user/register","url","{\"username\":\"{随机用户名}\",\"password\":\"123456\",\"nickname\":\"roy老师\",\"describe\":\"roy的rest测试\"}");
        inter.jsonValueCheck("$.error","Unsupported Media Type");

        inter.put("http://localhost:8080/user/register","json","{\"username\":\"{随机用户名}\",\"password\":\"123456\",\"nickname\":\"roy老师\",\"describe\":\"roy的rest测试\"}");
        inter.jsonValueCheck("$.msg","注册成功");

        inter.delete("http://localhost:8080/user/delete/roy213701");
        inter.jsonValueCheck("$.msg","删除用户成功");

        inter.delete("http://localhost:8080/user/delete/{随机用户名}");
        inter.jsonValueCheck("$.msg","删除用户成功");

    }
}
