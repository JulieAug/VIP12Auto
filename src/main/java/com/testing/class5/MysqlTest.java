package com.testing.class5;

import java.sql.*;

/**
 * @Classname MysqlTest
 * @Description 类型说明
 * @Date 2022/6/7 21:49
 * @Created by 特斯汀Roy
 */
public class MysqlTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //指定mysql连接驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://47.105.110.138:3306/tpshop?" +
                "useSSL=false&useUnicode=true&characterEncoding=utf-8", "Will", "willfqng");
        Statement statement = connection.createStatement();
        //得到结果集，需要进行处理
        ResultSet resultSet = statement.executeQuery("select * from tp_goods where goods_name like 'VIP12测试商品%'");
        System.out.println(resultSet);
        //获取查询结果的列数据（元信息）
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取下一列数据，next方法既判断有没有下一条数据，也将resultSet移到下一行数据。
        while (resultSet.next()) {
            //对列数据中的每一列进行遍历，获取列信息
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                //列的下标从1开始，所以需要+1
                System.out.println(metaData.getColumnName(i + 1) + "的值是" + resultSet.getString(i + 1));
            }
        }

    }

}
