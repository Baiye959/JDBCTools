package com.baiye959.test;

import com.baiye959.entity.Account;
import com.baiye959.utils.C3P0Tools;
import com.baiye959.utils.JDBCTools;

import java.sql.Connection;

public class Test3 {
    public static void main(String[] args) {
        Connection connection = C3P0Tools.getConnection();
        String sql = "select * from account where id = 1";
        JDBCTools<Account> jdbcTools = new JDBCTools<>();
        Account account = jdbcTools.getBean(connection, sql, Account.class);
        System.out.println(account);
    }
}
