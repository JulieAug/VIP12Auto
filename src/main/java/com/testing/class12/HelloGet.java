package com.testing.class12;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Classname HelloGet
 * @Description 类型说明
 * @Date 2022/6/23 21:17
 * @Created by 特斯汀Roy
 */
public class HelloGet {
    public static void main(String[] args) throws IOException {
        //创建一个用于发包的客户端
        CloseableHttpClient client = HttpClients.createDefault();
        //填写请求信息,创建get格式的请求报文
        HttpGet helloGet=new HttpGet("http://localhost:8080/hello/roy?age=26");
//        helloGet.setHeader("roy","handsome");

        //发包,并且获取到，返回报文
        CloseableHttpResponse execute = client.execute(helloGet);
        System.out.println(execute);
        //获取返回体内容。
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        System.out.println(s);
        //获取返回行
        System.out.println("状态码"+execute.getStatusLine().getStatusCode());
        System.out.println("状态码描述"+execute.getStatusLine().getReasonPhrase());
        //获取返回头
        Header[] headers = execute.getHeaders("Content-type");
        for (Header header : headers) {
            System.out.println(header.getName() + "头域的值是" + header.getValue());
        }
    }


}
