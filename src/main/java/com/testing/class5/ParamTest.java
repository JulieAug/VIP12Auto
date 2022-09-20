package com.testing.class5;

import com.testing.common.AutoTools;

/**
 * @Classname ParamTest
 * @Description 类型说明
 * @Date 2022/6/7 21:09
 * @Created by 特斯汀Roy
 */
public class ParamTest {

    public static void main(String[] args) {
        AutoTools.saveParam("roy","老王");
        AutoTools.saveParam("描述","可爱");
        AutoTools.saveParam("will","帅哥");
        System.out.println(AutoTools.paramMap);

        String s = AutoTools.replaceParam("{roy}是个{描述}");
        System.out.println(s);

    }


}
