package com.testing.class13;

import com.testing.common.AutoTools;
import com.testing.driverself.HttpDriver;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Classname JsonTest
 * @Description 类型说明
 * @Date 2022/6/25 20:08
 * @Created by 特斯汀Roy
 */
public class JsonTest {

    public static void main(String[] args) throws IOException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost jsonLogin=new HttpPost("http://www.testingedu.com.cn/mypro/api/user/login");
//        StringEntity jsonParam=new StringEntity("{\"username\":\"roy\",\"pwd\":\"123456\"}","utf-8");
//        jsonParam.setContentType("application/json");
//        jsonLogin.setEntity(jsonParam);
//
//        CloseableHttpResponse execute = client.execute(jsonLogin);
//        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
//        System.out.println(AutoTools.decodeUnicode(s));

        HttpDriver driver=new HttpDriver();
        String s = driver.postJson("http://www.testingedu.com.cn/mypro/api/user/login", "{\"username\":\"roy\",\"pwd\":\"123456\"}");
        System.out.println(AutoTools.decodeUnicode(s));


    }

}
