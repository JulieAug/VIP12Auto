package com.testing.common;

import lombok.Data;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.security.Key;

/**
 * @Classname RobotUtils
 * @Description 类型说明
 * @Date 2022/6/2 21:33
 * @Created by 特斯汀Roy
 */
@Data
public class RobotUtils {

    //成员变量robot各个方法都要用
    public Robot robot;

    public RobotUtils() {
        try {
            robot = new Robot();
            robot.setAutoDelay(100);
        } catch (AWTException e) {
            AutoTools.log.error("系统错误，无法控制键鼠",e.fillInStackTrace());
        }
    }

    /**
     * @param x
     * @param y
     */
    public void RbClick(int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }

    /**
     * 通过robot直接调用输入按钮。
     *
     * @param text
     */
    public void Rbinput(String text) {
        //把字符串拆成一个个字符，然后对应按下按键
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            switch (aChar) {
                case 'r':
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_R);
                    break;
                case 'R':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
            }
        }
    }


    /***
     * 用反射机制查找字符对应的按键，从而完成键盘输入操作。
     * @param text 输入内容
     */
    public void RbinputReflection(String text) {
        try {
            //把字符串拆成一个个字符，然后对应按下按键
            char[] chars = text.toCharArray();
            for (char aChar : chars) {
                Field field = KeyEvent.class.getField("VK_" + String.valueOf(aChar).toUpperCase());
                robot.keyPress(field.getInt(null));
                robot.keyRelease(field.getInt(null));
            }
        } catch (Exception e) {
            AutoTools.log.error("底层键盘输入字符串"+text+"失败",e.fillInStackTrace());
        }
    }

    /**
     * 通过剪贴板直接输入内容
     * @param text
     */
    public void copyWord(String text){
        try {
            //剪贴板内容设置
            Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
            //剪贴的内容
            StringSelection content=new StringSelection(text);
            clipboard.setContents(content,null);
            //Robot实现粘贴
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
        } catch (HeadlessException e) {
            AutoTools.log.error("复制"+text+"通过robot输入失败",e.fillInStackTrace());
        }
    }

    /**
     * 按回车的方法
     */
    public void enter(){
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }



}
