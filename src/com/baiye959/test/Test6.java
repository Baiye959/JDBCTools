package com.baiye959.test;

import com.baiye959.entity.Student;
import com.baiye959.utils.C3P0Tools;
import com.baiye959.utils.JDBCTools;

import java.sql.Connection;
import java.util.List;

/**
 * 通过传SQL语句和SQL参数查询对象列表测试
 */
public class Test6 {
    public static void main(String[] args) {
        Connection connection = C3P0Tools.getConnection();
        String sql = "select * from student where age = ?";
        JDBCTools<Student> jdbcTools = new JDBCTools<>();
        List<Student> students = jdbcTools.getBeans(connection, sql, Student.class, 19);
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
