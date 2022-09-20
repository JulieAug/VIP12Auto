package com.testing.class15;

import com.testing.inter.InterKeyword;

/**
 * @Classname TokenTest
 * @Description 类型说明
 * @Date 2022/6/28 21:43
 * @Created by 特斯汀Roy
 */
public class TokenTest {

    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.post("http://192.168.95.138:8080/encrypt/HTTP/auth","url","");
        inter.saveJsonParam("token值","$.token");
        inter.jsonValueCheck("$.msg","success");
        //添加token头
        inter.addHeader("{\"token\":\"{token值}\"}");
        //注册的时候，不能使用相同的用户名
        inter.saveTimeStampParam("随机用户名","roy","HHmm");
        inter.post("http://192.168.95.138:8080/encrypt/HTTP//register?username={随机用户名}&pwd=123456&nickname={随机用户名}&describe=rouhuhu","url","");
        //登录
        //保存加密后的数据
        inter.encryptParam("加密密码","123456");
        System.out.println(inter.paramMap.get("加密密码"));
        //请求的时候使用它。
        inter.post("http://192.168.95.138:8080/encrypt/HTTP/login","url","username={随机用户名}&password={加密密码}");
        inter.saveJsonParam("用户id","$.userid");
        inter.jsonValueCheck("$.msg","恭喜您，登录成功");

        //后面的接口就可以不用再调用addHeader了，保持useHeader的状态
        inter.post("http://192.168.95.138:8080/encrypt/HTTP/getUserInfo","url","id={用户id}");
        inter.jsonValueCheck("$.nickname","roy");
        //注销。
        inter.post("http://192.168.95.138:8080/encrypt/HTTP//logout","","");
        inter.jsonValueCheck("$.msg","用户已退出登录");
    }


}
