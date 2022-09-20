package com.testing.class6;

import com.testing.common.MysqlUtils;
import com.testing.web.WebKeyword;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ShopSearchTest
 * @Description 类型说明
 * @Date 2022/6/7 22:31
 * @Created by 特斯汀Roy
 */
public class ShopSearchTest{
    //由于好几个方法都需要使用这个web，所以写成成员变量。
    public static WebKeyword basePage = new WebKeyword();

    //页面的属性：
    //url
    static String homepage="http://www.testingedu.com.cn:8000/";

    //元素的xpath
    static String backToHome="//a[text()='返回商城首页']";

    static String 搜索框= "//input[@id='q']";

    static String 搜索按钮="//button[text()='搜索']";

    /**
     * 登录逻辑
     */
    public static void login() {
        basePage.click("//a[text()='登录']");
        basePage.inputAll("id", "username", "13800138006");
        basePage.inputByCss("#password", "123456");
        //验证码已经被注释了，随便输
        basePage.inputByCss("#verify_code", "1");
        basePage.click("//a[@name='sbtbutton']");
    }

    /**
     * 断言页面查询数据是否和数据库一致的业务逻辑
     */
    public static void shopSearchDbAssert() {
        int shopNumber = basePage.getDriver().findElements(By.xpath("//div[@class='shop-list-splb p']/ul/li")).size();
        List<Map<String, String>> shopGoodsList = new ArrayList<>();
        for (int i = 1; i <= shopNumber; i++) {
            Map<String, String> goodsInfo = new HashMap<>();
            String goodsName = basePage.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='shop_name2']/a");
            goodsInfo.put("goods_name", goodsName);
            goodsInfo.put("market_price", basePage.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='price-tag']/span[@class='old']/em[2]"));
            goodsInfo.put("shop_price", basePage.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='price-tag']/span[@class='now']/em[2]"));
            shopGoodsList.add(goodsInfo);
        }
        System.out.println(shopGoodsList);

        MysqlUtils mysqlUtils=new MysqlUtils();
        mysqlUtils.createCon("jdbc:mysql://47.105.110.138:3306/tpshop?" +
                "useSSL=false&useUnicode=true&characterEncoding=utf-8","Will", "willfqng");
        List<Map<String, String>> dbGoods = mysqlUtils.queryResult("select goods_name,shop_price,market_price from tp_goods where goods_name like '%{商品名}%'");
        System.out.println(dbGoods);

        //比较两个列表中的数据元素是否完全一致。
        if(shopGoodsList.containsAll(dbGoods)&&dbGoods.containsAll(shopGoodsList)){
            System.out.println("查询结果完全一致，测试通过");
        }
    }

    /**
     * 搜索方法
     */
    public static void search() {
        basePage.click(backToHome);
        basePage.saveParam("商品名","VIP12");
        basePage.input(搜索框, "{商品名}");
        basePage.click(搜索按钮);
    }

    public static void addCart(){
        //通过直接访问url重置回到页面，从头开始执行后续操作。
        basePage.visitWeb(homepage);
        basePage.hover("//a[text()='手机数码']");
        basePage.click("//a[text()='手机' and not(@class)]");
        basePage.switchWindowByNum("2");
        //点击第一个商品的链接
        basePage.click("//div[@class=\"shop-list-splb p\"]/ul/li[1]//div[@class='shop_name2']/a");
        basePage.click("//a[@id='join_cart']");
        //切换到iframe中。
        basePage.switchFrame("//iframe[contains(@id,'layui')]");
        basePage.click("//a[text()='去购物车结算']");
        basePage.halt("30");
    }

    public static void main(String[] args) {
        basePage.openBrowser("chrome");
        basePage.visitWeb(homepage);
        basePage.setWindowSize(300, 1600, 1000);
        login();
        search();
        shopSearchDbAssert();
        addCart();
        basePage.halt("10");
        basePage.closeBrowser();
    }




}
