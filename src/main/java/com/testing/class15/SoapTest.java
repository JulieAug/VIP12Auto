package com.testing.class15;

import com.testing.driverself.HttpDriver;
import com.testing.inter.InterKeyword;

/**
 * @Classname SoapTest
 * @Description 类型说明
 * @Date 2022/6/30 21:56
 * @Created by 特斯汀Roy
 */
public class SoapTest {
    public static void main(String[] args) {
//        HttpDriver driver=new HttpDriver();
//        String xml = driver.post("http://www.testingedu.com.cn:8081/inter/SOAP?wsdl", "xml", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:auth></soap:auth></soapenv:Body></soapenv:Envelope>");
//        String xml1 = driver.post("http://www.testingedu.com.cn:8081/inter/SOAP?wsdl", "xml", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:login><arg0>Will</arg0><arg1>123456</arg1></soap:login></soapenv:Body></soapenv:Envelope>");
//        System.out.println(xml);
//        System.out.println(xml1);


        InterKeyword inter=new InterKeyword();
        inter.saveParam("url","http://www.testingedu.com.cn:8081/inter/SOAP?wsdl");
        inter.post("{url}","xml","<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:auth></soap:auth></soapenv:Body></soapenv:Envelope>");
        inter.regexCheck("\"msg\":\"(.*?)\"","success");

        inter.saveRegexParam("token值","\"token\":\"(.*?)\"");
        inter.addHeader("{\"token\":\"{token值}\"}");
        inter.post("{url}","xml","<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\"><soapenv:Header/><soapenv:Body><soap:login><arg0>Will</arg0><arg1>123456</arg1></soap:login></soapenv:Body></soapenv:Envelope>");
        inter.regexCheck("\"msg\":\"(.*?)\"","恭喜您，登录成功");


    }

}
