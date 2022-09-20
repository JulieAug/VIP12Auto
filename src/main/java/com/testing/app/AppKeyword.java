package com.testing.app;

import com.google.common.io.Files;
import com.testing.common.AutoTools;
import com.testing.driverself.AndroidBrowserDriver;
import com.testing.driverself.AppDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * @Classname AppKeyword
 * @Description 类型说明
 * @Date 2022/6/18 20:16
 * @Created by 特斯汀Roy
 */
public class AppKeyword extends AutoTools {
    private AndroidDriver driver;

    public AndroidDriver getDriver() {
        return driver;
    }

    public void setDriver(AndroidDriver driver) {
        this.driver = driver;
    }


    /*****************************设备启动类的关键字*****************************************/

    public void startServer(String port) {
        try {
            runCmd("appium -g " + "log/Appium"+timeStampString("MMdd-HHmm")+".log" + " --log-timestamp --local-timezone -p" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 启动对应的应用
     *
     * @param port
     * @param packageName
     * @param activityName
     * @param deviceName
     */
    public void startAPP(String port, String packageName, String activityName, String deviceName) {
        try {
            AppDriver app = new AppDriver(port, packageName, activityName, deviceName);
            driver = app.getDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            halt("5");
        } catch (Exception e) {
            log.error("启动app时报错，请检查appium服务端日志", e.fillInStackTrace());
        }
    }


    /**
     * 启动浏览器作为H5应用测试方案。
     * @param port
     * @param deviceName
     */
    public void startBrowser(String port,String deviceName){
        try {
            AndroidBrowserDriver abrowser=new AndroidBrowserDriver(port,deviceName);
            driver=abrowser.getDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            halt("5");
        } catch (Exception e) {
            log.error("启动app时报错，请检查appium服务端日志", e.fillInStackTrace());
        }
    }

    /**
     * 使用browserName启动浏览器的时候，可以用的访问网页方法。
     * @param url
     */
    public void visitWeb(String url){
        try {
            driver.get(url);
        } catch (Exception e) {
            log.error("安卓浏览器访问网页失败",e.fillInStackTrace());
        }
    }

    /**
     * 在传递定位表达式的时候，在最前面指定一下类型，然后进行拆分。
     * 用|id|来表示使用id进行元素定位
     * |id|android/tencet.mobile
     *
     * @param locator
     */
    public void input(String locator, String content) {
        try {
            WebElement element;
            if (locator.startsWith("|id|")) {
                element = driver.findElement(By.id(locator.substring(locator.lastIndexOf("|") + 1)));
            } else if (locator.startsWith("|aid|")) {
                System.out.println("定位表达式aid是" + locator.substring(locator.lastIndexOf("|") + 1));
                System.out.println(driver);
                element = driver.findElement(AppiumBy.accessibilityId(locator.substring(locator.lastIndexOf("|") + 1)));
            } else {
                element = driver.findElement(By.xpath(locator));
            }
            element.clear();
            element.sendKeys(content);
            log.info("向定位表达式" + locator + "输入内容" + content);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("向定位表达式" + locator + "输入内容失败", e.fillInStackTrace());
        }
    }

    public void click(String locator) {
        try {
            WebElement element = getWebElement(locator);
            element.click();

            log.info("正在点击" + locator);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("点击" + locator + "失败", e.fillInStackTrace());
        }
    }

    /**
     * 封装的定位元素方法，支持id/aid/xpath
     *
     * @param locator
     * @return
     */
    public WebElement getWebElement(String locator) {
        WebElement element;
        if (locator.startsWith("|id|")) {
            element = driver.findElement(By.id(locator.substring(locator.lastIndexOf("|") + 1)));
        } else if (locator.startsWith("|aid|")) {
            element = driver.findElement(AppiumBy.accessibilityId(locator.substring(locator.lastIndexOf("|") + 1)));
        } else {
            element = driver.findElement(By.xpath(locator));
        }
        return element;
    }


    /******************************Appium封装坐标操作方法********************************/
    /****
     * 绝对坐标点击操作
     */
    public void tapAxis(String xAxis, String yAxis) {
        try {
            int x = Integer.parseInt(xAxis);
            int y = Integer.parseInt(yAxis);
            TouchAction touchAction = new TouchAction(driver);
            PointOption point = PointOption.point(x, y);
            touchAction.tap(point).perform();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 通过和屏幕的尺寸比例来进行换算再点击
     */
    public void tapRateWithScreen(String xRate, String yRate) {
        //对于屏幕的比例
        float xr = Float.parseFloat(xRate);
        float yr = Float.parseFloat(yRate);
        //屏幕尺寸*比例得到坐标位置
        int width = driver.manage().window().getSize().getWidth();
        System.out.println("屏幕宽" + width);
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("屏幕高" + height);
        int x = (int) (xr * width);
        int y = (int) (yr * height);
        System.out.println("点击位置是" + x + "," + y);
        tapAxis(String.valueOf(x), String.valueOf(y));
    }

    /**
     * 通过某个元素的相对位置来进行换算。
     *
     * @param locator
     * @param xRate
     * @param yRate
     */
    public void tapRateElement(String locator, String xRate, String yRate) {
        WebElement element = getWebElement(locator);
        //元素左上角坐标
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();
        //元素的宽和高
        Dimension size = element.getSize();
        int width = size.getWidth();
        int height = size.getHeight();
        //元素左上角(x,y)+比例*元素宽高（w,h）
        float xr = Float.parseFloat(xRate);
        float yr = Float.parseFloat(yRate);
        int clickX = (int) (xr * width + x);
        int clickY = (int) (yr * height + y);
        log.info("点击的坐标位置是" + clickX + "," + clickY);
        tapAxis(String.valueOf(clickX), String.valueOf(clickY));
    }


    /******************************adb封装操作方法*************************************/
    public void adbTap(String xAxis, String yAxis) {
        try {
            int x = Integer.parseInt(xAxis);
            int y = Integer.parseInt(yAxis);
            runCmd("adb shell input tap " + x + " " + y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tapAdbRateWithScreen(String xRate, String yRate) {
        //对于屏幕的比例
        float xr = Float.parseFloat(xRate);
        float yr = Float.parseFloat(yRate);
        //屏幕尺寸*比例得到坐标位置
        int width = driver.manage().window().getSize().getWidth();
        System.out.println("屏幕宽" + width);
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("屏幕高" + height);
        int x = (int) (xr * width);
        int y = (int) (yr * height);
        System.out.println("点击位置是" + x + "," + y);
        adbTap(String.valueOf(x), String.valueOf(y));
    }

    /*******************滑动与长按、缩放*************************/

    /**
     * 调用adb滑动,注意，缩短参数m时间，即可实现长按
     */
    public void adbSwipe(String i, String j, String k, String l, String m) {
        try {
            runCmd("adb shell input swipe " + i + " " + j + " " + k + " " + l + " " + m);
        } catch (Exception e) {
            log.error("通过adb执行滑动失败");
            log.error(e, e.fillInStackTrace());
        }
    }

    /**
     * appium基于长按+移动实现滑动
     */
    public void appiumSwipe(String startX, String startY, String endX, String endY) {
        try {
            TouchAction action = new TouchAction(driver);
            //学会通过源码学习如何使用方法。
            PointOption startpoint = PointOption.point(Integer.parseInt(startX), Integer.parseInt(startY));
            PointOption endPoint = PointOption.point(Integer.parseInt(endX), Integer.parseInt(endY));
            //通过拼接长按和移动方法完成swipe动作。
            action.longPress(startpoint).moveTo(endPoint).release().perform();
            log.info(String.format("执行滑动操作，从(%s,%s)滑动到(%s,%s)"), startX, startY, endX, endY);

        } catch (NumberFormatException e) {
            log.error(e, e.fillInStackTrace());
            log.error("滑动操作失败");

        }
    }

    /**
     * appium基于元素定位，从指定元素中点开始实现滑动
     *
     * @param xpath
     * @param finX
     * @param finY
     */
    public void appiumSwipeElement(String xpath, String finX, String finY) {
        try {
            // string型的参数转换为int型
            int x1 = Integer.parseInt(finX);
            int y1 = Integer.parseInt(finY);
            TouchAction action = new TouchAction(driver);
            // 设置起点和终点
            PointOption movePoint = PointOption.point(x1, y1);
            // 滑动操作由长按起点->移动到终点->松开组成。
            action.longPress(LongPressOptions.longPressOptions()
                    .withElement(ElementOption.element(driver.findElement(By.xpath(xpath))))).moveTo(movePoint)
                    .release().perform();

        } catch (Exception e) {
            log.error("执行Appium滑动方法失败");
            log.error(e, e.fillInStackTrace());

        }
    }

    /**
     * 使用appium touchAction类实现长按指定坐标位置
     *
     * @param x
     * @param y
     * @param time
     */
    public void appiumHold(String x, String y, String time) {
        try {
            // string转int
            int xAxis = Integer.parseInt(x);
            int yAxis = Integer.parseInt(y);
            int t = Integer.parseInt(time);
            // 指定长按的坐标
            PointOption pressPoint = PointOption.point(xAxis, yAxis);
            // 长按的时间，使用java提供的time类库中的duration类
            Duration last = Duration.ofSeconds(t);
            WaitOptions wait = WaitOptions.waitOptions(last);
            TouchAction action = new TouchAction(driver);
            // 长按坐标->指定按住的时间进行等待
            action.longPress(pressPoint).waitAction(WaitOptions.waitOptions(last)).release().perform();

        } catch (NumberFormatException e) {
            log.error("执行Appium滑动方法失败");
            log.error(e, e.fillInStackTrace());

        }
    }

    /**
     * 实现长按某个元素
     *
     * @param xpath
     * @param time
     */
    public void appiumHoldEl(String xpath, String time) {
        try {
            int t = Integer.parseInt(time);
            Duration last = Duration.ofSeconds(t);
            TouchAction action = new TouchAction(driver);
            // 定位到一个元素，并且在该元素上长按指定的时长
            action.longPress(LongPressOptions.longPressOptions()
                    .withElement(ElementOption.element(driver.findElement(By.xpath(xpath)))).withDuration(last))
                    .waitAction(WaitOptions.waitOptions(last)).release().perform();

        } catch (NumberFormatException e) {
            log.error("执行Appium滑动方法失败");
            log.error(e, e.fillInStackTrace());

        }
    }

    /**
     * 双指操作，需要分别指定两个手指的动作起止坐标。
     */
    public void doubleFinger() {
        try {
            // multitouchaction，用于拼接多个touchaction，让他们同时发生。
            MultiTouchAction multiAction = new MultiTouchAction(driver);
            // 创建两个touchaction，分别实现两个手指的动作。
            TouchAction actionone = new TouchAction(driver);
            // 创建等待时间的对象。
            Duration last = Duration.ofMillis(300);
            WaitOptions waitOptions = WaitOptions.waitOptions(last);
            // 创建第一个手指的移动起止点。x坐标增大，y坐标减小，往右上方划
            PointOption pressPointone = PointOption.point(300, 900);
            PointOption movePointone = PointOption.point(400, 800);
            // 滑动操作由长按起点->移动到终点->松开组成。
            actionone.press(pressPointone).waitAction(waitOptions).moveTo(movePointone).waitAction(waitOptions).release();
            // 创建第二个手指的动作。
            TouchAction actiontwo = new TouchAction(driver);
            // x坐标减小，y坐标增大，往左下方划
            PointOption pressPointtwo = PointOption.point(500, 600);
            PointOption movePointtwo = PointOption.point(400, 800);
            // 滑动操作由长按起点->移动到终点->松开组成。
            actiontwo.press(pressPointtwo).waitAction(waitOptions).moveTo(movePointtwo).waitAction(waitOptions).release();
            // 将创建好的两个不同的touchaction，添加到multiaction里，形成一组同步动作，从而完成操作。
            multiAction.add(actionone).add(actiontwo).perform();

        } catch (Exception e) {
            log.error(e.fillInStackTrace());

        }
    }

    /******************按键事件、adb输入*************************/

    /**
     * 调用adb input 方法进行输入。
     *
     * @param text
     */
    public void adbText(String text) {
        try {
            runCmd("adb shell input text " + text);
            log.info("用adb输入" + text);
        } catch (Exception e) {
            log.error("通过adb输入失败");
            log.error(e.fillInStackTrace());
        }
    }

    /**
     * 通过cmd调用adb按键事件
     *
     * @param keycode
     */
    public void adbPressKey(String keycode) {
        try {
            int k = Integer.parseInt(keycode);
            String cmd = " adb shell input keyevent " + k;
            runCmd(cmd);
            halt("5");
        } catch (Exception e) {
            log.error("通过adb执行按键事件失败");
            log.error(e, e.fillInStackTrace());
        }
    }


    /**
     * 调用按键完成回车操作
     */
    public void pressEnter() {
        try {
            KeyEvent enter = new KeyEvent();
            driver.pressKey(enter.withKey(AndroidKey.ENTER));
            log.info("执行回车操作");

        } catch (Exception e) {
            log.error("执行回车操作失败");
            log.error(e, e.fillInStackTrace());
        }
    }

    /**
     * 通过key名字执行安卓按键事件
     *
     * @param keyName 输入键名，比如BACK、ENTER
     */
    public void appiumKeyeventByName(String keyName) {
        KeyEvent key = new KeyEvent();
        AndroidKey akey = AndroidKey.valueOf(keyName);
        driver.pressKey(key.withKey(akey));
    }


    /**************************************截图操作***************************************/
    /***
     * 截取整个手机屏幕截图
     * @param filename
     */
    public void takeScreenshot(String filename) {
        try {
            filename += timeStampString("MMdd-HHmmss") + ".png";
            filename = "Logs\\pic\\" + filename;
            log.info("正在截取图片" + filename);
            //截图保存为jvm内存中的文件
            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //在硬盘中创建一个文件，将截图复制过去。
            File picFile = new File(filename);
            Files.copy(screenshotAs, picFile);
        } catch (IOException e) {
            log.error(e.fillInStackTrace());
            log.error("截图失败");
        }
    }


    /**
     * 将元素截为图片
     *
     * @param xpath
     * @param filename
     */
    public void takeEleShot(String xpath, String filename) {
        try {
            //截取指定元素的图片
            filename += timeStampString("MMdd-HHmmss") + ".png";
            filename = "Logs\\pic\\" + filename;
            File screenshotAs = driver.findElement(By.xpath(xpath)).getScreenshotAs(OutputType.FILE);
            File picFile = new File(filename);
            Files.copy(screenshotAs, picFile);
        } catch (IOException e) {
            log.error(e.fillInStackTrace());
            System.out.println("截图失败");
        }

    }


    /*****************************退出app和server************************************/
    public void closeApp() {
        driver.quit();
    }

    public void closeServer() {
        try {
            Runtime.getRuntime().exec("taskkill /f /im node.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
