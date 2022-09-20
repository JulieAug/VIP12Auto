package com.testing.class4;

import com.testing.common.AutoTools;
import com.testing.web.WebKeyword;
import org.openqa.selenium.By;

/**
 * @Classname ShopAdminTest
 * @Description 类型说明
 * @Date 2022/6/2 20:11
 * @Created by 特斯汀Roy
 */
public class ShopAdminTest {

    public static void main(String[] args) {
        WebKeyword web=new WebKeyword();
        web.openBrowser("chrome");
        web.visitWeb("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.setWindowSize(300,1600,1000);

        web.input("//input[@name='username']","admin");
        web.input("//input[@name='password']","123456");
        web.input("//input[@name='vertify']","1");
        web.click("//input[@value='登录']");

        web.click("//a[text()='商城']");
        web.switchFrame("workspace");
        web.click("//span[text()='添加商品']");

        web.saveTimeStampParam("ROY随机商品","VIP12随机测试","mmss");
        web.input("//input[@name=\"goods_name\"]","{ROY随机商品}");
        //尝试跟手工操作一样逐个点击。
        web.click("//select[@id='cat_id']");
        web.click("//option[@value='31']");
        //通过select提供的方法进行操作。
        web.halt("0.5");
        web.selectByValue("//select[@id='cat_id_2']","34");
        web.halt("0.5");
        web.selectByText("//select[@id='cat_id_3']","手机壳");
        web.input("//input[@name='shop_price']","666");
        web.input("//input[@name='market_price']","668");

        //文件上传
        web.click("//input[@onclick and contains(@title,'预览图')]");
        //先切到根层级然后再尝试切第二层的iframe，是不行的。因为要定位在workspace的元素，需要切进去。
//        web.switchRoot();
        //之前的操作都在workspace这个iframe中操作，layui是workspace内部的一个iframe
        //往自己的子iframe进行切换，直接切就好。不能跨层切。
        web.switchFrame("//iframe[contains(@id,'layui')]");

//        //通过键鼠操作完成上传
//        web.click("//div[text()='点击选择文件']/following-sibling::div/label");
//        web.halt("1");
//        web.keyboardInput("微信二维码.png");
//        web.keyboardEnter();

        //直接对input元素赋值
        web.upload("//div[text()='点击选择文件']/following-sibling::div/input","E:\\QSwork\\素材\\微信二维码.png");
        web.halt("1");
        web.click("//div[text()='确定使用']");

        //从layui回到workspace，也要进行iframe切换。
        web.switchParent();
        web.click("//label[text()='是' and @id]");

        web.runJs("window.scrollTo(0,2000)");

        //切换到ueditor_0
        web.switchFrame("ueditor_0");
//        web.input("//p","测试输入富文本框");
        web.runJSWithElement("//p","innerText='测试输入'");

        web.switchParent();
        web.click("//a[@id='submit']");

//        String text = web.getDriver().findElement(By.xpath("//div[@id='flexigrid']/table//tr[1]/td[4]")).getText();
//        System.out.println("第一个商品的值是"+text);
//        if (text.equals(shopName)){
//            System.out.println("测试成功");
//        }
        web.assertEleText("//div[@id='flexigrid']/table//tr[1]/td[4]","{ROY随机商品}");
        web.assertDbExist("select * from tp_goods where goods_name='{ROY随机商品}'");

        web.halt("30");
        web.closeBrowser();
    }


}
