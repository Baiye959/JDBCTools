package com.baiye959.test;

import com.baiye959.entity.Student;
import com.baiye959.utils.C3P0Tools;
import com.baiye959.utils.JDBCTools;

import java.sql.Connection;
import java.util.List;

/**
 * 通过固定SQL语句查询对象列表测试
 */
public class Test5 {
    public static void main(String[] args) {
        Connection connection = C3P0Tools.getConnection();
        String sql = "select * from student where age = 19";
        JDBCTools<Student> jdbcTools = new JDBCTools<>();
        List<Student> students = jdbcTools.getBeans(connection, sql, Student.class);
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
