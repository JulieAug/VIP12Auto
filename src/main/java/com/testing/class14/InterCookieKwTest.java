package com.testing.class14;

import com.testing.inter.InterKeyword;

/**
 * @Classname InterCookieKwTest
 * @Description 类型说明
 * @Date 2022/6/28 21:11
 * @Created by 特斯汀Roy
 */
public class InterCookieKwTest {
    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.post("http://www.testingedu.com.cn/mypro/api/user/login","json","{\"username\":\"roy\",\"pwd\":\"123456\"}");
        inter.jsonValueCheck("$.msg","恭喜您登录成功");
        inter.notUseCookie();
        inter.post("http://www.testingedu.com.cn/mypro/api/user/setavatar","file" ,"{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        inter.jsonValueCheck("$.msg","{'userid': [ValidationError(['不能为空'])]}");
        inter.useCookie();
        inter.post("http://www.testingedu.com.cn/mypro/api/user/setavatar","file" ,"{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        inter.jsonValueCheck("$.msg","恭喜您修改成功");

    }
}
