package com.testing.class1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

/**
 * @Classname SeleniumDemo
 * @Description 类型说明
 * @Date 2022/5/26 21:12
 * @Created by 特斯汀Roy
 */
public class SeleniumDemo {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","E:\\AutoTools\\chromedriver\\102\\chromedriver.exe");
        ChromeOptions opt=new ChromeOptions();
        opt.addArguments("--disable-blink-features=AutomationControlled");
        opt.setExperimentalOption("excludeSwitches", new String[] {"enable-automation","load-extension"});

        WebDriver driver=new ChromeDriver(opt);
        //1、打开百度
        driver.get("https://www.baidu.com/");
//        //等于get
//        driver.navigate().to("https://www.baidu.com/");
//        Thread.sleep(1000);
//        driver.navigate().to("https://www.51job.com");
//        Thread.sleep(1000);
//        driver.navigate().back();
        driver.manage().window().setPosition(new Point(380,0));
        driver.manage().window().setSize(new Dimension(1544,944));
        //2、在输入框输入特斯汀
        WebElement input = driver.findElement(By.cssSelector("#kw"));
        input.sendKeys("特斯汀");
        Thread.sleep(1000);
        input.clear();
        Thread.sleep(1000);
        input.sendKeys("roy");

        //3、提交搜索
        driver.findElement(By.id("su")).click();

        //4、点击链接或者检查页面内容

        //5、预期结果断言。
        //验证input的内容是roy
        String value = driver.findElement(By.cssSelector("#kw")).getAttribute("value");
        if ("roy".equals(value)){
            System.out.println("验证成功，value的值是"+value);
        }

        //验证标题是roy_百度搜索
        String title = driver.getTitle();
        if("roy_百度搜索".equals(title)){
            System.out.println("验证成功，title是："+title);
        }

        System.out.println(driver.findElement(By.xpath("//title")).getText());

    }


}
