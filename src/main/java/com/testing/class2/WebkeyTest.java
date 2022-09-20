package com.testing.class2;

import com.testing.web.WebKeyword;
import org.openqa.selenium.By;

/**
 * @Classname WebkeyTest
 * @Description 类型说明
 * @Date 2022/5/28 20:54
 * @Created by 特斯汀Roy
 */
public class WebkeyTest {
    public static void main(String[] args) {
        WebKeyword web =new WebKeyword();
        web.openBrowser("火狐");
        web.visitWeb("https://www.baidu.com");
        web.setWindowSize(400,1200,700);
        web.inputByCss("#kw","特斯汀");
        web.clickById("su");

        //获取标题的时候，有可能还没有加载完。隐式等待不负责这个事情。
        System.out.println(web.getDriver().getTitle());
        web.assertEleAttr("//input[@id='kw']","value","roy");
        //可以用get来获取driver对象，调试打开的浏览器。
        System.out.println(web.getDriver().getTitle());

    }
}
