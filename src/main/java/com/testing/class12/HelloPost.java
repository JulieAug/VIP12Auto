package com.testing.class12;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Classname HelloPost
 * @Description 类型说明
 * @Date 2022/6/23 21:45
 * @Created by 特斯汀Roy
 */
public class HelloPost {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost helloPost=new HttpPost("http://localhost:8080/hello");
        //编辑请求体
        StringEntity helloEntity=new StringEntity("username=asdfdd&password=sdf","utf-8");
        helloEntity.setContentType("application/x-www-form-urlencoded");
        helloEntity.setContentEncoding("UTF-8");
        //添加请求体到报文中
        helloPost.setEntity(helloEntity);

        //设置请求头
//        helloPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        CloseableHttpResponse execute = client.execute(helloPost);
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        System.out.println(s);

    }


}
