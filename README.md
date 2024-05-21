# 数据库查询工具

[【【建议收藏】徒手撸一个数据库查询工具，简单好用，对你理解底层帮助巨大！】](https://www.bilibili.com/video/BV1Kf4y1s7xf)


用于测试的数据库建表语句
```sql
create database JDBCTools;
use JDBCTools;
create table account(
    `id` int auto_increment primary key,
    `name` varchar(32) not null
);
insert into account values(1, "张三");
insert into account values(2, "李四");
insert into account values(3, "王五");

create table student(
    `id` int auto_increment primary key,
    `name` varchar(32) not null,
    `age` int null,
    `cid` int not null
);
insert into student values(1, "学生1", 19, 1);
insert into student values(2, "学生2", 19, 2);
insert into student values(3, "学生3", 20, 3);
```
