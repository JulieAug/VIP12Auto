package com.testing.class7;

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
public class WebDDT {
    public static void main(String[] args) {
        WebKeyword web=new WebKeyword();
        ExcelReader cases=new ExcelReader("Cases\\WebCases.xlsx");
        //生成一份结果文件，然后用writer来写结果文件。
        ExcelWriter writer=new ExcelWriter("Cases\\WebCases.xlsx","Cases\\Result\\结果-Web"+ AutoTools.timeStampString("yyyyMMdd HH：mm：ss"));
        //遍历所有sheet页
        for (int sheetNO = 0; sheetNO < cases.getTotalSheetNo(); sheetNO++) {
            cases.useSheetByIndex(sheetNO);
            writer.useSheetByIndex(sheetNO);
            System.out.println("当前sheet页是："+cases.getSheetName(sheetNO));
            //遍历sheet页中的所有行,要执行测试用例，并且通过writer写入结果。
            for (int i = 0; i < cases.getRowNo(); i++) {
                List<String> caseLine = cases.readLine(i);
                System.out.println(caseLine);
                //通过第4列提供的不同关键字名，调用对应的方法：
                switch(caseLine.get(3)){
                    case "openBrowser":
                        web.openBrowser(caseLine.get(4));
                        //写入行是i
                        writer.writeCell(i,10,"打开浏览器成功");
                        break;
                    case "visitWeb":
                        web.visitWeb(caseLine.get(4));
                        writer.writeCell(i,10,"访问web成功");
                        break;
                    case "input":
                        web.input(caseLine.get(4),caseLine.get(5));
                        writer.writeCell(i,10,"输入成功");
                        break;
                    case "click":
                        web.click(caseLine.get(4));
                        writer.writeFailCell(i,10,"点击操作很重要");
                        break;
                    case "switchFrame":
                        web.switchFrame(caseLine.get(4));
                        writer.writeFailCell(i,10,"切换Iframe");
                        break;
                    case "saveTimeStampParam":
                        web.saveTimeStampParam(caseLine.get(4),caseLine.get(5),caseLine.get(6));
                        break;
                    case "selectByText":
                        web.selectByText(caseLine.get(4),caseLine.get(5));
                        break;
                    case "halt":
                        web.halt(caseLine.get(4));
                        break;
                    case "closeBrowser":
                        web.closeBrowser();
                        break;
                }
            }
        }
        //记得调用save方法。
        cases.close();
        writer.save();

    }


}
