---
layout: posts
title: 使用Navicat生成RE关系图
date: 2019-08-31 08:38:36
categories: 数据库
tags: [数据库, navicat,ER图]

---

# [使用Navicat生成ER图](https://github.com/yizuoliang/blog/tree/master/数据库相关/02_使用Navicat生成ER图)

## 1.ER图

​	E-R图也称实体-联系图(Entity Relationship Diagram)，提供了表示实体类型、属性和联系的方法,开发的时候往往需要有ER图.

## 2.常用工具

​	Visio:可以以更直观的方式创建图表的新功能,但是如果要画表关系的话,需要一个个去打,没有逆向生成模型的功能.

​	PowerDesigner:是一款非常全面的数据库设计工具,支持表与表之间建立关系,界面简洁，功能强大,这个可以选用.

​	Navicat:非常好用的数据库管理工具,可以逆向生成模型.

## 3.使用Navicat生成关系图

​	1.新建模型

​		新建模型,将需要的表拖入模型中,如果表与表之间存在外键,将自动生成外键关系图.

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190831133449.bmp)

​	2.添加外键

​	现在数据库建表其实都不适合使用外键,使用外键,极易受到数据库服务器的瓶颈,而且不容易扩展。尤其是现在的互联网产品，数据量大，并发高，更不适合使用外键。所有的外键约束，需要应用程序来实现，牺牲应用服务器的资源，换取数据库服务器的性能。

​	那么表与表之间没有外键，表关系在代码中，ER图又想体现出关系来，在navicat中该怎么做呢？

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190831133450.bmp)



​	3.导出模型

​	模型创建好了可以保存为npm格式，下次使用navicat可以再修改，很多时候，需要转换成PDF格式，可以使用navicat转换。

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190831133448.bmp)