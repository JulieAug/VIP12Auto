package com.testing.class15;

import com.testing.inter.InterKeyword;

/**
 * @Classname TokenTest
 * @Description 类型说明
 * @Date 2022/6/28 21:43
 * @Created by 特斯汀Roy
 */
public class TokenRestTest {

    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.post("http://www.testingedu.com.cn:8081/inter/REST/auth","url","");
        inter.saveJsonParam("token值","$.token");
        inter.jsonValueCheck("$.msg","success");
        //添加token头
        inter.addHeader("{\"token\":\"{token值}\"}");
        //注册的时候，不能使用相同的用户名
        inter.saveTimeStampParam("随机用户名","roy","HHmm");
        inter.post("http://www.testingedu.com.cn:8081/inter/REST/user/register","url","{\"username\":\"{随机用户名}\",\"pwd\":\"123456\",\"nickname\":\"roy老师\",\"describe\":\"royrest\"}");
        //登录
        //请求的时候使用它。
        inter.post("http://www.testingedu.com.cn:8081/inter/REST/login/{随机用户名}/123456","url","");
        inter.saveJsonParam("用户id","$.userid");
        inter.jsonValueCheck("$.msg","恭喜您，登录成功");

        //后面的接口就可以不用再调用addHeader了，保持useHeader的状态
        inter.post("http://www.testingedu.com.cn:8081/inter/REST/login/{用户id}","url","");
        inter.jsonValueCheck("$.nickname","roy老师");
        //注销。
        inter.post("http://www.testingedu.com.cn:8081/inter/REST//login","","");
        inter.jsonValueCheck("$.msg","用户已退出登录");
    }


}
