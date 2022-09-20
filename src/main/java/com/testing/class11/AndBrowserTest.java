package com.testing.class11;

import com.testing.app.AppKeyword;
import com.testing.driverself.AndroidBrowserDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * @Classname AndBrowserTest
 * @Description 把手机上的浏览器作为一个chrome，展开web自动化测试。
 * @Date 2022/6/21 21:19
 * @Created by 特斯汀Roy
 */
public class AndBrowserTest {
    public static void main(String[] args) throws Exception {
//        AndroidBrowserDriver browserDriver=new AndroidBrowserDriver("4723","127.0.0.1:62001");
//        AndroidDriver driver = browserDriver.getDriver();
//        driver.get("http://www.testingedu.com.cn:8000");
//        driver.findElement(By.xpath("//a[text()=' 登录']")).click();
//        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("13800138006");
//        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
//        driver.findElement(By.xpath("//input[@value=\"登 录\"]")).click();
        AppKeyword browser=new AppKeyword();
        browser.startBrowser("4723","127.0.0.1:62001");
        browser.visitWeb("http://www.testingedu.com.cn:8000");
        browser.click("//a[text()=' 登录']");
        browser.input("//input[@id='username']","13800138006");
        browser.input("//input[@id='password']","123456");
        browser.click("//input[@value=\"登 录\"]");

    }
}
