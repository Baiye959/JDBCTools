package com.baiye959.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3P0Tools {

    private static ComboPooledDataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource("testc3p0");
    }

    /**
     * 获取连接对象
     * @return
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void Release(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
