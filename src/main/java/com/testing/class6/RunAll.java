package com.testing.class6;

import com.testing.common.AutoTools;

/**
 * @Classname RunAll
 * @Description 类型说明
 * @Date 2022/6/9 22:10
 * @Created by 特斯汀Roy
 */
public class RunAll {
    public static void main(String[] args) {
        AutoTools.log.info("测试执行开始啦");
        ShopAdminTest.main(null);
        ShopSearchTest.main(null);
    }
}
