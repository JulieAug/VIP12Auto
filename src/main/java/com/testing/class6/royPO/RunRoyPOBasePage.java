package com.testing.class6.royPO;

import com.testing.web.WebKeyword;

/**
 * @Classname RunRoyPOBasePage
 * @Description 基于basePage统一管理浏览器。
 * @Date 2022/6/9 22:03
 * @Created by 特斯汀Roy
 */
public class RunRoyPOBasePage {
    public static void main(String[] args) {
        //浏览器管理
        WebKeyword basePage=new WebKeyword();
        basePage.openBrowser("chrome");
        //用例1
        ShopAdminLoginPage sap=new ShopAdminLoginPage();
        sap.setDriver(basePage.getDriver());
        sap.init();
        sap.login("admin","123456");
        //用例2
        ShopAdminPage sadp=new ShopAdminPage();
        //将登登录页面操作的浏览器driver传递给商品添加页面
        sadp.setDriver(basePage.getDriver());
        sadp.init();
        sadp.addGoods("PO模式添加的商品");
        //用例3
        ShopSearchPage ssp=new ShopSearchPage();
        ssp.setDriver(basePage.getDriver());
        ssp.init();
//        ssp.login();
        ssp.search();
        ssp.shopSearchDbAssert();
        ssp.halt("10");
        //浏览器关闭
        basePage.closeBrowser();



    }

}
