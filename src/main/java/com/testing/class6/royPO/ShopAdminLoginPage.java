package com.testing.class6.royPO;

import com.testing.web.WebKeyword;

/**
 * @Classname ShopAdminLoginPage
 * @Description 类型说明
 * @Date 2022/6/9 21:52
 * @Created by 特斯汀Roy
 */
public class ShopAdminLoginPage extends WebKeyword {

    //属性
    private String url="http://www.testingedu.com.cn:8000/Admin/Admin/login";

    public void init(){
        visitWeb(url);
        setWindowSize(300,1600,1000);

    }

    public  void login(String username,String pwd) {
        input("//input[@name='username']",username);
        input("//input[@name='password']",pwd);
        input("//input[@name='vertify']","1");
        click("//input[@value='登录']");
    }
}
