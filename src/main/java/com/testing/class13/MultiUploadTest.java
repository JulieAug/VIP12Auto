package com.testing.class13;

import com.testing.common.AutoTools;
import com.testing.driverself.HttpDriver;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Classname MultiUploadTest
 * @Description 类型说明
 * @Date 2022/6/25 20:42
 * @Created by 特斯汀Roy
 */
public class MultiUploadTest {
    public static void main1(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post=new HttpPost("http://www.testingedu.com.cn:8000/index.php/home/Uploadify/imageUp/savepath/head_pic/pictitle/banner/dir/images.html");
        MultipartEntityBuilder meb=MultipartEntityBuilder.create();
        //添加文件类型的参数。
        meb.addBinaryBody("file",new File("E:\\QSwork\\素材\\微信二维码.png"));
        //添加文本类型的参数。
        meb.addTextBody("id","WU_FILE_0");
        meb.addTextBody("type","image/jpg");
        HttpEntity build = meb.build();
        System.out.println("实体的content-TYpe是"+build.getContentType());
        post.setEntity(build);
        CloseableHttpResponse execute = client.execute(post);
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        System.out.println(AutoTools.decodeUnicode(s));
    }

    public static void main(String[] args) {
        HttpDriver driver=new HttpDriver();
        driver.addHeader("cookie","sessionid=7kn5qx7k1edifj3yzpxqdph43w6gyy1h; nickname=\"b'roy\\\\xe8\\\\x80\\\\x81\\\\xe5\\\\xb8\\\\x88\\\\xe5\\\\x91\\\\x80'\"; PHPSESSID=2jo25agdi1gu5kt66v77nf7pn4; province_id=1; city_id=2; district_id=3; parent_region=%5B%7B%22id%22%3A3%2C%22name%22%3A%22%u4E1C%u57CE%u533A%22%7D%2C%7B%22id%22%3A14%2C%22name%22%3A%22%u897F%u57CE%u533A%22%7D%2C%7B%22id%22%3A22%2C%22name%22%3A%22%u5D07%u6587%u533A%22%7D%2C%7B%22id%22%3A30%2C%22name%22%3A%22%u5BA3%u6B66%u533A%22%7D%2C%7B%22id%22%3A39%2C%22name%22%3A%22%u671D%u9633%u533A%22%7D%2C%7B%22id%22%3A83%2C%22name%22%3A%22%u4E30%u53F0%u533A%22%7D%2C%7B%22id%22%3A105%2C%22name%22%3A%22%u77F3%u666F%u5C71%u533A%22%7D%2C%7B%22id%22%3A115%2C%22name%22%3A%22%u6D77%u6DC0%u533A%22%7D%2C%7B%22id%22%3A145%2C%22name%22%3A%22%u95E8%u5934%u6C9F%u533A%22%7D%2C%7B%22id%22%3A159%2C%22name%22%3A%22%u623F%u5C71%u533A%22%7D%2C%7B%22id%22%3A188%2C%22name%22%3A%22%u901A%u5DDE%u533A%22%7D%2C%7B%22id%22%3A204%2C%22name%22%3A%22%u987A%u4E49%u533A%22%7D%2C%7B%22id%22%3A227%2C%22name%22%3A%22%u660C%u5E73%u533A%22%7D%2C%7B%22id%22%3A245%2C%22name%22%3A%22%u5927%u5174%u533A%22%7D%2C%7B%22id%22%3A264%2C%22name%22%3A%22%u6000%u67D4%u533A%22%7D%2C%7B%22id%22%3A281%2C%22name%22%3A%22%u5E73%u8C37%u533A%22%7D%2C%7B%22id%22%3A47530%2C%22name%22%3A%223%22%7D%5D; user_id=8; is_distribut=0; uname=%25E6%2584%25BF%25E6%25AD%25A4%25E5%258E%25BB%25E5%2589%258D%25E7%25A8%258B%25E4%25BC%25BC%25E9%2594%25A6%25EF%25BC%258C%25E5%2586%258D%25E7%259B%25B8%25E9%2580%25A2%25E4%25BE%259D%25E7%2584%25B6%25E5%25A6%2582%25E6%2595%2585; cn=0; is_mobile=0");
        String s = driver.postUpload("http://www.testingedu.com.cn/mypro/api/user/setavatar", "{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{}}");
        System.out.println(AutoTools.decodeUnicode(s));

//        driver.notUseHeader();
        String s1 = driver.postUrl("http://www.testingedu.com.cn/mypro/api/user/getuserinfo", "");
        System.out.println(AutoTools.decodeUnicode(s1));


//        String m=driver.post("http://www.testingedu.com.cn:8000/index.php/home/Uploadify/imageUp/savepath/head_pic/pictitle/banner/dir/images.html","file","{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
//        System.out.println(AutoTools.decodeUnicode(m));

    }

}
