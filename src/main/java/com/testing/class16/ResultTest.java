package com.testing.class16;

import com.testing.common.ExcelResult;

import java.util.List;
import java.util.Map;

/**
 * @Classname ResultTest
 * @Description 类型说明
 * @Date 2022/7/2 22:21
 * @Created by 特斯汀Roy
 */
public class ResultTest {
    public static void main(String[] args) {
        ExcelResult result=new ExcelResult();
        List<Map<String, String>> sumarry = result.Sumarry("Cases\\Result\\结果-接口20220702 21：42：31.xlsx", "22929349");
        System.out.println(sumarry);
        for (Map<String, String> stringStringMap : sumarry) {
            System.out.println(stringStringMap);
        }

    }

}
