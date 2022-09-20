package com.testing.runner;

import com.testing.common.AutoTools;
import org.junit.Test;

public class julieTest {

    @Test
    public void test() throws Exception {
        String startTime = AutoTools.getDateTime();
        String resultFileName="Cases/Result/结果-接口"+startTime;
        String fileName = "Cases/InterCases.xlsx";
        ExcelUtil.excel(fileName, resultFileName);
    }
}
