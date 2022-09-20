package com.testing.class8;

import com.testing.common.AutoTools;
import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import com.testing.web.WebKeyword;

import java.util.List;

/**
 * @Classname WebDDT
 * @Description 类型说明
 * @Date 2022/6/11 22:05
 * @Created by 特斯汀Roy
 */
public class WebDDTWrite {
    public static void main(String[] args) {
        WebKeyword web = new WebKeyword();
        ExcelReader cases = new ExcelReader("Cases\\WebCases.xlsx");
        //生成一份结果文件，然后用writer来写结果文件。
        ExcelWriter writer = new ExcelWriter("Cases\\WebCases.xlsx", "Cases\\Result\\结果-Web" + AutoTools.timeStampString("yyyyMMdd HH：mm：ss"));
        //遍历所有sheet页
        for (int sheetNO = 0; sheetNO < cases.getTotalSheetNo(); sheetNO++) {
            cases.useSheetByIndex(sheetNO);
            writer.useSheetByIndex(sheetNO);
            System.out.println("当前sheet页是：" + cases.getSheetName(sheetNO));

            //判断result是否为true，来记录执行成功或者失败
            boolean result = true;
            //遍历sheet页中的所有行,要执行测试用例，并且通过writer写入结果。
            for (int i = 0; i < cases.getRowNo(); i++) {
                List<String> caseLine = cases.readLine(i);
                System.out.println(caseLine);
                //写入结果和执行用例的时候先判断是不是用例行（第一、二列为空）
                if (caseLine.get(0).trim().length() == 0 && caseLine.get(1).trim().length() == 0) {
                    //通过第4列提供的不同关键字名，调用对应的方法，同时为执行结果result赋值
                    switch (caseLine.get(3)) {
                        case "openBrowser":
                            web.openBrowser(caseLine.get(4));
                            break;
                        case "visitWeb":
                            web.visitWeb(caseLine.get(4));
                            break;
                        case "input":
                            result = web.input(caseLine.get(4), caseLine.get(5));
                            break;
                        case "click":
                            result = web.click(caseLine.get(4));
                            break;
                        case "switchFrame":
                            web.switchFrame(caseLine.get(4));
                            break;
                        case "saveTimeStampParam":
                            web.saveTimeStampParam(caseLine.get(4), caseLine.get(5), caseLine.get(6));
                            break;
                        case "selectByText":
                            web.selectByText(caseLine.get(4), caseLine.get(5));
                            break;
                        case "halt":
                            web.halt(caseLine.get(4));
                            break;
                        case "closeBrowser":
                            web.closeBrowser();
                            break;
                    }
                    //在执行之后，判断执行结果，决定写入成功还是失败
                    if (result) {
                        writer.writeCell(i, 10, "测试通过");
                    } else {
                        writer.writeFailCell(i, 10, "测试失败");
                    }
                }

            }
        }
        //记得调用save方法。
        cases.close();
        writer.save();

    }


}
