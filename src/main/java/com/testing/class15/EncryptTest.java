package com.testing.class15;

import com.testing.common.EncryptUtils;

/**
 * @Classname EncryptTest
 * @Description 类型说明
 * @Date 2022/6/30 20:25
 * @Created by 特斯汀Roy
 */
public class EncryptTest {
    public static void main(String[] args) {
        EncryptUtils enc=new EncryptUtils();

        String enCrypt = enc.enCrypt("123456");
        System.out.println(enCrypt);
        System.out.println(enc.deCrypt(enCrypt));


    }

}
