package com.testing.class16;

import com.testing.inter.InterKeyword;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Classname ReflectTest
 * @Description 反射机制测试
 * @Date 2022/7/2 20:43
 * @Created by 特斯汀Roy
 */
public class ReflectTest {
    public static InterKeyword inter=new InterKeyword();

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //获取类名
        String name = inter.getClass().getName();
        //反过来获取到这个类
        Class<?> aClass = Class.forName(name);
        //从类中获取方法和变量
        //输出所有方法的名字
        Method[] methods = aClass.getMethods();
        //查找单个方法的时候，需要指定参数列表（参数类型，顺序，个数）
        Method post = aClass.getMethod("post",String.class,String.class,String.class);
        System.out.println(post);
//        for (Method method : methods) {
//            System.out.println(method.getName());
//            //找到post的方法然后进行执行
//            if(method.getName().equals("post")){
//                //调用post
//                method.invoke(inter,"http://www.testingedu.com.cn:8081/inter/REST/user/register","url","{\"username\":\"{随机用户名}\",\"pwd\":\"123456\",\"nickname\":\"roy老师\",\"describe\":\"royrest\"}");
//            }
//        }
//
//        //输出所有变量
//        Field[] fields = aClass.getFields();
//        for (Field field : fields) {
//            System.out.println(field.getName());
//        }


    }


}
