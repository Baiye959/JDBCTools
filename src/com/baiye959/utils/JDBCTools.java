package com.baiye959.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;

public class JDBCTools<T> {
    public T getBean(Connection connection, String sql, Class clazz) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 查询SQL
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // 映射成JavaBean，反射
            Object object = clazz.getConstructor(null).newInstance(null);

            // 解析结果集
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnClassName = metaData.getColumnClassName(i);
                    Object value = null;
                    switch (columnClassName) {
                        case "java.lang.Integer":
                            value = resultSet.getInt(columnName);
                            break;
                        case "java.lang.String":
                            value = resultSet.getString(columnName);
                            break;
                    }
                    // System.out.println(columnName + ":" + value);
                    // 给属性赋值
                    // 获取setter方法
                    String methodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Field field = clazz.getDeclaredField(columnName);
                    Method method = clazz.getMethod(methodName, field.getType());
                    method.invoke(object, value);
                }
            }



            return (T)object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Tools.Release(connection, statement, resultSet);
        }

        return null;
    }
}
