package com.baiye959.test;

import com.baiye959.entity.Account;
import com.baiye959.entity.Student;
import com.baiye959.utils.C3P0Tools;
import com.baiye959.utils.JDBCTools;

import java.sql.Connection;

/**
 * 固定sql语句，单表查询测试
 */
public class Test3 {
    public static void main(String[] args) {
//        Connection connection = C3P0Tools.getConnection();
//        String sql = "select * from account where id = 1";
//        JDBCTools<Account> jdbcTools = new JDBCTools<>();
//        Account account = jdbcTools.getBean(connection, sql, Account.class);
//        System.out.println(account);

        Connection connection = C3P0Tools.getConnection();
        String sql = "select * from student where id = 1";
        JDBCTools<Student> jdbcTools = new JDBCTools<>();
        Student student = jdbcTools.getBean(connection, sql, Student.class);
        System.out.println(student);
    }
}
