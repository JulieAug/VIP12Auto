package com.testing.class2;

import com.testing.web.WebKeyword;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname WebfindTest
 * @Description 类型说明
 * @Date 2022/5/28 22:27
 * @Created by 特斯汀Roy
 */
public class WebfindTest {
    public static void main(String[] args) {
        WebKeyword web = new WebKeyword();
        web.openBrowser("chrome");
        web.visitWeb("https://www.baidu.com");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

        Date cssBefore = new Date();
        System.out.println("css执行前的时间：" + sdf.format(cssBefore));

        web.getDriver().findElement(By.cssSelector("#kw"));

        Date cssAfter = new Date();
        System.out.println("css执行后xpath执行前的时间：" + sdf.format(cssAfter));

        web.getDriver().findElement(By.xpath("//input[@id='kw']"));

        Date xpathAfter = new Date();
        System.out.println("xpath执行后的时间：" + sdf.format(xpathAfter));


    }
}
