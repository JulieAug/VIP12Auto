package com.testing.class11;

import com.testing.driverself.GoogleMobileDriver;
import com.testing.web.WebKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @Classname MobileWebTest
 * @Description 把需要测试的webH5在电脑浏览器中打开，进行模拟测试。
 * @Date 2022/6/21 20:35
 * @Created by 特斯汀Roy
 */
public class MobileWebTest {
    public static void main(String[] args) {
//        GoogleMobileDriver googleMobileDriver=new GoogleMobileDriver("DriverExe/chromedriver.exe");
//        WebDriver driver = googleMobileDriver.getDriver();
//        driver.get("http://www.testingedu.com.cn:8000");
//        driver.findElement(By.xpath("//a[text()=' 登录']")).click();
        WebKeyword H5=new WebKeyword();
        H5.openBrowser("mobile");
        H5.setWindowSize(600,600,900);
        H5.visitWeb("http://www.testingedu.com.cn:8000");
        H5.click("//a[text()=' 登录']");
        H5.input("//input[@id='username']","13800138006");
        H5.input("//input[@id='password']","123456");
        H5.click("//input[@value=\"登 录\"]");

    }
}
