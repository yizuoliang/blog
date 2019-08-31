---
layout: posts
title: mysql数据库存储引擎
date: 2019-08-31 12:38:36
categories: 数据库
tags: [数据库, mysql]

---

# [mysql数据库存储引擎](https://github.com/yizuoliang/blog/tree/master/数据库相关/01_mysql数据库存储引擎)

## 一、存储引擎概念

数据库就是存放数据的仓库。至于怎么存储，就涉及到存储引擎。

不同的存储引擎使用不同的存储机制、索引机制、锁定水平，根据实际需求选择不同的存储引擎。

## 二、mysql支持的存储引擎

MyISAM、InnoDB、Memory、CSV、Archive

常用的：MyISAM、InnoDB

## 三、各种存储引擎比较

mysql引擎有很多，只介绍以下通用的引擎。

### MyISAM引擎

不支持事务操作，支持表级锁，支持创建索引，不支持外键，并发性能会低很多（表级锁），存储空间会占用比较小。

### InnoDB引擎

支持事务操作，支持行级锁，支持创建索引，支持外键，允许并发量更大（行级锁），存储空间会占用比较大。InnoDB是默认的MySQL引擎。

### memery 引擎

所有表的数据存储在内存中，数据库重启和崩溃数据即将消失。非常适合储存临时数据的临时表以及数据仓库的经纬表。

### Archive引擎

Archive存储引擎只支持Insert和select操作,Archive存储引擎十分适合储存归档的数据,比如日志。使用行锁实现高并发的而操作。而且Archive存储引擎使用了zlib算法，将数据行进行压缩后储存，压缩比达1:10。

### 存储引擎的选择

1.如果要提供事物能力，并要求实现并发控制，InnoDB是一个好的选择。

2.如果数据表主要用来插入和查询记录，则MyISAM引擎能提供较高的处理效率。

3.如果只是临时存放数据，数据量不大，以选择将数据保存在内存中的Memory引擎。

4.如果只有INSERT和SELECT操作，可以选择Archive，如记录日志信息可以使用Archive。

## 四、设置表的存储引擎

### 1、查看表的引擎

SELECT TABLE_SCHEMA,TABLE_NAME,TABLE_TYPE,ENGINE FROM  information_schema.TABLES WHERE  TABLE_NAME = 'TABLE_NAME';

SHOW CREATE TABLE  TABLE_NAME；（也可从创建表的sql语句查看）。

SHOW TABLE STATUS where name ='TABLE_NAME'

### 2、修改表的引擎

ALTER TABLE TABLE_NAME ENGINE = InnoDB;

### 3.创建表时指定引擎

CREATE TABLE TABLE_NAME (ID INT) ENGINE=InnoDB;

## 一、存储引擎概念

数据库就是存放数据的仓库。至于怎么存储，就涉及到存储引擎。

不同的存储引擎使用不同的存储机制、索引机制、锁定水平，根据实际需求选择不同的存储引擎。

## 二、mysql支持的存储引擎

MyISAM、InnoDB、Memory、CSV、Archive

常用的：MyISAM、InnoDB

## 三、各种存储引擎比较

mysql引擎有很多，只介绍以下通用的引擎。

### MyISAM引擎

不支持事务操作，支持表级锁，支持创建索引，不支持外键，并发性能会低很多（表级锁），存储空间会占用比较小。

### InnoDB引擎

支持事务操作，支持行级锁，支持创建索引，支持外键，允许并发量更大（行级锁），存储空间会占用比较大。InnoDB是默认的MySQL引擎。

### memery 引擎

所有表的数据存储在内存中，数据库重启和崩溃数据即将消失。非常适合储存临时数据的临时表以及数据仓库的经纬表。

### Archive引擎

Archive存储引擎只支持Insert和select操作,Archive存储引擎十分适合储存归档的数据,比如日志。使用行锁实现高并发的而操作。而且Archive存储引擎使用了zlib算法，将数据行进行压缩后储存，压缩比达1:10。

### 存储引擎的选择

1.如果要提供事物能力，并要求实现并发控制，InnoDB是一个好的选择。

2.如果数据表主要用来插入和查询记录，则MyISAM引擎能提供较高的处理效率。

3.如果只是临时存放数据，数据量不大，以选择将数据保存在内存中的Memory引擎。

4.如果只有INSERT和SELECT操作，可以选择Archive，如记录日志信息可以使用Archive。

## 四、设置表的存储引擎

### 1、查看表的引擎

SELECT TABLE_SCHEMA,TABLE_NAME,TABLE_TYPE,ENGINE FROM  information_schema.TABLES WHERE  TABLE_NAME = 'TABLE_NAME';

SHOW CREATE TABLE  TABLE_NAME；（也可从创建表的sql语句查看）。

SHOW TABLE STATUS where name ='TABLE_NAME'

### 2、修改表的引擎

ALTER TABLE TABLE_NAME ENGINE = InnoDB;

### 3.创建表时指定引擎

CREATE TABLE TABLE_NAME (ID INT) ENGINE=InnoDB;