---
layout: posts
title: GitHub+jsDelivr+PicGo搭建免费图床
date: 2019-08-31 14:38:36
categories: GitHub
tags: [Lucene,后端,jsDelivr,PicGo]

---

# [GitHub+jsDelivr+PicGo搭建免费图床](https://github.com/yizuoliang/blog/tree/master/Github相关/01_GitHub+jsDelivr+PicGo搭建免费图床)



## 一、前言

​	1.用 markdown 写博客，想插入一些图片，如果采用本地存储方式，上传博客时插入的图片路径就找不到了，需要手动再把图片上传上去，并且修改路径，很麻烦，可以考虑将图片上传至图床生成 URL，直接在markdown 引入url。

​	2.现在国内用的各种图床,例如,微博图床、SM.MS、Imgur、七牛云、又拍云、腾讯云COS、阿里云OSS等都有各种限制，或者需要收费。

​	3.使用GitHub仓库创建一个图床，存在的问题是国内访问github的速度不是很快，可以利用jsDelivr CDN加速访问（jsDelivr 是一个免费开源的 CDN 解决方案）国内该平台是首个「打通中国大陆与海外的免费CDN服务」，网页开发者无须担心中国防火墙问题而影响使用。

## 二、创建Github仓库

​	创建一个github仓库，专门存放上传的图片。

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830142054.bmp)

## 三、生成Access token 

按照下列步骤依次生成token,生成的token只显示一次,页面关闭后就看不了了,需要先将它复制下来。

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830145901.bmp)

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830150612.bmp)

## 四、配置PicGo，使用jsDelivr的CDN

下载PicGo软件，安装。下载路径：<https://github.com/Molunerfinn/picgo/releases>

打开PicGo进行配置

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830151445.bmp)

将刚才在Github上创建的仓库名和分支名填入设置中，生成的Token复制到配置中。

指定存储文件夹的路径，PicGo上传文件的时候，将自动在github仓库中创建此文件夹。

自定义域名：这个很有用，如果设置了自定义域名，PicGo生成的访问链接，将是【自定义域名+文件名】的拼接方式。因为我们需要使用jsDelivr加速访问，所以将自定义域名设置为【[https://cdn.jsdelivr.net/gh/](https://link.zhihu.com/?target=https%3A//cdn.jsdelivr.net/gh/)用户名/图床仓库名 】。

当然PicGo还有许多配置，不懂可以看看PicGo提供的文档，<https://picgo.github.io/PicGo-Doc/zh/guide/>

## 五、使用免费图床

下面就可以愉快的图床了，选择需要的图片和格式，复制链接，粘贴到markdown中，就能显示了。

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830154016.bmp)

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20190830154410.bmp)