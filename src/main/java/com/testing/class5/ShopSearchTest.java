package com.testing.class5;

import com.testing.common.MysqlUtils;
import com.testing.web.WebKeyword;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
public class ShopSearchTest {

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
        web.saveParam("商品名","VIP12");
        web.input("//input[@id='q']", "{商品名}");
        web.click("//button[text()='搜索']");

        //将一个商品列表中的三个信息 名称、原价、现价都获取到并且存储到一个map里面。
        //按序号组合三个信息
        //名称 //div[@class='shop-list-splb p']/ul/li[序号]//div[@class='shop_name2']/a
        //原价 //div[@class='shop-list-splb p']/ul/li[序号]//div[@class='price-tag']/span[@class='old']/em[2]
        //现价 //div[@class='shop-list-splb p']/ul/li[序号]//div[@class='price-tag']/span[@class='now']/em[2]

        //获取商品的个数？
        //通过定位商品元素 //div[@class='shop-list-splb p']/ul/li 获取列表个数即可
        shopSearchDbAssert(web);

    }

    public static void shopSearchDbAssert(WebKeyword web) {
        int shopNumber = web.getDriver().findElements(By.xpath("//div[@class='shop-list-splb p']/ul/li")).size();
        List<Map<String, String>> shopGoodsList = new ArrayList<>();
        for (int i = 1; i <= shopNumber; i++) {
            Map<String, String> goodsInfo = new HashMap<>();
            String goodsName = web.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='shop_name2']/a");
            goodsInfo.put("goods_name", goodsName);
            goodsInfo.put("market_price", web.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='price-tag']/span[@class='old']/em[2]"));
            goodsInfo.put("shop_price", web.getText("//div[@class='shop-list-splb p']/ul/li[" + i + "]//div[@class='price-tag']/span[@class='now']/em[2]"));
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
}
