package com.testing.class11;

import com.testing.app.AppKeyword;

/**
 * @Classname NativeBrowser
 * @Description 把手机上的浏览器当成一个应用进行自动化测试。
 * @Date 2022/6/21 21:48
 * @Created by 特斯汀Roy
 */
public class NativeBrowser {
    public static void main(String[] args) {
        AppKeyword app=new AppKeyword();
        app.startAPP("4723","com.android.browser",".BrowserActivity","127.0.0.1:62001");
        app.click("|id|com.android.browser:id/url");
        app.input("|id|com.android.browser:id/url","http://www.testingedu.com.cn:8000");
        //通过回车访问网页
        app.pressEnter();
        app.click("|aid|登录");
        System.out.println("所有的context是："+app.getDriver().getContextHandles());
        System.out.println("当前的context是："+app.getDriver().getContext());
        app.getDriver().context("WEBVIEW_com.android.browser");
        //切换context之后，变成了webview模式，可以用selenium的定位操作方法，直接操作webview
        app.input("//input[@id='username']","13800138006");
        app.input("//input[@id='password']","123456");
        app.click("//input[@value=\"登 录\"]");

    }
}
