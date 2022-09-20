package com.testing.class6.pageFactoryPO;

import com.testing.class6.pageFactoryPO.pageAdmin.AddGoodsPage;
import com.testing.class6.pageFactoryPO.pageAdmin.AdminLoginPage;
import com.testing.class6.pageFactoryPO.pageShop.HomePage;
import com.testing.class6.pageFactoryPO.pageShop.LoginPage;
import com.testing.common.AutoTools;
import com.testing.web.WebKeyword;

/**
 * @Classname TestSeleniumPOFactory
 * @Description 类型说明
 * @Date 2021/1/16 22:02
 * @Created by 特斯汀Roy
 */
public class TestSeleniumPOFactory {
    public static void main(String[] args) {
        //通过basePage进行启动
        WebKeyword basePage=new WebKeyword();
        basePage.openBrowser("chrome");
        AutoTools.log.info("---------------执行后台登录界面测试----------------------");
        AdminLoginPage adminLoginPage=new AdminLoginPage(basePage.getDriver());
        adminLoginPage.login();
        AutoTools.log.info("---------------执行后台商品添加测试----------------------");
        AddGoodsPage addGoodsPage=new AddGoodsPage(basePage.getDriver());
        addGoodsPage.addGoods();
        AutoTools.log.info("---------------执行用户端登录测试----------------------");
        LoginPage loginPage=new LoginPage(basePage);
        loginPage.login();
        AutoTools.log.info("---------------执行用户端登录测试----------------------");
        HomePage homePage=new HomePage(basePage.getDriver());
        homePage.joinCart();
        basePage.closeBrowser();



    }
}
