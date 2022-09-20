package com.testing.class4;

import com.testing.common.RobotUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

/**
 * @Classname RobotTest
 * @Description 类型说明
 * @Date 2022/6/2 21:34
 * @Created by 特斯汀Roy
 */
public class RobotTest {

    public static void main(String[] args) throws AWTException {
        Robot rb =new Robot();
//        rb.mouseMove(1129,172);
//        rb.mousePress(KeyEvent.BUTTON1_MASK);
//        rb.mouseRelease(KeyEvent.BUTTON1_MASK);
        rb.delay(200);
        rb.setAutoDelay(500);
        rb.keyPress(KeyEvent.VK_R);
        rb.keyRelease(KeyEvent.VK_R);
        rb.keyPress(KeyEvent.VK_O);
        rb.keyRelease(KeyEvent.VK_O);
        rb.keyPress(KeyEvent.VK_Y);
        rb.keyRelease(KeyEvent.VK_Y);
        rb.keyPress(KeyEvent.VK_AMPERSAND);
        rb.keyRelease(KeyEvent.VK_AMPERSAND);

//        RobotUtils rb=new RobotUtils();
//        rb.Rbinput("rRRRRrrrr");

//        rb.RbClick(814,103);
//        rb.copyWord("数据驱动架构图.png");
//        rb.enter();
//
//        rb.RbinputReflection("royabcde");

    }

}
