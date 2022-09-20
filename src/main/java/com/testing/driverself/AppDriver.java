package com.testing.driverself;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Classname AppDriver
 * @Description 类型说明
 * @Date 2022/6/18 20:11
 * @Created by 特斯汀Roy
 */
public class AppDriver {

    private AndroidDriver driver;

    public AppDriver(String port,String packageName,String ActivityName,String deviceName) throws Exception {
        URL url=new URL("http://127.0.0.1:"+port+"/wd/hub");

        DesiredCapabilities caps=new DesiredCapabilities();
        caps.setCapability("platformName","Android");
        caps.setCapability("appPackage",packageName);
        caps.setCapability("appActivity",ActivityName);
        caps.setCapability("skipServerInstallation",true);
        caps.setCapability("noReset",true);

        caps.setCapability("udid",deviceName);
        //使用中文输入
        caps.setCapability("unicodeKeyboard",true);
        caps.setCapability("resetKeyboard",true);

        driver=new AndroidDriver(url,caps);

    }



    public AndroidDriver getDriver() {
        return driver;
    }
}
