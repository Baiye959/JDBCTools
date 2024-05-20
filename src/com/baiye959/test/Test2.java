package com.baiye959.test;

import com.baiye959.entity.Account;
import com.baiye959.utils.C3P0Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test2 {
    public static void main(String[] args) throws SQLException {
        Connection connection = C3P0Tools.getConnection();
//        System.out.println(connection);
//        C3P0Tools.Release(connection, null, null);
        String sql = "select * from account where id = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name =  resultSet.getString(2);
            Account account = new Account();
            account.setId(id);
            account.setName(name);
            System.out.println(account);
        }
        C3P0Tools.Release(connection, statement, resultSet);
    }
}
