package com.testing.class6.logTest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname LogTest
 * @Description 类型说明
 * @Date 2022/6/9 22:40
 * @Created by 特斯汀Roy
 */
public class LogTest {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(LogTest.class);
        logger.trace("追踪级别");
        logger.debug("调试界别");
        logger.info("普通信息");
        logger.warn("警告！警告！");
        logger.error("报错信息");
        logger.fatal("致命错误");

    }


}
