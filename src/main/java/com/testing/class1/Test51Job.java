package com.testing.class1;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Classname Test51Job
 * @Description 类型说明
 * @Date 2022/5/26 22:09
 * @Created by 特斯汀Roy
 */
public class Test51Job {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","E:\\AutoTools\\chromedriver\\102\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.51job.com");
        driver.manage().window().setPosition(new Point(380,0));
        driver.manage().window().setSize(new Dimension(1544,944));

        driver.findElement(By.id("kwdselectid")).sendKeys("软件测试");
        driver.findElement(By.xpath("//button[text()='搜索']")).click();


        Thread.sleep(2000);
        //断言：
        String value = driver.findElement(By.id("keywordInput")).getAttribute("value");
        System.out.println("输入框的内容是"+value);
        //获取标题
        String title = driver.getTitle();
        Thread.sleep(2000);
        //得到的结果是空的,因为本质上getText是获取页面上元素的显示内容。
        String titleText = driver.findElement(By.xpath("//title")).getText();
        //innerText属性才是真正意义上元素的内容。
        String titleinnerText = driver.findElement(By.xpath("//title")).getAttribute("innerText");
        System.out.println(title+"  和  "+titleText+"   和  "+titleinnerText);

    }

}
