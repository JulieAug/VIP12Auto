package com.testing.class6.royPO;

import com.testing.web.WebKeyword;

/**
 * @Classname ShopAdminTest
 * @Description 类型说明
 * @Date 2022/6/2 20:11
 * @Created by 特斯汀Roy
 */
public class ShopAdminPage extends WebKeyword {

    //属性
    private String url="http://www.testingedu.com.cn:8000/index.php/Admin/Index/index";

    public void init(){
        visitWeb(url);
        setWindowSize(300,1600,1000);

    }
    
    public void assertAddGoods() {
        assertEleText("//div[@id='flexigrid']/table//tr[1]/td[4]","{ROY随机商品}");
        assertDbExist("select * from tp_goods where goods_name='{ROY随机商品}'");
    }

    public void addGoods(String goodsName) {
        click("//a[text()='商城']");
        switchFrame("workspace");
        click("//span[text()='添加商品']");

        saveTimeStampParam("ROY随机商品",goodsName,"mmss");
        input("//input[@name=\"goods_name\"]","{ROY随机商品}");
        //尝试跟手工操作一样逐个点击。
        click("//select[@id='cat_id']");
        click("//option[@value='31']");
        //通过select提供的方法进行操作。
        halt("0.5");
        selectByValue("//select[@id='cat_id_2']","34");
        halt("0.5");
        selectByText("//select[@id='cat_id_3']","手机壳");
        input("//input[@name='shop_price']","666");
        input("//input[@name='market_price']","668");

        //文件上传
        click("//input[@onclick and contains(@title,'预览图')]");
        switchFrame("//iframe[contains(@id,'layui')]");

        upload("//div[text()='点击选择文件']/following-sibling::div/input","E:\\QSwork\\素材\\微信二维码.png");
        halt("1");
        click("//div[text()='确定使用']");
        switchParent();
        click("//label[text()='是' and @id]");
        runJs("window.scrollTo(0,2000)");

        //切换到ueditor_0
        switchFrame("ueditor_0");
        runJSWithElement("//p","innerText='测试输入'");
        switchParent();
        click("//a[@id='submit']");
    }


}
