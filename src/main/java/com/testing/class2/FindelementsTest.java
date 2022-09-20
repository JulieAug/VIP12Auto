package com.testing.class2;

import com.testing.web.WebKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;

import java.util.List;

/**
 * @Classname FindelementsTest
 * @Description 类型说明
 * @Date 2022/5/28 21:55
 * @Created by 特斯汀Roy
 */
public class FindelementsTest {
    public static void main(String[] args) {
        WebKeyword web=new WebKeyword();
        web.openBrowser("chrome");
        web.visitWeb("https://www.baidu.com");
        List<WebElement> elements = web.getDriver().findElements(By.xpath("//span[@class='title-content-title']"));
        //findelements方法定位到的元素的list，通常遍历进行操作。
        for (WebElement element : elements) {
//            System.out.println(element);
            System.out.println(element.getAttribute("outerHTML"));
        }

        System.out.println("-----------------------获取一个元素的时候-----------------------------");
        WebElement first = web.getDriver().findElement(By.xpath("(//span[@class='title-content-title'])[1]"));
        System.out.println(first.getAttribute("outerHTML"));

        web.getDriver().quit();

//        web.getDriver().findElement(By.)

    }
}
