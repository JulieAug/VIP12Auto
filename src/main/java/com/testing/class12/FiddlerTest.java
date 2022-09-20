package com.testing.class12;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Proxy;

/**
 * @Classname FiddlerTest
 * @Description 类型说明
 * @Date 2022/6/23 21:41
 * @Created by 特斯汀Roy
 */
public class FiddlerTest {

    public static void main(String[] args) throws IOException {
        //设置一下fiddler作为请求的主机要走的代理
        //如果加了这个设置，就一定记得请求的时候打开fiddler。
        HttpHost fiddler = new HttpHost("127.0.0.1", 8888);
        CloseableHttpClient client = HttpClients.custom().setProxy(fiddler).build();
        HttpGet helloGet=new HttpGet("http://localhost:8080/hello/roy?age=26");
//        helloGet.setHeader("roy","handsome");

        //发包,并且获取到，返回报文
        CloseableHttpResponse execute = client.execute(helloGet);
        System.out.println(execute);
        //获取返回体内容。
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        System.out.println(s);
    }
}
