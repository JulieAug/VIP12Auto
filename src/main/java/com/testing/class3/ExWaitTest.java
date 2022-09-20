package com.testing.class3;

import com.testing.web.WebKeyword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @Classname WebkeyTest
 * @Description 类型说明
 * @Date 2022/5/28 20:54
 * @Created by 特斯汀Roy
 */
public class ExWaitTest {
    public static void main(String[] args) {
        WebKeyword web =new WebKeyword();
        web.openBrowser("火狐");
        web.visitWeb("https://www.baidu.com");
        web.setWindowSize(400,1200,700);
        //等待一个元素能够被定位到
//        web.exWaitElement("//input[@id='k']");
        web.inputByCss("#kw","特斯汀");
        web.clickById("su");
        //先设置显式等待的最长等待时长
        WebDriverWait wait=new WebDriverWait(web.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("特斯汀roy"));
        //获取标题的时候，有可能还没有加载完。隐式等待不负责这个事情。
        System.out.println(web.getDriver().getTitle());

    }
}
