package com.testing.class6.royPO;

import com.testing.class6.ShopAdminTest;

/**
 * @Classname RunRoyPO
 * @Description 类型说明
 * @Date 2022/6/9 21:34
 * @Created by 特斯汀Roy
 */
public class RunRoyPO {

    public static void main(String[] args) {

        ShopAdminLoginPage sap=new ShopAdminLoginPage();
        sap.openBrowser("chrome");
        sap.init();
        sap.login("admin","123456");

        ShopAdminPage sadp=new ShopAdminPage();
        //将登登录页面操作的浏览器driver传递给商品添加页面
        sadp.setDriver(sap.getDriver());
        sadp.init();
        sadp.addGoods("PO模式添加的商品");
        sadp.closeBrowser();

        ShopSearchPage ssp=new ShopSearchPage();
        ssp.openBrowser("chrome");
        ssp.init();
//        ssp.login();
        ssp.search();
        ssp.shopSearchDbAssert();
        ssp.halt("10");
        ssp.closeBrowser();





    }

}
