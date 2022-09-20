package com.testing.class3;

import com.testing.web.WebKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @Classname ShopBuyTest
 * @Description 类型说明
 * @Date 2022/5/31 20:12
 * @Created by 特斯汀Roy
 */
public class ShopBuyTest {

    public static void main(String[] args) {
        WebKeyword web = new WebKeyword();
        web.openBrowser("chrome");
        web.visitWeb("http://www.testingedu.com.cn:8000/");
        web.setWindowSize(300, 1600, 1000);
        //登录
        web.click("//a[text()='登录']");
        web.inputAll("id", "username", "13800138006");
        web.inputByCss("#password", "123456");
        //验证码已经被注释了，随便输
        web.inputByCss("#verify_code", "1");
        web.click("//a[@name='sbtbutton']");
        web.click("//a[text()='返回商城首页']");
        //骚操作，可以通过显式等待来等待元素的出现，手动操作一下之后再进行后续操作。
//        WebDriverWait wait =new WebDriverWait(web.getDriver(),Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.invisibilityOf(web.getDriver().findElement(By.xpath("//a[text()='手机' and not(@class)]"))));
        web.hover("//a[text()='手机数码']");
        String handle = web.getDriver().getWindowHandle();
        System.out.println("打开新窗口之前的窗口的句柄是" + handle);
        System.out.println("打开之前所有的句柄" + web.getDriver().getWindowHandles());

        web.click("//a[text()='手机' and not(@class)]");

        System.out.println("打开之后所有的句柄" + web.getDriver().getWindowHandles());
//        web.switchWindowByContent("//div[@class=\"shop-list-splb p\"]/ul/li[1]//div[@class='shop_name2']/a");
        web.switchWindowByNum("2");
        //点击第一个商品的链接
        web.click("//div[@class=\"shop-list-splb p\"]/ul/li[1]//div[@class='shop_name2']/a");

        web.click("//a[@id='join_cart']");
        //切换到iframe中。
//        web.switchFrame("layui-layer-iframe1");
        web.switchFrame("//iframe[contains(@id,'layui')]");
        web.click("//a[text()='去购物车结算']");

        web.halt("30");
        web.closeBrowser();

    }


}
