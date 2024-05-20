package com.baiye959.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class Test1 {
    public static void main(String[] args) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource("testc3p0");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
