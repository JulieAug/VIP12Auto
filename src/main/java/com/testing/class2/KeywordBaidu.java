package com.testing.class2;

import com.testing.driverself.GoogleDriver;
import org.openqa.selenium.WebDriver;

/**
 * @Classname KeywordBaidu
 * @Description 类型说明
 * @Date 2022/5/28 20:30
 * @Created by 特斯汀Roy
 */
public class KeywordBaidu {
    public static void main(String[] args) {
        GoogleDriver gg=new GoogleDriver("DriverExe\\chromedriver.exe");
        WebDriver driver = gg.getDriver();
        driver.get("https://www.baidu.com");

        //关闭浏览器窗口，不会清理chromedriver进程。
//        driver.close();
        //关闭浏览器，并且杀死chromedriver进程。
        driver.quit();


    }

}
