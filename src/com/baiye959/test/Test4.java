package com.baiye959.test;

import com.baiye959.entity.Account;
import com.baiye959.entity.Student;
import com.baiye959.utils.C3P0Tools;
import com.baiye959.utils.JDBCTools;
import java.sql.Connection;

public class Test4 {
    public static void main(String[] args) {
        Connection connection = C3P0Tools.getConnection();
        String sql = "select * from account where id = ? and name = ?";
        JDBCTools<Account> jdbcTools = new JDBCTools<>();
        Account account = jdbcTools.getBean(connection, sql, Account.class, 1, "张三");
        System.out.println(account);
    }
}
