package com.testing.class14;

import com.testing.common.AutoTools;
import com.testing.driverself.HttpDriver;

/**
 * @Classname CookieKwTest
 * @Description 类型说明
 * @Date 2022/6/28 21:00
 * @Created by 特斯汀Roy
 */
public class CookieKwTest {
    public static void main(String[] args) {
        HttpDriver driver=new HttpDriver();
        //先登录
        String s = driver.postJson("http://www.testingedu.com.cn/mypro/api/user/login", "{\"username\":\"roy\",\"pwd\":\"123456\"}");
        System.out.println(AutoTools.decodeUnicode(s));
        driver.notUserCookie();
        driver.addHeader("Cookie","sessionid=08p7nqa1m5vhykou7b4x24qcs5sbcqmd");
        String s1 = driver.postUpload("http://www.testingedu.com.cn/mypro/api/user/setavatar", "{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        System.out.println(AutoTools.decodeUnicode(s1));
        driver.useCookie();
        driver.notUseHeader();
        String s2 = driver.postUpload("http://www.testingedu.com.cn/mypro/api/user/setavatar", "{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        System.out.println(AutoTools.decodeUnicode(s2));

    }
}
