package com.testing.class12;

import com.testing.common.AutoTools;
import com.testing.driverself.HttpDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname TestDriver
 * @Description 类型说明
 * @Date 2022/6/23 22:10
 * @Created by 特斯汀Roy
 */
public class TestDriver {
    public static void main(String[] args) {
        HttpDriver driver = new HttpDriver();
        String result = driver.get("https://sp1.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=5.5.5.54&co=&resource_id=5809&t=1655993395824&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery1102018633548311615433_1655993364767&_=1655993364769");
        result = AutoTools.decodeUnicode(result);
        System.out.println(result);
        if (result.contains("\"ResultCode\":\"0\"")) {
            System.out.println("测试成功");
        }
        //正则表达式解析断言
        Pattern p = Pattern.compile("\"Srcid\":\"(.*?)\"");
        Matcher matcher = p.matcher(result);
        if (matcher.find()) {
            String srcid = matcher.group(1);
            if ("5809".equals(srcid)) {
                System.out.println("测试成功");
            } else {
                System.out.println("测试失败");
            }
        } else {
            System.out.println("测试失败，解析不到对应结果");
        }


        String s = driver.postUrl("http://www.testingedu.com.cn:8000/index.php?m=Home&c=User&a=do_login&t=0.5288626671160634", "username=13800138006&password=123456&verify_code=1");
        System.out.println(AutoTools.decodeUnicode(s));
    }
}
