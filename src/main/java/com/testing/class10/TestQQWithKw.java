package com.testing.class10;

import com.testing.app.AppKeyword;

/**
 * @Classname TestQQWithKw
 * @Description 类型说明
 * @Date 2022/6/18 20:23
 * @Created by 特斯汀Roy
 */
public class TestQQWithKw {

    public static void main(String[] args) {
        AppKeyword app=new AppKeyword();
        app.startServer("12345");
        app.startAPP("12345","com.tencent.mobileqq",
                ".activity.SplashActivity","127.0.0.1:62001");
        app.input("|aid|请输入QQ号码或手机或邮箱","2798145476");
        app.input("|id|com.tencent.mobileqq:id/password","roy12345");
        app.click("//*[@content-desc=\"登 录\"]");
        app.halt("5");
//        app.tapAxis("65","100");
//        app.tapRateWithScreen("0.072","0.063");
        app.tapRateElement("|id|com.tencent.mobileqq:id/ws","0.072","0.5");
        app.tapAdbRateWithScreen("0.072","0.063");
    }

}
