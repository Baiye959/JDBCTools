package com.baiye959.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCTools<T> {
    /**
     * 通过传SQL语句和SQL参数查询对象列表
     * @param connection
     * @param sql
     * @param clazz
     * @param params
     * @return
     */
    public List<T> getBeans(Connection connection, String sql, Class clazz, Object... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();

        try {
            // 查询SQL
            statement = connection.prepareStatement(sql);
            // 替换参数
            fillParams(statement, params);
            // 获取结果集
            resultSet = statement.executeQuery();
            // 解析结果集
            parseData(resultSet, list, clazz);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Tools.Release(connection, statement, resultSet);
        }

        return list;
    }

    /**
     * 通过固定SQL语句查询对象列表
     * @param connection
     * @param sql
     * @param clazz
     * @return
     */
    public List<T> getBeans(Connection connection, String sql, Class clazz) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();

        try {
            // 查询SQL
            statement = connection.prepareStatement(sql);
            // 获取结果集
            resultSet = statement.executeQuery();
            // 解析结果集
            parseData(resultSet, list, clazz);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Tools.Release(connection, statement, resultSet);
        }

        return list;
    }

    /**
     * 通过传SQL语句和SQL参数查询某个对象
     * @param connection
     * @param sql
     * @param clazz
     * @param params
     * @return
     */
    public T getBean(Connection connection, String sql, Class clazz, Object... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 查询SQL
            statement = connection.prepareStatement(sql);
            // 替换参数
            fillParams(statement, params);
            // 获取结果集
            resultSet = statement.executeQuery();
            // 映射成JavaBean，反射
            Object object = clazz.getConstructor(null).newInstance(null);
            // 解析结果集
            parseData(resultSet, object);

            return (T)object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Tools.Release(connection, statement, resultSet);
        }

        return null;
    }

    /**
     * 通过固定SQL语句查询某个对象
     * @param connection
     * @param sql
     * @param clazz
     * @return
     */
    public T getBean(Connection connection, String sql, Class clazz) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 查询SQL
            statement = connection.prepareStatement(sql);
            // 获取结果集
            resultSet = statement.executeQuery();
            // 映射成JavaBean，反射
            Object object = clazz.getConstructor(null).newInstance(null);
            // 解析结果集
            parseData(resultSet, object);

            return (T)object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Tools.Release(connection, statement, resultSet);
        }

        return null;
    }

    /**
     * 替换SQL参数
     * @param statement
     * @param params
     */
    public void fillParams(PreparedStatement statement, Object... params) {
        try {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                // 反射获取参数类型
                String typeName = param.getClass().getTypeName();
                switch (typeName) {
                    case "java.lang.Integer":
                        statement.setInt(i + 1, (Integer) param);
                        break;
                    case "java.lang.String":
                        statement.setString(i + 1, (String) param);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析结果集，给对象列表赋值
     * @param resultSet
     * @param list
     * @param clazz
     */
    public void parseData(ResultSet resultSet, List<T> list, Class clazz) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                // 映射成JavaBean，反射
                Object object = clazz.getConstructor(null).newInstance(null);

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

                list.add((T)object);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析结果集，给对象赋值
     * @param resultSet
     * @param object
     */
    public void parseData(ResultSet resultSet, Object object) {
        try {
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

                    // 给属性赋值
                    // 获取setter方法
                    String methodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Field field = object.getClass().getDeclaredField(columnName);
                    Method method = object.getClass().getMethod(methodName, field.getType());
                    method.invoke(object, value);
                }
            }
        } catch (SQLException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
