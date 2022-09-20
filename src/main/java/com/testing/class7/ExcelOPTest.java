package com.testing.class7;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname ExcelOPTest
 * @Description 类型说明
 * @Date 2022/6/11 21:03
 * @Created by 特斯汀Roy
 */
public class ExcelOPTest {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        //输入流读取文件的内容
        FileInputStream fis=new FileInputStream(new File("Cases\\WebCases.xlsx"));
        //用file参数创建
        Workbook workbook=new XSSFWorkbook(new File("Cases\\WebCases.xlsx"));
        //用inputstream参数创建
        Workbook workbook1=new XSSFWorkbook(fis);
        Sheet sheetBuy = workbook.getSheet("商城前台系统用例");
        //下标从0开始计数
        Row row6 = sheetBuy.getRow(5);

        Cell cell = row6.getCell(2);
        String stringCellValue = cell.getStringCellValue();
        System.out.println("第二页的信息"+stringCellValue);

        //获取后台测试用例sheet页，也就是第1页
        Sheet sheet1=workbook.getSheetAt(0);

        //下标从0开始计数
        Row row61 = sheet1.getRow(12);

        Cell cell1 = row61.getCell(2);
        String stringCellValue1 = cell1.getStringCellValue();
        System.out.println("第一页中的第13行"+stringCellValue1);


        //写入操作：
        Row row4= sheet1.getRow(3);
        Cell result=row4.getCell(10);
        result.setCellValue("执行写入成功");
        //在文件中其实看不到写入效果，因为现在是在jvm中操作。
        String stringCellValue2 = row4.getCell(10).getStringCellValue();
        System.out.println("写入之后的结果："+stringCellValue2);

        //将workbook在内存中的修改，写入到文件中。
        File file=new File("Cases/Result/Res.xlsx");
        file.createNewFile();
        FileOutputStream out=new FileOutputStream(file);
        workbook.write(out);
        out.close();
        workbook.close();






    }


}
