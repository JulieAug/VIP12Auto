package com.testing.class13;

import com.testing.inter.InterKeyword;

/**
 * @Classname InterKwTest
 * @Description 类型说明
 * @Date 2022/6/25 22:18
 * @Created by 特斯汀Roy
 */
public class InterKwTest {
    public static void main(String[] args) {
        InterKeyword inter=new InterKeyword();
        inter.get("https://sp1.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=234.123.125.11&co=&resource_id=5809&t=1656166778752&ie=utf8&oe=gbk&format=json&tn=baidu&_=1656165017108");
        inter.jsonValueCheck("$.Result[0].DisplayData.resultData.tplData.location","保留地址 ");
        inter.jsonValueCheck("$.Result[0].ResultURL","http://www.ip138.com/");

        inter.post("http://www.testingedu.com.cn/mypro/api/user/getsummary","url","");
        inter.jsonValueCheck("$.msg","查询成功");

        inter.addHeader("{\"cookie\":\"sessionid=7kn5qx7k1edifj3yzpxqdph43w6gyy1h; nickname=\\\"b'roy\\\\\\\\xe8\\\\\\\\x80\\\\\\\\x81\\\\\\\\xe5\\\\\\\\xb8\\\\\\\\x88\\\\\\\\xe5\\\\\\\\x91\\\\\\\\x80'\\\"; PHPSESSID=2jo25agdi1gu5kt66v77nf7pn4; province_id=1; city_id=2; district_id=3; parent_region=%5B%7B%22id%22%3A3%2C%22name%22%3A%22%u4E1C%u57CE%u533A%22%7D%2C%7B%22id%22%3A14%2C%22name%22%3A%22%u897F%u57CE%u533A%22%7D%2C%7B%22id%22%3A22%2C%22name%22%3A%22%u5D07%u6587%u533A%22%7D%2C%7B%22id%22%3A30%2C%22name%22%3A%22%u5BA3%u6B66%u533A%22%7D%2C%7B%22id%22%3A39%2C%22name%22%3A%22%u671D%u9633%u533A%22%7D%2C%7B%22id%22%3A83%2C%22name%22%3A%22%u4E30%u53F0%u533A%22%7D%2C%7B%22id%22%3A105%2C%22name%22%3A%22%u77F3%u666F%u5C71%u533A%22%7D%2C%7B%22id%22%3A115%2C%22name%22%3A%22%u6D77%u6DC0%u533A%22%7D%2C%7B%22id%22%3A145%2C%22name%22%3A%22%u95E8%u5934%u6C9F%u533A%22%7D%2C%7B%22id%22%3A159%2C%22name%22%3A%22%u623F%u5C71%u533A%22%7D%2C%7B%22id%22%3A188%2C%22name%22%3A%22%u901A%u5DDE%u533A%22%7D%2C%7B%22id%22%3A204%2C%22name%22%3A%22%u987A%u4E49%u533A%22%7D%2C%7B%22id%22%3A227%2C%22name%22%3A%22%u660C%u5E73%u533A%22%7D%2C%7B%22id%22%3A245%2C%22name%22%3A%22%u5927%u5174%u533A%22%7D%2C%7B%22id%22%3A264%2C%22name%22%3A%22%u6000%u67D4%u533A%22%7D%2C%7B%22id%22%3A281%2C%22name%22%3A%22%u5E73%u8C37%u533A%22%7D%2C%7B%22id%22%3A47530%2C%22name%22%3A%223%22%7D%5D; user_id=8; is_distribut=0; uname=%25E6%2584%25BF%25E6%25AD%25A4%25E5%258E%25BB%25E5%2589%258D%25E7%25A8%258B%25E4%25BC%25BC%25E9%2594%25A6%25EF%25BC%258C%25E5%2586%258D%25E7%259B%25B8%25E9%2580%25A2%25E4%25BE%259D%25E7%2584%25B6%25E5%25A6%2582%25E6%2595%2585; cn=0; is_mobile=0\"}");
        inter.post("http://www.testingedu.com.cn/mypro/api/user/setavatar","file","{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        inter.jsonValueCheck("$.msg","恭喜您修改成功");

        inter.notUseHeader();
        inter.post("http://www.testingedu.com.cn/mypro/api/user/setavatar","file","{\"文件\":{\"file\":\"E:\\\\QSwork\\\\素材\\\\微信二维码.png\"},\"文本\":{\"id\":\"WU_FILE_0\",\"type\":\"image/png\"}}");
        inter.jsonValueCheck("$.code","1001");
    }
}
