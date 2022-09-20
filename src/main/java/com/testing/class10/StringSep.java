package com.testing.class10;

/**
 * @Classname StringSep
 * @Description 类型说明
 * @Date 2022/6/18 20:34
 * @Created by 特斯汀Roy
 */
public class StringSep {
    public static void main(String[] args) {
        String s="|id|android/tencet.mobile";
        String substring = s.substring(s.lastIndexOf("|")+1);
        System.out.println(substring);
    }
}
