package com.testing.web;

import com.google.common.io.Files;
import com.testing.common.AutoTools;
import com.testing.common.ExcelWriter;
import com.testing.common.MysqlUtils;
import com.testing.common.RobotUtils;
import com.testing.driverself.FFDriver;
import com.testing.driverself.GoogleDriver;
import com.testing.driverself.GoogleMobileDriver;
import com.testing.driverself.IEDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname WebKeyword
 * @Description 封装对浏览器的基本操作类
 * @Date 2022/5/28 20:45
 * @Created by 特斯汀Roy
 */
public class WebKeyword extends AutoTools {
    //所有对浏览器的基本操作都要用的，浏览器对象。
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    //excel写入类，用于在执行过程中完成结果文件的写入
    private ExcelWriter writer;
    //用于记录当前需要写入的行号
    private int writeLineNO;

    //用于设置当前写入的行号
    public void setWriteLineNO(int writeLineNO) {
        this.writeLineNO = writeLineNO;
    }

    //常量，写入列，方便一旦excel模板发生改动的时候，调整写入结果的位置。
    public static final int RESULT_COL = 10;

    /**
     * 无参构造方法：针对调试时，不需要进行excel写入操作，这时writer为空
     * 有参构造方法：做数据驱动时，对excel进行写入。 这个时候writer不为空
     */
    //无参构造方法
    public WebKeyword() {

    }

    //有参构造方法，传递excelwriter对象来进行写入。
    public WebKeyword(ExcelWriter writer) {
        this.writer = writer;
    }

    //完成结果写入的方法：
    public void setPass() {
        //为了避免空指针报错，做判断，判断是否需要写入操作。
        if (writer != null) {
            writer.writeCell(writeLineNO, RESULT_COL, "PASS");
        }
    }

    public void setFail() {
        if (writer != null) {
            writer.writeFailCell(writeLineNO, RESULT_COL, "FAIL");
        }
    }


    /**************************浏览器管理类方法*****************************/

    /**
     * 启动浏览器，将webdriver对象赋值。
     *
     * @param browserType
     */
    public boolean openBrowser(String browserType) {
        try {
            switch (browserType) {
                case "火狐":
                    FFDriver ff = new FFDriver("", "DriverExe\\geckodriver.exe");
                    driver = ff.getDriver();
                    break;
                case "ie":
                    IEDriver ieDriver = new IEDriver("DriverExe\\IEDriverServer.exe");
                    driver = ieDriver.getDriver();
                    break;
                case "edge":
                    System.setProperty("webdriver.edge.driver", "DriverExe\\msedgedriver.exe");
                    driver = new EdgeDriver();
                    break;
                case "mobile":
                    GoogleMobileDriver gmd=new GoogleMobileDriver("DriverExe\\chromedriver.exe");
                    driver=gmd.getDriver();
                    break;
                case "chrome":
                default:
                    GoogleDriver gg = new GoogleDriver("DriverExe\\chromedriver.exe");
                    driver = gg.getDriver();
            }
            //设置隐式等待。
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            setPass();
            log.info("浏览器已完成启动");
            return true;
        } catch (Exception e) {
            log.error("启动浏览器失败！", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    public boolean visitWeb(String url) {
        try {
            log.info("正在访问" + url);
            driver.get(url);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("输入的url地址有问题，无法访问网页", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    public boolean setWindowSize(int leftCorner, int height, int width) {
        try {
            driver.manage().window().setPosition(new Point(leftCorner, 0));
            driver.manage().window().setSize(new Dimension(height, width));
            log.info("正在设置窗口位置");
            return true;
        } catch (Exception e) {
            log.error("设置窗口位置失败", e.fillInStackTrace());
            return false;
        }
    }

    public boolean closeBrowser() {
        try {
            driver.quit();
            log.info("浏览器已关闭");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("关闭浏览器失败，请查看异常信息", e.fillInStackTrace());
            setFail();
            return false;
        }

    }

    /**************************元素操作类方法******************************/

    /**
     * 根据css选择器定位元素并输入。
     *
     * @param css
     * @param content
     */
    public boolean inputByCss(String css, String content) {
        try {
            WebElement element = driver.findElement(By.cssSelector(css));
            element.clear();
            element.sendKeys(content);
            log.info("向元素" + css + "输入内容" + content);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("向元素" + css + "输入内容" + content + "失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 根据id来点击一个元素。
     *
     * @param id
     */
    public boolean clickById(String id) {
        try {
            driver.findElement(By.id(id)).click();
            log.info("点击id为" + id + "的元素");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("点击id为" + id + "的元素", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 默认元素定位方法用xpath进行输入
     *
     * @param xpath
     * @param content
     */
    public boolean input(String xpath, String content) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            element.clear();
            String paramContent = replaceParam(content);
            element.sendKeys(paramContent);
            setPass();
            log.info("向" + xpath + "元素输入" + content);
            return true;
        } catch (Exception e) {
            log.error("向" + xpath + "元素输入" + content + "失败", e.fillInStackTrace());
            takeScreenshot("输入操作失败截图");
            setFail();
            return false;
        }
    }

    /**
     * 封装upload方法，为了可读性更高。
     *
     * @param xpath
     * @param filePath
     */
    public void upload(String xpath, String filePath) {
        input(xpath, filePath);
    }

    /**
     * 默认使用xpath方法定位元素进行输入
     *
     * @param xpath
     */
    public boolean click(String xpath) {
        try {
            driver.findElement(By.xpath(xpath)).click();
            log.info("点击元素" + xpath);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("元素点击失败" + xpath, e.fillInStackTrace());
            takeScreenshot("点击操作失败截图");
            setFail();
            return false;
        }
    }

    /**
     * 根据选择定位方式，基于元素定位表达式来进行定位。
     *
     * @param method  定位方法
     * @param locator 定位表达式
     * @param content 输入内容
     */
    public boolean inputAll(String method, String locator, String content) {
        try {
            switch (method) {
                case "id":
                    driver.findElement(By.id(locator)).sendKeys(content);
                    break;
                case "css":
                    driver.findElement(By.cssSelector(locator)).sendKeys(content);
                    break;
                case "xpath":
                    driver.findElement(By.xpath(locator)).sendKeys(content);
                    break;
            }
            log.info(String.format("通过%s定位方法以%s定位并输入%s", method, locator, content));
            setPass();
            return true;
        } catch (Exception e) {
            log.error(String.format("通过%s定位方法以%s定位并输入%s失败", method, locator, content), e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 判断输入定位表达式的特征选择定位方法完成定位
     * 支持id 、 css、xpath
     *
     * @param locator
     */
    public boolean clickAll(String locator) {
        try {
            WebElement element;
            //xpath必然以/或//开头
            if (locator.startsWith("/")) {
                element = driver.findElement(By.xpath(locator));
            } else if (locator.contains("[")
                    || locator.startsWith("#")
                    || locator.startsWith(".")) {
                //css选择器中包含[或者以#或.开头，不会以/开头
                element = driver.findElement(By.cssSelector(locator));
            } else {
                //不是css或者xpath，则使用id方法定位，不使用其它的定位方法。
                element = driver.findElement(By.id(locator));
            }
            element.click();
            log.info("定位" + locator + "元素并点击");
            setPass();
            return true;
        } catch (Exception e) {
            setFail();
            log.error("点击" + locator + "失败", e.fillInStackTrace());
            return false;
        }
    }

    /**
     * 悬停操作，将鼠标移动到指定的元素上。
     *
     * @param xpath
     */
    public boolean hover(String xpath) {
        try {
            Actions act = new Actions(driver);
            act.moveToElement(driver.findElement(By.xpath(xpath))).perform();
            log.info("悬停鼠标到" + xpath + "元素");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("悬停到" + xpath + "元素失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * select元素基于value来进行选择
     *
     * @param xpath
     * @param value
     */
    public boolean selectByValue(String xpath, String value) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            Select select = new Select(element);
            select.selectByValue(value);
            log.info("操作select元素" + xpath + "选择value" + value);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("操作的select元素定位失败或没有找到对应的value" + value, e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    public boolean selectByText(String xpath, String text) {
        try {
            Select select = new Select(driver.findElement(By.xpath(xpath)));
            select.selectByVisibleText(text);
            log.info("操作select元素" + xpath + "选择文本" + text);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("操作的select元素定位失败或没有找到对应的文本" + text, e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 获取元素的文本内容
     *
     * @param xpath
     * @return
     */
    public String getText(String xpath) {
        try {
            String text = driver.findElement(By.xpath(xpath)).getText();
            log.info("获取元素" + xpath + "文本内容为" + text);
            return text;
        } catch (Exception e) {
            log.error("获取元素" + xpath + "文本内容时出错", e.fillInStackTrace());
            return "元素定位错误";
        }
    }

    /***
     * 截取整个浏览器页面截图
     * @param filename
     */
    public void takeScreenshot(String filename) {
        try {
            filename += timeStampString("MMdd-HHmmss") + ".png";
            filename = "log\\pic\\" + filename;
            log.info("正在截取图片" + filename);
            //截图保存为jvm内存中的文件，强转，将chromedriver对象从原本的webdriver转成截图类型
            File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //在硬盘中创建一个文件，将截图复制过去。
            File picFile = new File(filename);
            //底层是通过输入流将JVM中截好的图，传输到硬盘创建的文件中去。
            Files.copy(screenshotAs, picFile);
        } catch (IOException e) {
            log.error("截图失败", e.fillInStackTrace());
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
            filename = "log\\pic\\" + filename;
            File screenshotAs = driver.findElement(By.xpath(xpath)).getScreenshotAs(OutputType.FILE);
            File picFile = new File(filename);
            Files.copy(screenshotAs, picFile);
            log.info("将元素" + xpath + "截图");
        } catch (IOException e) {
            log.error("截图失败", e.fillInStackTrace());
        }

    }

    /***************************切换窗口和iframe的方法***************************************/

    /**
     * 在各个窗口中进行切换，直到找到目标标题的窗口，结束切换。
     *
     * @param title
     */
    public boolean switchWindowByTitle(String title) {
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                //先切换到窗口
                driver.switchTo().window(windowHandle);
                //如果窗口的标题是目标标题，则不再切换了。
                if (driver.getTitle().equals(title)) {
                    break;
                }
            }
            log.info("切换到标题为" + title + "的窗口");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换到标题为" + title + "的窗口失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 切换页面看其中有没有对应的元素，来判断是否为自己需要的页面
     *
     * @param xpath
     */
    public boolean switchWindowByContent(String xpath) {
        Set<String> windowHandles = driver.getWindowHandles();
        try {
            for (String windowHandle : windowHandles) {
                //先切换到对应窗口
                driver.switchTo().window(windowHandle);
                try {
                    driver.findElement(By.xpath(xpath));
                    break;
                } catch (Exception e) {
                    log.info("不是这个窗口，这里面没有" + xpath);
                }
            }
            log.info("切换到页面中包含" + xpath + "元素的窗口");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换到页面中包含" + xpath + "元素的窗口", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 将set转成list，然后根据序号来切换。
     *
     * @param index 从1开始算
     */
    public boolean switchWindowByNum(String index) {
        try {
            int i = Integer.parseInt(index);
            //通过实例化，将set转成list。
            List<String> handlelist = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(handlelist.get(i - 1));
            log.info("切换到第" + index + "个窗口");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换到第" + index + "个窗口失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /***
     *  支持使用xpath定位元素，或者用nameorId直接切换。
     * @param locator
     */
    public boolean switchFrame(String locator) {
        try {
            // 斜杠开头就是xpath，定位在切换
            if (locator.startsWith("/")) {
                WebElement frame = driver.findElement(By.xpath(locator));
                driver.switchTo().frame(frame);
                log.info("切换到xpath为" + locator + "的iframe");
            } else {
                //通过name or id形式切换。
                driver.switchTo().frame(locator);
                log.info("切换到id为" + locator + "的iframe");
            }
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换iframe失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 切换父级iframe
     */
    public boolean switchParent() {
        try {
            driver.switchTo().parentFrame();
            log.info("切换到父级Frame");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换到父级iframe失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 切换到最外层html
     */
    public boolean switchRoot() {
        try {
            driver.switchTo().defaultContent();
            log.info("切换到最外层html");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("切换到最外层html失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 如果要操作alert，就对这个方法做具体实现。
     */
    public void confirmAlert() {
        //alert很少出现，没法进行右键检查，不是一个html元素
        Alert alert = driver.switchTo().alert();
        //基本不会碰到alert元素。
        //alert有三种  普通alert 只有确认键 alert.accept();
        // confirm格式的alert 有确认和取消 alert.dismiss();
        //prompt 格式的， 可以填内容。 alert.sendKeys();
    }

    /*********************************调用Robot底层操作方法***************************************/

    public boolean mouseClick(String x, String y) {
        try {
            int xAxis = Integer.parseInt(x);
            int yAxis = Integer.parseInt(y);
            RobotUtils rb = new RobotUtils();
            rb.RbClick(xAxis, yAxis);
            log.info(String.format("通过底层键鼠操作点击坐标(%s,%s)", x, y));
            setPass();
            return true;
        } catch (Exception exception) {
            log.error(String.format("通过底层键鼠操作点击坐标(%s,%s)失败", x, y), exception.fillInStackTrace());
            setFail();
            return false;
        }
    }

    public boolean keyboardInput(String text) {
        try {
            RobotUtils rb = new RobotUtils();
            rb.copyWord(text);
            log.info("通过键盘直接输入" + text);
            setPass();
            return true;
        } catch (Exception e) {
            log.info("通过键盘直接输入" + text + "失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    public boolean keyboardEnter() {
        try {
            RobotUtils rb = new RobotUtils();
            rb.enter();
            log.info("键盘回车");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("键盘回车失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /********************************Js执行*****************************************/
    public boolean runJs(String script) {
        try {
            JavascriptExecutor jsRunner = (JavascriptExecutor) driver;
            jsRunner.executeScript(script);
            log.info("正在执行js" + script);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("执行js" + script + "失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 通过定位元素之后再进行操作。
     *
     * @param xpath
     * @param script 输入的内容，是.之后的内容
     */
    public boolean runJSWithElement(String xpath, String script) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            JavascriptExecutor jsRunner = (JavascriptExecutor) driver;
            //arguments[0]代表js语句之后传入的参数数组的第一个。
            jsRunner.executeScript("arguments[0]." + script, element);
            log.info("执行js语句arguments[0]." + script);
            setPass();
            return true;
        } catch (Exception e) {
            log.error("执行js语句" + "arguments[0]." + script + "失败", e.fillInStackTrace());
            setFail();
            return false;
        }

    }

    /****************************等待类方法*******************************/
    /**
     * 输入秒数，转换为毫秒，调用thread.sleep来实现等待。
     *
     * @param second 输入一个秒数，可以是0.几秒
     */
    public void halt(String second) {
        try {
            float times = Float.parseFloat(second);
            Thread.sleep((long) (times * 1000));
            log.info("等待" + times + "秒");
        } catch (Exception e) {
            log.error("等待失败了，请检查输入参数。", e.fillInStackTrace());
        }
    }

    /**
     * 实现一个显式等待，能够基于xpath定位元素，等待元素可以定位到
     *
     * @param xpath
     */
    public boolean exWaitElement(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //编写自己的等待条件，等待某个元素能定位到。
            ExpectedCondition<WebElement> waitElement = new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    WebElement element = driver.findElement(By.xpath(xpath));
                    return element;
                }

                public String toString() {
                    return String.format("通过xpath" + xpath + "没有定位到指定元素，所以10秒之后报错");
                }
            };
            wait.until(waitElement);
            log.info("显式等待" + xpath + "元素完成");
            setPass();
            return true;
        } catch (Exception e) {
            log.error("显示等待失败", e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /***************************断言类方法********************************/
    /**
     * 定位元素，获取属性的值，判断是否和预期结果相等。
     *
     * @param xpath
     * @param attr
     * @param expectedResult
     */
    public boolean assertEleAttr(String xpath, String attr, String expectedResult) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            String attribute = element.getAttribute(attr);
            if (attribute.equals(expectedResult)) {
                log.info("测试成功");
                setPass();
                return true;
            } else {
                log.error("测试失败");
                log.error("预期结果是" + expectedResult + "，实际结果是" + attribute);
                setFail();
                return false;
            }
        } catch (Exception e) {
            log.error("测试失败，获取元素的属性出错了",e.fillInStackTrace());
            setFail();
            return false;
        }
    }

    /**
     * 断言元素文本内容和预期结果一致。
     *
     * @param xpath
     * @param expectedResult
     */
    public boolean assertEleText(String xpath, String expectedResult) {
        try {
            String actual = driver.findElement(By.xpath(xpath)).getText();
            log.info("断言的时候实际结果是" + actual);
            log.info("预期结果是" + expectedResult + "替换变量后的值是" + replaceParam(expectedResult));
            if (actual.equals(replaceParam(expectedResult))) {
                log.info("测试成功");
                setPass();
                return true;
            } else {
                log.error("测试失败");
                setFail();
                return false;
            }
        } catch (Exception e) {
           log.error("端砚文本结果时定位元素失败",e.fillInStackTrace());
           setFail();
           return false;
        }
    }

    /**
     * 验证能够通过sql语句查询到结果
     *
     * @param sql
     */
    public boolean assertDbExist(String sql) {
        try {
            MysqlUtils mysqlUtils = new MysqlUtils();
            mysqlUtils.createCon("jdbc:mysql://47.105.110.138:3306/tpshop?" +
                    "useSSL=false&useUnicode=true&characterEncoding=utf-8", "Will", "willfqng");
            if (mysqlUtils.hasResult(replaceParam(sql))) {
                log.info("测试成功");
                setPass();
                return true;
            } else {
                log.error("测试失败，数据库里面没有查到相关内容。");
                setFail();
                return false;
            }
        } catch (Exception e) {
            log.error("断言数据库能查询到结果执行异常",e.fillInStackTrace());
            setFail();
            return false;
        }
    }


}
