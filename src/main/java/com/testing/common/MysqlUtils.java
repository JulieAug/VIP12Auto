package com.testing.common;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname MysqlUtils
 * @Description 类型说明
 * @Date 2022/6/7 22:08
 * @Created by 特斯汀Roy
 */
public class MysqlUtils {

    //成员变量数据库连接对象
    Connection mycon;

    public void createCon(String jdbc,String username,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mycon = DriverManager.getConnection(jdbc,username,password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接配置失败");
        }
    }

    public boolean hasResult(String sql){
        try {
            Statement statement = mycon.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("查询操作失败");
            return false;
        }
    }


    /**
     * 查询数据库中的结果，获取为一个list<map>
     * @param sql
     * @return
     */
    public List<Map<String,String>> queryResult(String sql){
        try {
            Statement statement = mycon.createStatement();
            ResultSet resultSet = statement.executeQuery(AutoTools.replaceParam(sql));
            //所有行存成一个list
            List<Map<String,String>> datalist=new ArrayList<>();
            while(resultSet.next()){
                //每一行存成一个map
                Map<String,String> lineData=new HashMap<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //存储列的名称，和一行数据的对应值为键值对
                    lineData.put(resultSet.getMetaData().getColumnName(i),resultSet.getString(i));
                }
                datalist.add(lineData);
            }
            return datalist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //即使报错了，也还是返回一个空列表。
            return  new ArrayList<>();
        }
    }



}
