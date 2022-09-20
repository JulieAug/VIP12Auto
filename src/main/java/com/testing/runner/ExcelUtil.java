package com.testing.runner;

import com.testing.common.*;
import com.testing.inter.InterKeyword;

import java.util.List;

/**
 * @Classname InterDDT
 * @Description 类型说明
 * @Date 2022/7/2 20:13
 * @Created by 特斯汀Roy
 */
public class ExcelUtil {

    public static void excel(String fileName,String resultFileName) {
        //测试执行开始时间
//        String startTime = AutoTools.timeStampString("YYYY:MM:DD HH：mm：ss");
        String startTime = AutoTools.getDateTime();
        ExcelReader cases=new ExcelReader(fileName);
        ExcelWriter writer=new ExcelWriter(fileName,resultFileName);
        InterKeyword inter=new InterKeyword(writer);
        //遍历sheet页
        for (int sheetNo = 0; sheetNo < cases.getTotalSheetNo(); sheetNo++) {
            cases.useSheetByIndex(sheetNo);
            writer.useSheetByIndex(sheetNo);
            AutoTools.log.info("当前sheet页是"+cases.getSheetName(sheetNo)+"开始遍历");
            for (int rowNo = 0; rowNo < cases.getRowNo(); rowNo++) {
                List<String> rowContent = cases.readLine(rowNo);
                //指定当前写入行。
                inter.setWriteLine(rowNo);
                AutoTools.log.info("第"+rowNo+"行的内容是："+rowContent);
                //执行测试用例
                //测试用例行的特征是前两列都为空
                if(rowContent.get(0).trim().length()==0&&rowContent.get(1).trim().length()==0){
                    //调用第4列的关键字执行发包
                    //通过反射机制完成调用
                    AutoTools.invokeKeyWord(inter,rowContent,3);
                    //调用第8列的断言方法。
                    AutoTools.invokeKeyWord(inter,rowContent,7);
                }
            }
        }
        cases.close();
        writer.save();
        //对结果文件进行数据统计并且生成测试报告发送
        Report.sendreport(resultFileName,startTime);
    }
}
