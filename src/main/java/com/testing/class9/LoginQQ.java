package com.testing.class9;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Classname LoginQQ
 * @Description 类型说明
 * @Date 2022/6/16 22:35
 * @Created by 特斯汀Roy
 */
public class LoginQQ {

    public static void main(String[] args) throws MalformedURLException {
        URL url=new URL("http://127.0.0.1:4723/wd/hub");

        DesiredCapabilities caps=new DesiredCapabilities();
        caps.setCapability("platformName","Android");
        caps.setCapability("appPackage","com.tencent.mobileqq");
        caps.setCapability("appActivity",".activity.SplashActivity");
        caps.setCapability("skipServerInstallation",true);
        caps.setCapability("noReset",true);

        caps.setCapability("udid","127.0.0.1:62001");
        //使用中文输入
        caps.setCapability("unicodeKeyboard",true);
        caps.setCapability("resetKeyboard",true);

        AndroidDriver driver=new AndroidDriver(url,caps);






    }



}
