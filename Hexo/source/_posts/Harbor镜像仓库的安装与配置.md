---
layout: posts
title: Harbor镜像仓库的安装与配置
date: 2019-06-06 00:38:36
categories: docker镜像仓库
tags: [ docker]
---

#  Harbor镜像仓库的安装与配置

## 一、安装Harbor

### 1.环境准备：

​	1.一台Centos7服务器(docker已安装, ip : 192.168.1.105),此服务器准备安装镜像仓库;

​	2.一台Centos7服务器(docker已安装, ip : 192.168.1.106),此服务器测试从Harbor镜像仓库push和pull镜像用。

### 2.下载Harbor安装

下载安装包：https://github.com/goharbor/harbor/releases

#### 1.解压压缩包

```
 tar zxvf harbor-online-installer-v1.8.1.tgz
```

#### 2.配置harbor.yml文件

```
hostname: 192.168.1.105

http:
port will redirect to https port
  port: 80

https:
  port: 443
  certificate: /data/cert/192.168.1.105.crt
  private_key: /data/cert/192.168.1.105.key

harbor_admin_password: Harbor12345

database:
  password: root123
  
data_volume: /data
```

#### 3.运行prepare文件

```
./prepare
```

#### 4.安装

​	服务器必须先安装Docker Compose

```
./install.sh
```

安装完成,可以在浏览器中登录;但是使用另一台主机从镜像仓库中拉取镜像时将报错:

```
http: server gave HTTP response to HTTPS client
```

原因是docker作为客户端,发送的是https请求,仓库未配置https.

## 二、配置Harbor的HTTPS访问

可参考:	<https://goharbor.io/docs/2.0.0/install-config/configure-https/>

以下ip修改为Harbor服务器的ip

### 1.创建CA证书

​	生成CA证书私钥

```
openssl genrsa -out ca.key 4096
```

​	生成CA证书

```
openssl req -x509 -new -nodes -sha512 -days 3650 \
 -subj "/C=CN/ST=Beijing/L=Beijing/O=example/OU=Personal/CN=192.168.1.105"\
 -key ca.key \
 -out ca.crt
```

### 2.生成服务器证书

​	生成服务器证书私钥

```
openssl genrsa -out 192.168.1.105.key 4096
```

​	生成服务器证书

```
openssl req -sha512 -new \
    -subj "/C=CN/ST=Beijing/L=Beijing/O=example/OU=Personal/CN=192.168.1.105" \
    -key 92.168.1.105.key \
    -out 92.168.1.105.csr
```

### 3.生成 x509 v3扩展文件

这里不能按照官网的安装,官网使用的事域名.

```
cat > v3.ext <<-EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
extendedKeyUsage = serverAuth
subjectAltName = 192.168.1.105
```

### 4.向Harbor和Docker提供证书

### 1.向Harbor提供证书

harbor.yml配置文件中,certificate和private_key文件路径在/data/cert/下,所以要把生成的服务器证书放在这个目录下

```
mkdir /data/cert/
cp 192.168.1.105.crt /data/cert/
cp 192.168.1.105.key /data/cert/
```

### 2.向docker提供证书

将.crt文件转换为.cert文件,供docker使用

```
openssl x509 -inform PEM -in yourdomain.com.crt -out yourdomain.com.cert
```

```
mkdir -p /etc/docker/certs.d/192.168.1.105/
cp 192.168.1.105.cert /etc/docker/certs.d/192.168.1.105/
cp 192.168.1.105.key /etc/docker/certs.d/192.168.1.105/
cp ca.crt /etc/docker/certs.d/192.168.1.105/
```

### 3.重启docker

```sh
systemctl restart docker
```

## 三、验证,测试

### 1.浏览器使用https登录

将192.168.1.105.crt拷贝至本机安装,浏览器就能用https正常访问.

### 2.测试docker能否正常登录

#### 1.使用192.168.1.106服务器登录Harbor

```
docker login 192.168.1.105
```

将报错:

```
Error response from daemon: Get https://192.168.1.105/v2/: x509: certificate signed by unknown authority
```

#### 2.制作的ca证书添加到信任（因为是自签名证书):

将ca.crt拷贝至192.168.1.106主机

```
mkdir –p /etc/docker/certs.d/192.168.1.105
cp ca.crt /etc/docker/certs.d/192.168.1.105/ca.crt
```

```
systemctl restart docker
```

出现:Login Succeeded,表示登录成功

## 四、推送和拉取镜像

### 1.标记镜像

```
docker tag SOURCE_IMAGE[:TAG] 192.168.1.105/项目名/IMAGE[:TAG]
```

### 2.推送镜像到Harbor

```
docker push 10.82.13.105/项目名/IMAGE[:TAG]
```

#### 3.从Harbor中拉取镜像

```
docker pull 10.82.13.105/项目名/IMAGE[:TAG]
```

