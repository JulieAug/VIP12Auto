package com.testing.class14;

import com.testing.common.AutoTools;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Classname CookiePlatTest
 * @Description 类型说明
 * @Date 2022/6/28 20:09
 * @Created by 特斯汀Roy
 */
public class CookiePlatTest {


    public static void main(String[] args) throws IOException {
        //创建一个用于存储cookie的cookiestore对象。
        CookieStore purse=new BasicCookieStore();
        //先登录获取cookie，然后在之后的文件上传请求中携带cookie

        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(purse).build();
        HttpPost jsonLogin=new HttpPost("http://www.testingedu.com.cn/mypro/api/user/login");
        StringEntity jsonParam=new StringEntity("{\"username\":\"roy\",\"pwd\":\"123456\"}","utf-8");
        jsonParam.setContentType("application/json");
        jsonLogin.setEntity(jsonParam);

        CloseableHttpResponse execute = client.execute(jsonLogin);
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        System.out.println(AutoTools.decodeUnicode(s));

//        请求上传
        //未登录状态进行测试，用了一个新的client1对象
        CloseableHttpClient client1 = HttpClients.custom().build();
        HttpPost post=new HttpPost("http://www.testingedu.com.cn/mypro/api/user/setavatar");
        MultipartEntityBuilder meb=MultipartEntityBuilder.create();
        //添加文件类型的参数。
        meb.addBinaryBody("file",new File("E:\\QSwork\\素材\\微信二维码.png"));
        HttpEntity build = meb.build();
        System.out.println("实体的content-TYpe是"+build.getContentType());
        post.setEntity(build);
//        post.setHeader("Content-Type","multipart/form-data; boundary=----WebKitFormBoundary1aJgyAxH1yUpDnZv");
//        post.setHeader("Cookie","sessionid=50gbjge7cr6dlex0g8nqazfbavlcumyg");
        CloseableHttpResponse upResult = client1.execute(post);
        String upStr = EntityUtils.toString(upResult.getEntity(), "utf-8");
        System.out.println(AutoTools.decodeUnicode(upStr));

        //以已登录状态进行测试，沿用之前登录的时候用的client对象
        HttpPost upload=new HttpPost("http://www.testingedu.com.cn/mypro/api/user/setavatar");
        MultipartEntityBuilder meblogin=MultipartEntityBuilder.create();
        //添加文件类型的参数。
        meblogin.addBinaryBody("file",new File("E:\\QSwork\\素材\\微信二维码.png"));
        HttpEntity buildlogin = meblogin.build();
        System.out.println("实体的content-TYpe是"+buildlogin.getContentType());
        upload.setEntity(buildlogin);
//        post.setHeader("Content-Type","multipart/form-data; boundary=----WebKitFormBoundary1aJgyAxH1yUpDnZv");
//        post.setHeader("Cookie","sessionid=50gbjge7cr6dlex0g8nqazfbavlcumyg");
        CloseableHttpResponse uploginResult = client.execute(upload);
        String uploginStr = EntityUtils.toString(uploginResult.getEntity(), "utf-8");
        System.out.println(AutoTools.decodeUnicode(uploginStr));





    }
}
