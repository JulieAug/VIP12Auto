package com.testing.class14;

import com.testing.inter.InterKeyword;

import java.net.URLEncoder;

/**
 * @Classname TokenTest
 * @Description 类型说明
 * @Date 2022/6/28 21:43
 * @Created by 特斯汀Roy
 */
public class TokenTest {

    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.post("http://www.testingedu.com.cn:8081/inter/HTTP/auth","url","");
//        String s = inter.jsonGetResult("$.token");
//        System.out.println("token的值是"+s);
////        inter.addHeader("{\"token\":\""+s+"\"}");
        inter.saveJsonParam("token值","$.token");
        inter.jsonValueCheck("$.msg","success");
        //添加token头
        inter.addHeader("{\"token\":\"{token值}\"}");
        //注册的时候，不能使用相同的用户名
        inter.saveTimeStampParam("随机用户名","roy","HHmm");
        inter.post("http://www.testingedu.com.cn:8081/inter/HTTP//register?username={随机用户名}&pwd=123456&nickname={随机用户名}&describe=rouhuhu","url","");
        //登录
        inter.post("http://www.testingedu.com.cn:8081/inter/HTTP/login","url","username={随机用户名}&password=123456");
        inter.saveJsonParam("用户id","$.userid");
        inter.jsonValueCheck("$.msg","恭喜您，登录成功");

        //后面的接口就可以不用再调用addHeader了，保持useHeader的状态
        inter.post("http://www.testingedu.com.cn:8081/inter/HTTP/getUserInfo","url","id={用户id}");
        inter.jsonValueCheck("$.nickname","roy");
        //注销。
        inter.post("http://www.testingedu.com.cn:8081/inter/HTTP//logout","","");
        inter.jsonValueCheck("$.msg","用户已退出登录");
    }


}
