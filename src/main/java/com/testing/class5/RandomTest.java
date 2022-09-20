package com.testing.class5;

import com.testing.common.AutoTools;
import javafx.scene.control.RadioMenuItem;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Classname RandomTest
 * @Description 类型说明
 * @Date 2022/6/7 20:13
 * @Created by 特斯汀Roy
 */
public class RandomTest {

    public static void main(String[] args) {
        //取随机数
        Random random=new Random();
        int nextInt = random.nextInt(1000);
        System.out.println(nextInt);

        //取指定范围内的随机值
        String[] target={"roy","will","土匪","aiming"};
        int indexNext= random.nextInt(target.length-1);
        System.out.println(target[indexNext]);

        //生成时间戳
//        long l = System.currentTimeMillis();
        //指定时间格式的模板：
        SimpleDateFormat sdf=new SimpleDateFormat("HH-mm-ss");
        Date date=new Date();
        String format = sdf.format(date);
        System.out.println(format);


        String s = AutoTools.pickRandomString("web", "app", "性能", "接口");
        System.out.println(s);


    }


}
